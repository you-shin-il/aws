package ysi.cloud.aws;

import com.amazonaws.services.ec2.model.Filter;
import com.amazonaws.services.ec2.model.Image;
import com.amazonaws.services.ec2.model.Instance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by ysi.
 * User: ysi
 * Date: 2019-07-15
 * Time: 오후 3:40
 */
@RestController
public class Ec2Controller {

	@Autowired
	private Ec2Service ec2Service;

	/**
	 * filters에 해당하는 인스턴스 목록 조회
	 * filters에 값이 없으면 모든 인스턴스 목록 조회
	 *
	 * @param filters
	 */
	@GetMapping("/aws/ec2/instance/list")
	public List<Instance> selectInstanceList(@RequestBody List<Filter> filters) {
		return ec2Service.selectInstanceList(filters);
	}

	/**
	 * filters에 해당하는 이미지 목록 조회
	 * filters에 값이 없으면 모든 인스턴스 목록 조회
	 *
	 * @param filters
	 */
	@GetMapping("/aws/ec2/instance/list")
	public List<Image> selectImageList(@RequestBody List<Filter> filters) {
		return ec2Service.selectImageList(filters);
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