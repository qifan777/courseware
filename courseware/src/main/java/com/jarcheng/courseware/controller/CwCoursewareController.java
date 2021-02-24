package com.jarcheng.courseware.controller;


import com.jarcheng.courseware.service.CwCoursewareService;
import com.jarcheng.mbg.annotation.JwtIgnore;
import com.jarcheng.mbg.common.CommonPage;
import com.jarcheng.mbg.common.RedisKey;
import com.jarcheng.mbg.common.Result;
import com.jarcheng.mbg.model.CwCourseware;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.annotation.Validated;
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
@RequestMapping("/cw-courseware")
public class CwCoursewareController {
    @Autowired
    CwCoursewareService coursewareService;
    @Autowired
    StringRedisTemplate redisTemplate;

    @PostMapping("/add")
    @RequiresRoles("管理员")
    public Result addCourseWare(@RequestBody @Validated CwCourseware cwCourseware) {

        return Result.SUCCESS(coursewareService.addCW(cwCourseware));
    }

    @PostMapping("/update")
    @RequiresRoles("管理员")
    public Result updateCourseWare(@RequestBody @Validated CwCourseware cwCourseware) {
        return Result.SUCCESS(coursewareService.updateCW(cwCourseware));
    }

    @GetMapping("/delete")
    @RequiresRoles("管理员")
    public Result deleteCourseWare(@RequestParam Integer id) {
        return Result.SUCCESS(coursewareService.deleteCW(id));
    }

    @GetMapping("/get")
    public Result getCourseWare(@RequestParam Integer id) {
        return Result.SUCCESS(coursewareService.getCW(id));
    }

    @JwtIgnore
    @GetMapping("/list")
    public Result listCourseWare(@RequestParam Integer start) {
        return Result.SUCCESS(CommonPage.restPage(coursewareService.listCW(start)));
    }
    @GetMapping("/listForAdmin")
    public Result listCourseWareByAdmin(@RequestParam Integer start) {
        return Result.SUCCESS(CommonPage.restPage(coursewareService.listCW(start)));
    }

    @JwtIgnore
    @GetMapping("/getCarousel")
    public Result getCarousel() {
        return Result.SUCCESS(coursewareService.getCarousel());
    }
    @GetMapping("/getCarouselForAdmin")
    public Result getCarouselForAdmin() {
        return Result.SUCCESS(coursewareService.getCarousel());
    }


}

