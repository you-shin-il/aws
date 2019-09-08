package ysi.cloud.aws.imagImport.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ysi.cloud.aws.imagImport.ImageImportService;

import java.util.concurrent.ExecutionException;

@Controller
public class ImageImportController {
    @Autowired
    private ImageImportService imageImportService;

    @RequestMapping("/image/import/task")
    public void task(@RequestParam("importTaskId") String importTaskId) throws ExecutionException, InterruptedException {
        imageImportService.importTask(importTaskId);
    }
}