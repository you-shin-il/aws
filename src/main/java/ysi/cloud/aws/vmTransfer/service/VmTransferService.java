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
public class VmTransferService {

	@Autowired
	private InstanceService instanceService;

	@Autowired
	private ImageService imageService;

	@Autowired
	private VolumeService volumeService;

	@Autowired
	private VmTransferService transferService;

	/**
	 * 인스턴스 이전
	 * 1. 이전 대상 인스턴스 조회
	 * 2. 볼륨 생성
	 * 3. 볼륨 연결
	 * 4. 볼륨 초기화
	 * 5. 볼륨 mount
	 * 6. 인스턴스 이미지 전송
	 * @param filters
	 */
	public void awsMoveOpenstack(List<Filter> filters) {
		List<Instance> instances = instanceService.selectInstanceList(filters);
		instances.toString();
		instances.stream().forEach(x ->  {
			Optional<String> rootDeviceName = instanceService.selectRootDeviceName(x.getBlockDeviceMappings(),x.getRootDeviceName());//루트디바이스이름 TODO 루트디바이스명 존재여부 확인 필요 없을 경우 삭제 할 것

			if(rootDeviceName.isPresent()) {//루트디바이스이름이 존재 할 경우 TODO 루트디바이스명 존재여부 확인 필요 없을 경우 삭제 할 것
				List<InstanceBlockDeviceMapping> instanceBlockDeviceMappingList = x.getBlockDeviceMappings();
Optional<EbsInstanceBlockDevice> ebsInstanceBlockDevice = selectEbsInstanceBlockDevice(instanceBlockDeviceMappingList, rootDeviceName.get());

				if(ebsInstanceBlockDevice.isPresent()) {//루트디바이스 볼륨 찾기
		Volume rootVolume = volumeService.getRootDeviceVolume(ebsInstanceBlockDevice.get());//루트 디바이스 볼륨
		Volume attachVolume = createVolume(rootVolume);//인스턴스에 연결할 볼륨 생성
		AttachVolumeRequest attachVolumeRequest = new AttachVolumeRequest(attachVolume.getVolumeId(), x.getInstanceId(), "/dev/xvdp");
		AttachVolumeResult attachVolumeResult = volumeService.volumeAttach(attachVolumeRequest);//볼륨 연결
	}
}

		});
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

	/**
	 * 볼륨 생성
	 *
	 * @param volume
	 */
	public Volume createVolume(Volume volume) {
		CreateVolumeRequest createVolumeRequest = new CreateVolumeRequest();
		createVolumeRequest.setSize(volume.getSize() + 5);//원본 볼륨보다 5GB 크게 설정
		createVolumeRequest.setAvailabilityZone(volume.getAvailabilityZone());//
		createVolumeRequest.setVolumeType(volume.getVolumeType());
		createVolumeRequest.setEncrypted(false);
		createVolumeRequest.setVolumeType(VolumeType.Gp2);

		List<Volume> result = volumeService.createVolume(Arrays.asList(createVolumeRequest));

		return result.stream().findFirst().get();
	}

	/**
	 * 볼륨 초기화 쉘 스크립트 실행
	 *
	 * @param device
	 * @param keyName
	 * @param publicIp
	 */
	public void execVolumeInitShellScript(String publicIp, String keyName, String device) {

	}

}