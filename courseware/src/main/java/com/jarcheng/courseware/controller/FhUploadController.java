package com.jarcheng.courseware.controller;

import com.jarcheng.courseware.service.FhUploadService;
import com.jarcheng.mbg.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/fh-upload")
@Slf4j
public class FhUploadController {
    @Autowired
    FhUploadService uploadService;
    @PostMapping("/upload")
    public Result uploadImg(@RequestParam("file") MultipartFile file) {
        try {
            log.info("上传");
            String url = uploadService.upload(file);
            Map<String, String> urlMap = new HashMap<>();
            urlMap.put("url", url);
            return Result.SUCCESS(urlMap);
        } catch (Exception e) {
            return Result.FAIL("上传失败");
        }
    }

    @GetMapping("/deleteObject")
    public Result deleteObject(@RequestParam("url") String url) {
        uploadService.delete(url);
        return Result.SUCCESS();
    }
}
