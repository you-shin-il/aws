package ysi.cloud.aws;

import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.*;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ysi.
 * User: ysi
 * Date: 2019-07-15
 * Time: 오후 8:04
 */
@Service
class Ec2Service {
	final AmazonEC2 ec2 = AmazonEC2ClientBuilder.defaultClient();

	/**
	 * filters에 해당하는 이미지 목록 조회
	 * filters에 값이 없으면 모든 인스턴스 목록 조회
	 *
	 * @param filters
	 */
	public List<Instance> selectInstanceList(List<Filter> filters) {
		DescribeInstancesRequest describeInstancesRequest = new DescribeInstancesRequest();
		describeInstancesRequest.setFilters(filters);
		DescribeInstancesResult describeInstancesResult = ec2.describeInstances(describeInstancesRequest);
		List<Reservation> reservations = describeInstancesResult.getReservations();

		return reservations.stream().map(x -> x.getInstances()).flatMap(Collection::stream).collect(Collectors.toList());
	}

	/**
	 * filters에 해당하는 이미지 목록 조회
	 * filters에 값이 없으면 모든 인스턴스 목록 조회
	 *
	 * @param filters
	 */
	public List<Image> selectImageList(List<Filter> filters) {
		DescribeImagesRequest describeImagesRequest = new DescribeImagesRequest();
		describeImagesRequest.setFilters(filters);
		DescribeImagesResult describeImagesResult = ec2.describeImages(describeImagesRequest);

		return describeImagesResult.getImages();
	}

	public Instance selectInstance(String instanceId) {
		Instance instanceDetail = null;
		DescribeInstancesRequest describeInstancesRequest = new DescribeInstancesRequest();
		describeInstancesRequest.withInstanceIds(instanceId);
		DescribeInstancesResult describeInstancesResult = ec2.describeInstances(describeInstancesRequest);
		List<Reservation> reservations = describeInstancesResult.getReservations();

		if(!reservations.isEmpty()) {
			instanceDetail = reservations.get(0).getInstances().get(0);
		}

		return instanceDetail;
	}

	public Image createVolume(String instanceId) {
		Image image = null;
		Instance instance = selectInstance(instanceId);
		String imageId = instance.getImageId();
		DescribeImagesRequest describeImageRequest = new DescribeImagesRequest();
		describeImageRequest.withImageIds(imageId);
		DescribeImagesResult describeImageResult = ec2.describeImages(describeImageRequest);
		List<Image> reservations = describeImageResult.getImages();

		if(!reservations.isEmpty()) {
			//reservations =
		}

		CreateVolumeRequest createVolumeRequest = new CreateVolumeRequest();
		return null;
	}

	/**
	 * 볼륨 생성
	 *
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
	 * rootDeviceName
	 * rootDeviceType
	 */
	public void awsMoveOpenstack(List<Filter> filters) {
		List<Instance> instances = selectInstanceList(filters);
//		InstanceBlockDeviceMapping instanceBlockDeviceMapping = instances.stream().map(x -> x.getBlockDeviceMappings().stream().filter(y -> y.equals(x.getRootDeviceName())));
	}
}