package com.jarcheng.courseware.controller;


import com.alibaba.fastjson.JSONObject;
import com.jarcheng.courseware.service.UserService;
import com.jarcheng.mbg.annotation.CheckPhone;
import com.jarcheng.mbg.annotation.JwtIgnore;
import com.jarcheng.mbg.common.Result;
import com.jarcheng.mbg.common.ResultCode;
import com.jarcheng.mbg.model.User;
import com.jarcheng.mbg.model.WXAuth;
import com.jarcheng.mbg.model.dto.UserDto;
import com.jarcheng.mbg.uitls.CommonUtils;
import com.yungouos.pay.entity.WxOauthInfo;
import com.yungouos.pay.wxpay.WxPay;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 起凡
 * @since 2020-12-02
 */
@RestController
@RequestMapping("/user")
@Slf4j
@Validated
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    CommonUtils commonUtils;

    @PostMapping("/login")
    @JwtIgnore
    public Result login(@RequestBody UserDto user) {
        try {
            return Result.SUCCESS(userService.login(user));
        } catch (UnknownAccountException e) {
            return new Result(ResultCode.USER_NOT_EXIST);
        } catch (IncorrectCredentialsException e) {
            return new Result(ResultCode.USER_LOGIN_ERROR);
        }
    }

    @PostMapping("/signUp")
    @JwtIgnore
    public Result SignUp(@RequestBody @Validated UserDto userDto) {
        return userService.signUp(userDto);
    }

    @GetMapping("/userinfo")
    public Result getUserInfo(@RequestParam(value = "uuid", defaultValue = "") String uuid,
                              @RequestParam(required = false, defaultValue = "false") Boolean refresh) {
        return Result.SUCCESS(userService.getUserInfo(uuid, refresh));
    }

    @PostMapping("/updateUser")
    public Result updateUser(@RequestBody User user) {
        UserDto user1 = (UserDto) SecurityUtils.getSubject().getPrincipal();
        if (user1.getId().equals(user.getId())) {
            //防止用户修改其他重要信息
            user.setPassword(null);
            user.setPhoneNumber(null);
            user.setUuid(null);
            user.setId(user1.getId());
            return Result.SUCCESS(userService.updateInfo(user));
        } else {
            return new Result(ResultCode.PERMISSION_UNAUTHORISE);
        }
    }


    @JwtIgnore
    @PostMapping("/authLogin")
    public Result authLogin(@RequestBody WXAuth wxAuth) {
        return userService.authLogin(wxAuth);
    }

    @JwtIgnore
    @GetMapping("/getSessionId")
    public Result getSessionId(@RequestParam String code) {
        String sessionId = userService.getSessionId(code);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("sessionId", sessionId);
        return Result.SUCCESS(hashMap);
    }

    @PostMapping("/initUserInfo")
    public Result initUserInfo(@RequestBody UserDto user) {
        return Result.SUCCESS(userService.initUserInfo(user));
    }
}

