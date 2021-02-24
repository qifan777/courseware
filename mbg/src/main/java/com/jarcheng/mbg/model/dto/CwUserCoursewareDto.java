package com.jarcheng.mbg.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jarcheng.mbg.model.CwCourseware;
import com.jarcheng.mbg.model.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CwUserCoursewareDto {
    private Integer id;

    private Integer userId;
    private User user;
    private CwCourseware courseware;
    private Integer cwId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")

    private LocalDateTime createTime;
}
