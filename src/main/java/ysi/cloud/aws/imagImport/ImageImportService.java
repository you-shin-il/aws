package ysi.cloud.aws.imagImport;

import com.amazonaws.services.ec2.model.DescribeImportImageTasksRequest;
import com.amazonaws.services.ec2.model.DescribeImportImageTasksResult;
import com.amazonaws.services.ec2.model.ImportImageTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ysi.cloud.aws.task.imageImport.ImageImportTaskService;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class ImageImportService {
    public static final Logger LOGGER = LoggerFactory.getLogger(ImageImportService.class);
    private static final String imageImportTaskFormat = "Image Import Tasks : ImportTaskId[%s], status[%s], Progress[%s], StatusMessage[%s]";

    @Autowired
    private ImageImportTaskService imageImportTaskService;

    public void importTask(String importTaskId) throws ExecutionException, InterruptedException {
        DescribeImportImageTasksRequest request = new DescribeImportImageTasksRequest();
        request.withImportTaskIds(importTaskId);

        while(true) {
            DescribeImportImageTasksResult describeImportImageTasksResult = imageImportTaskService.getDescribeImagesResult(request);

            if(describeImportImageTasksResult == null) {
                LOGGER.info("importImageTaskList empty");
                break;
            }

            //List<ImportImageTask> importImageTaskList = describeImportImageTasksResult.getImportImageTasks();
            ImportImageTask importImageTask = describeImportImageTasksResult.getImportImageTasks().get(0);

            if("deleted".equals(importImageTask.getStatus()) || "completed".equals(importImageTask.getStatus())) {
                LOGGER.info("importImageTask.getStatus : " + importImageTask.getStatus());
                break;
            }

            System.out.println(String.format(imageImportTaskFormat, importTaskId, importImageTask.getStatus(), importImageTask.getProgress(), importImageTask.getStatusMessage()));

            try {
                Thread.sleep(5000);
            } catch (InterruptedException ie) {
                LOGGER.info("Thread.sleep() was interrupted!");
            }
        }
        LOGGER.debug("importTask while END");
    }
}