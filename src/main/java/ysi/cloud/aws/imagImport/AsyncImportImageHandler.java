package ysi.cloud.aws.imagImport;


import com.amazonaws.handlers.AsyncHandler;
import com.amazonaws.services.ec2.model.DescribeImportImageTasksRequest;
import com.amazonaws.services.ec2.model.DescribeImportImageTasksResult;

public class AsyncImportImageHandler implements AsyncHandler<DescribeImportImageTasksRequest, DescribeImportImageTasksResult> {

    @Override
    public void onError(Exception e) {
    }

    @Override
    public void onSuccess(DescribeImportImageTasksRequest request, DescribeImportImageTasksResult describeImportImageTasksResult) {
    }

}