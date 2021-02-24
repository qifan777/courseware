package com.jarcheng.courseware.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.internal.OSSHeaders;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.StorageClass;
import com.jarcheng.courseware.oss.OSSInfo;
import com.jarcheng.courseware.service.FhUploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service("OSSService")
@Slf4j
public class OSSServiceImpl implements FhUploadService {
    @Autowired
    OSS oss;
    @Autowired
    OSSInfo ossInfo;

    @Override
    public String upload(MultipartFile multipartFile) throws IOException {
        String filename = multipartFile.getOriginalFilename();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String objectName = sdf.format(new Date()) + filename;
        return basicUpload(objectName,multipartFile.getInputStream());
    }

    @Override
    public String uploadByPath(MultipartFile multipartFile, String path) throws IOException {
        String filename = multipartFile.getOriginalFilename();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String objectName = path + "/" + sdf.format(new Date()) + filename;
        return basicUpload(objectName, multipartFile.getInputStream());
    }

    @Override
    public int delete(String url) {
        url = url.replace("/resource/", "");
        String[] splits = url.split("/");
        StringBuilder filename1 = new StringBuilder();
        for (int i = 0; i <= splits.length - 1; i++) {
            filename1.append("/").append(splits[i]);
        }
        String filename2 = filename1.substring(1, filename1.length());
        oss.deleteObject(ossInfo.getBucketName(), filename2);
        return 1;
    }

    public String basicUpload(String objectName, InputStream inputStream) {
        PutObjectRequest putObjectRequest = new PutObjectRequest(ossInfo.getBucketName(), objectName, inputStream);
        // 如果需要上传时设置存储类型与访问权限，请参考以下示例代码。
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
        metadata.setObjectAcl(CannedAccessControlList.PublicRead);
        putObjectRequest.setMetadata(metadata);
        oss.putObject(putObjectRequest);
        return "/resource/" + objectName;
    }
}
