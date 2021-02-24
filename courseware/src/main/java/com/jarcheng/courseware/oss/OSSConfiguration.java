package com.jarcheng.courseware.oss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OSSConfiguration {
    @Autowired
    OSSInfo ossInfo;

    @Bean
    public OSS getOSSClient() {
        return new OSSClientBuilder().build(ossInfo.getEndpoint(), ossInfo.getAccessKeyId(), ossInfo.getAccessKeySecret());
    }
}
