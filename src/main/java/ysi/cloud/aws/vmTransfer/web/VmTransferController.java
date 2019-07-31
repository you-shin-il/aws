package ysi.cloud.aws.vmTransfer.web;

import com.amazonaws.services.ec2.model.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;
import ysi.cloud.aws.vmTransfer.command.Command;
import ysi.ssh.SSHClient;
import ysi.cloud.aws.vmTransfer.service.VmTransferService;

import java.util.List;

/**
 * Created by ysi.
 * User: ysi
 * Date: 2019-07-18
 * Time: 오전 11:00
 */
@RestController
public class VmTransferController {
	@Autowired
	private VmTransferService vmTransferService;
	@Autowired
	private SSHClient sshClient;
	@Autowired
	Environment environment;

	/**
	 * filters에 해당하는 인스턴스 이전
	 *
	 * @param filters
	 */
	@GetMapping("/aws/ec2/vmTransfer")
	public void vmTransfer(@RequestBody List<Filter> filters) {
		vmTransferService.awsMoveOpenstack(filters);
	}

	/**
	 *
	 */
	@GetMapping("/sshTest.do")
	public void sshTest() {
		String actionCommand = String.format(Command.partitioning, "/dev/xvdp");
		sshClient.getSession("ysi_keypair_20190722.pem", actionCommand);
	}
}