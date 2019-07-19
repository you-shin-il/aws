package ysi.cloud.aws.vmTransfer.web;

import com.amazonaws.services.ec2.model.Filter;
import com.amazonaws.services.ec2.model.Instance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ysi.cloud.aws.instance.service.InstanceService;
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

	/**
	 * filters에 해당하는 인스턴스 목록 조회
	 * filters에 값이 없으면 모든 인스턴스 목록 조회
	 *
	 * @param filters
	 */
	@GetMapping("/aws/ec2/vmTransfer")
	public void vmTransfer(@RequestBody List<Filter> filters) {
		vmTransferService.awsMoveOpenstack(filters);
	}

}