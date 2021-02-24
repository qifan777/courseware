package com.jarcheng.courseware.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jarcheng.courseware.service.CwExchangeKeyService;
import com.jarcheng.courseware.service.CwUserCoursewareService;
import com.jarcheng.mbg.common.Result;
import com.jarcheng.mbg.dao.CwUserCoursewareMapper;
import com.jarcheng.mbg.model.CwExchangeKey;
import com.jarcheng.mbg.model.CwUserCourseware;
import com.jarcheng.mbg.model.dto.UserDto;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 起凡
 * @since 2021-02-20
 */
@RestController
@RequestMapping("/cw-exchange-key")
public class CwExchangeKeyController {
    @Autowired
    CwExchangeKeyService cwExchangeKeyService;
    @Autowired
    CwUserCoursewareMapper userCoursewareMapper;
    @GetMapping("/list")
    @RequiresRoles("管理员")
    public Result listKey(@RequestParam Integer start) {
        PageHelper.startPage(start, 10);
        List<CwExchangeKey> list = cwExchangeKeyService.list();
        PageInfo<CwExchangeKey> pageInfo = new PageInfo<>(list);
        return Result.SUCCESS(pageInfo);
    }

    @GetMapping("/add")
    @RequiresRoles("管理员")
    public Result add(@RequestParam Integer id) {
        CwExchangeKey build = CwExchangeKey.builder().cwId(id).exKey(UUID.randomUUID().toString()).build();
        cwExchangeKeyService.save(build);
        return Result.SUCCESS(true);
    }

    @GetMapping("/use")
    public Result use(@RequestParam String key) {
        return Result.SUCCESS(cwExchangeKeyService.use(key));
    }
    @GetMapping("/delete")
    @RequiresRoles("管理员")
    public Result delete(@RequestParam Integer id){
        cwExchangeKeyService.removeById(id);
        return Result.SUCCESS(true);
    }
}

