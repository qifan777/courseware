package com.jarcheng.courseware.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FhUploadService {
    String upload(MultipartFile multipartFile) throws IOException;

    String uploadByPath(MultipartFile multipartFile, String path) throws IOException;

    int delete(String url);
}
