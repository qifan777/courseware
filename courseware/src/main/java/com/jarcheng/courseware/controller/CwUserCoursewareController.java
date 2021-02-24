package com.jarcheng.courseware.controller;


import com.alibaba.fastjson.JSONObject;
import com.jarcheng.courseware.service.impl.CwUserCoursewareServiceImpl;
import com.jarcheng.mbg.annotation.JwtIgnore;
import com.jarcheng.mbg.common.Result;
import com.yungouos.pay.wxpay.WxPay;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 起凡
 * @since 2021-02-17
 */
@RestController
@RequestMapping("/cw-user-courseware")
@Slf4j
public class CwUserCoursewareController {
    @Autowired
    CwUserCoursewareServiceImpl userCoursewareService;

    @GetMapping("/myCourseware")
    public Result getMyCourseware() {
        return Result.SUCCESS(userCoursewareService.getMyCW());
    }

}

