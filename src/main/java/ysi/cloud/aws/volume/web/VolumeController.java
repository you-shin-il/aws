package ysi.cloud.aws.volume.web;

import com.amazonaws.services.ec2.model.Filter;
import com.amazonaws.services.ec2.model.Instance;
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
}