package com.jarcheng.courseware;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.jarcheng")
@MapperScan({"com.jarcheng.mbg.dao"})
public class CoursewareApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoursewareApplication.class, args);
    }

}
