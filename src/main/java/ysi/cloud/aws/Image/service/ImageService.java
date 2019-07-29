package ysi.cloud.aws.Image.service;

import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.DescribeImagesRequest;
import com.amazonaws.services.ec2.model.DescribeImagesResult;
import com.amazonaws.services.ec2.model.Filter;
import com.amazonaws.services.ec2.model.Image;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ysi.
 * User: ysi
 * Date: 2019-07-16
 * Time: 오후 1:27
 */
@Service
public class ImageService {
	final AmazonEC2 ec2 = AmazonEC2ClientBuilder.defaultClient();

	/**≠≠
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
}