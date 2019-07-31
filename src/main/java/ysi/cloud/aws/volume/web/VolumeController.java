package ysi.cloud.aws.volume.web;

import com.amazonaws.services.ec2.model.CreateVolumeRequest;
import com.amazonaws.services.ec2.model.Filter;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.Volume;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ysi.cloud.aws.instance.service.InstanceService;
import ysi.cloud.aws.volume.service.VolumeService;

import java.util.List;

/**
 * Created by ysi.
 * User: ysi
 * Date: 2019-07-15
 * Time: 오후 3:40
 */
@RestController
public class VolumeController {
	@Autowired
	private VolumeService volumeService;

	@PostMapping("/aws/ec2/volume/create")
	public List<Volume> createVolume(@RequestBody List<CreateVolumeRequest> createVolumeRequests) {
		return volumeService.createVolume(createVolumeRequests);
	}
}