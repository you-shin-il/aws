package ysi.cloud.aws.instance.service;

import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.*;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by ysi.
 * User: ysi
 * Date: 2019-07-16
 * Time: 오후 1:28
 */
@Service
public class InstanceService {
	private final AmazonEC2 ec2 = AmazonEC2ClientBuilder.defaultClient();

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
	 * rootDeviceName 찾기
	 *
	 * @param blockDeviceMappings
	 * @param rootDeviceName
	 */
	public Optional<String> selectRootDeviceName(List<InstanceBlockDeviceMapping> blockDeviceMappings, String rootDeviceName) {
		return blockDeviceMappings.stream()
				.filter(x -> x.getDeviceName().equals(rootDeviceName))
				.map(InstanceBlockDeviceMapping::getDeviceName)
				.findFirst();
	}
}