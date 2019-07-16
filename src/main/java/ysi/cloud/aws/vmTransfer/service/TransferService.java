package ysi.cloud.aws.vmTransfer.service;

import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ysi.cloud.aws.Image.service.ImageService;
import ysi.cloud.aws.instance.service.InstanceService;
import ysi.cloud.aws.volume.service.VolumeService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Created by ysi.
 * User: ysi
 * Date: 2019-07-15
 * Time: 오후 8:04
 */
@Service
public class TransferService {
	final AmazonEC2 ec2 = AmazonEC2ClientBuilder.defaultClient();

	@Autowired
	private InstanceService instanceService;

	@Autowired
	private ImageService imageService;

	@Autowired
	private VolumeService volumeService;

	@Autowired
	private TransferService transferService;

	/**
	 * 볼륨 생성
	 *
	 * @param createVolumeRequest
	 */
	public Volume createVolume(CreateVolumeRequest createVolumeRequest) {
//		CreateVolumeRequest createVolumeRequest = new CreateVolumeRequest();
//		createVolumeRequest.setSize(10);
//		createVolumeRequest.setAvailabilityZone();
//		createVolumeRequest.setVolumeType();
//		createVolumeRequest.setSize(10);
//		ec2.createVolume();
		return null;
	}

	/**
	 * aws 에서 오픈스택으로
	 *
	 * @param filters
	 */
	public void awsMoveOpenstack(List<Filter> filters) {
		List<Instance> instances = instanceService.selectInstanceList(filters);

		instances.stream().forEach(x ->  {
			Optional<String> rootDeviceName = instanceService.selectRootDeviceName(x.getBlockDeviceMappings(),x.getRootDeviceName());

			if(rootDeviceName.isPresent()) {
				List<InstanceBlockDeviceMapping> instanceBlockDeviceMappingList = x.getBlockDeviceMappings();
				Optional<EbsInstanceBlockDevice> ebsInstanceBlockDevice = selectEbsInstanceBlockDevice(instanceBlockDeviceMappingList, rootDeviceName.get());

				if(ebsInstanceBlockDevice.isPresent()) {
					List<Filter> volumeFilters = new ArrayList<Filter>();
					Filter volumeFilter = new Filter();
					volumeFilter.setName("volume-id");
					volumeFilter.setValues(Arrays.asList(ebsInstanceBlockDevice.get().getVolumeId()));

					List<Volume> volumes = volumeService.selectVolumeList(volumeFilters);
					Volume volume = volumes.get(0);

					//DescribeVolumesRequest describeVolumesRequest = new DescribeVolumesRequest();

				}
				//selectRootDeviceVolumeId();
				//Optional<EbsInstanceBlockDevice> ebsInstanceBlockDevice = instanceBlockDeviceMappingList.stream().filter(y -> rootDeviceName.equals(y.getDeviceName())).map(z -> z.getEbs()).findFirst();
			}

		});

		//Optional<String> rootDeviceName = instances.stream().map(Instance::getRootDeviceName);
		//instances.stream().map(Instance::getBlockDeviceMappings);
//		InstanceBlockDeviceMapping instanceBlockDeviceMapping = instances.stream().map(x -> x.getBlockDeviceMappings().stream().filter(y -> y.equals(x.getRootDeviceName())));
	}

//	/**
//	 * rootDeviceVolumeId 찾기
//	 *
//	 * @param instanceBlockDeviceMappingList
//	 * @param rootDeviceName
//	 */
//	public Optional<EbsInstanceBlockDevice> selectRootDeviceVolumeId(List<InstanceBlockDeviceMapping> instanceBlockDeviceMappingList, String rootDeviceName) {
//		return instanceBlockDeviceMappingList.stream()
//											 .filter(x -> rootDeviceName.equals(x.getDeviceName()))
//											 .map(x -> x.getEbs())
//											 .findFirst();
//	}

	/**
	 * rootDevice에 volumeId와 일치하는 EbsInstanceBlockDevice 객체 찾기
	 *
	 * @param instanceBlockDeviceMappingList
	 * @param rootDeviceName
	 */
	public Optional<EbsInstanceBlockDevice> selectEbsInstanceBlockDevice(List<InstanceBlockDeviceMapping> instanceBlockDeviceMappingList, String rootDeviceName) {
		return instanceBlockDeviceMappingList.stream()
											 .filter(x -> x.getDeviceName().equals(rootDeviceName))
											 .findFirst()
											 .map(InstanceBlockDeviceMapping::getEbs);
	}

}