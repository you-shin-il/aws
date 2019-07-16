package ysi.cloud.aws.Image.web;

import com.amazonaws.services.ec2.model.Filter;
import com.amazonaws.services.ec2.model.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ysi.cloud.aws.Image.service.ImageService;

import java.util.List;

/**
 * Created by ysi.
 * User: ysi
 * Date: 2019-07-15
 * Time: 오후 3:40
 */
@RestController
public class ImageController {
	@Autowired
	private ImageService imageService;

	/**
	 * filters에 해당하는 이미지 목록 조회
	 * filters에 값이 없으면 모든 인스턴스 목록 조회
	 *
	 * @param filters
	 */
	@GetMapping("/aws/ec2/image/list")
	public List<Image> selectImageList(@RequestBody List<Filter> filters) {
		return imageService.selectImageList(filters);
	}
}