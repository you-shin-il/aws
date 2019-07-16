package ysi.cloud.aws.instance.web;

import com.amazonaws.services.ec2.model.Filter;
import com.amazonaws.services.ec2.model.Image;
import com.amazonaws.services.ec2.model.Instance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ysi.cloud.aws.Image.service.ImageService;
import ysi.cloud.aws.instance.service.InstanceService;
import ysi.cloud.aws.vmTransfer.service.TransferService;
import ysi.cloud.aws.volume.service.VolumeService;

import java.util.List;

/**
 * Created by ysi.
 * User: ysi
 * Date: 2019-07-15
 * Time: 오후 3:40
 */
@RestController
public class InstanceController {
	@Autowired
	private InstanceService instanceService;

	/**
	 * filters에 해당하는 인스턴스 목록 조회
	 * filters에 값이 없으면 모든 인스턴스 목록 조회
	 *
	 * @param filters
	 */
	@GetMapping("/aws/ec2/instance/list")
	public List<Instance> selectInstanceList(@RequestBody List<Filter> filters) {
		return instanceService.selectInstanceList(filters);
	}

	/**
	 * 인스턴스 이전(AWS -> OPENSTACK)
	 * filters에 해당하는 인스턴스 목록 조회
	 * filters에 값이 없으면 모든 인스턴스 목록 조회
	 *
	 * @param instanceId
	 */
	@PostMapping("/aws/ec2/instance/instanceTransfer")
	public String instanceTransfer(@RequestParam String instanceId) {

		return null;
	}
}