package ysi.cloud.aws.task.imageImport;

import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.AmazonEC2Exception;
import com.amazonaws.services.ec2.model.DescribeImportImageTasksRequest;
import com.amazonaws.services.ec2.model.DescribeImportImageTasksResult;
import org.springframework.stereotype.Service;

@Service
public class ImageImportTaskService {
    private static final AmazonEC2 amazonEC2 = AmazonEC2ClientBuilder.defaultClient();

    public DescribeImportImageTasksResult getDescribeImagesResult(DescribeImportImageTasksRequest request) {
        try {
            return amazonEC2.describeImportImageTasks(request);
        } catch(AmazonEC2Exception e) {
            return null;
        }
    }
}