package ysi.cloud.aws.volume.service;

import com.amazonaws.services.cloudwatchevents.AmazonCloudWatchEvents;
import com.amazonaws.services.cloudwatchevents.AmazonCloudWatchEventsClient;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ysi.cloud.aws.instance.service.InstanceService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Created by ysi.
 * User: ysi
 * Date: 2019-07-16
 * Time: 오후 1:28
 */
@Service
public class VolumeService {
	final AmazonEC2 ec2 = AmazonEC2ClientBuilder.defaultClient();

	@Autowired
	private InstanceService instanceService;

	public List<Volume> createVolume(List<CreateVolumeRequest> createVolumeRequests) {
		List<Volume> result = new ArrayList<Volume>();

		createVolumeRequests.stream().forEach(createVolumeRequest-> {
			CreateVolumeResult createVolumeResult = ec2.createVolume(createVolumeRequest);
			result.add(createVolumeResult.getVolume());
			}
		);

//		Instance instance = instanceService.selectInstance(instanceId);
//		String imageId = instance.getImageId();
//		DescribeImagesRequest describeImageRequest = new DescribeImagesRequest();
//		describeImageRequest.withImageIds(imageId);
//		DescribeImagesResult describeImageResult = ec2.describeImages(describeImageRequest);
//		List<Image> reservations = describeImageResult.getImages();
//
//		if(!reservations.isEmpty()) {
//			//reservations =
//		}
//
//		CreateVolumeRequest createVolumeRequest = new CreateVolumeRequest();
		return result;
	}

	/**
	 * filters에 해당하는 volume 목록 조회
	 * filters에 값이 없으면 모든 volume 목록 조회
	 *
	 * @param filters
	 */
	public List<Volume> selectVolumeList(List<Filter> filters) {
		DescribeVolumesRequest describeVolumesRequest = new DescribeVolumesRequest();
		describeVolumesRequest.setFilters(filters);
		DescribeVolumesResult describeVolumesResult = ec2.describeVolumes(describeVolumesRequest);

		return describeVolumesResult.getVolumes();
	}
/*
	*//**
	 * volume 생성
	 *
	 * @param createVolumeRequest
	 *//*
	public Volume createVolume(CreateVolumeRequest createVolumeRequest) {

		ec2.createVolume(createVolumeRequest);
		return null;
	}*/

	/**
	 * CreateVolumeRequest 생성
	 *
	 * @param volume
	 */
	public Volume createTransferVolume(Volume volume) {
		CreateVolumeRequest createVolumeRequest = new CreateVolumeRequest();
		createVolumeRequest.setVolumeType(volume.getVolumeType());
		createVolumeRequest.setAvailabilityZone(volume.getAvailabilityZone());
		createVolumeRequest.setEncrypted(volume.getEncrypted());
		createVolumeRequest.setIops(volume.getIops());
		createVolumeRequest.setKmsKeyId(volume.getKmsKeyId());
		createVolumeRequest.setSize(volume.getSize());
		createVolumeRequest.setVolumeType(volume.getVolumeType());

		List<Volume> result = createVolume(Arrays.asList(createVolumeRequest));

		return result.stream().findFirst().get();
	}

	/**
	 * 인스턴스에 볼륨 연결
	 *
	 * @param attachVolumeRequest
	 */
	public AttachVolumeResult volumeAttach(AttachVolumeRequest attachVolumeRequest) {
		return ec2.attachVolume(attachVolumeRequest);
	}

	/**
	 * 루트 디바이스 볼륨 조회
	 *
	 * @param ebsInstanceBlockDevice
	 */
	public Volume getRootDeviceVolume(EbsInstanceBlockDevice ebsInstanceBlockDevice) {
		List<Filter> volumeFilters = new ArrayList<Filter>();
		Filter volumeFilter = new Filter();
		volumeFilter.setName("volume-id");
		volumeFilter.setValues(Arrays.asList(ebsInstanceBlockDevice.getVolumeId()));

		List<Volume> volumes = selectVolumeList(volumeFilters);

		return volumes.stream().findFirst().get();
	}
}