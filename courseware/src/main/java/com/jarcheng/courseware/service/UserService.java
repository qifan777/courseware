package com.jarcheng.courseware.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.jarcheng.mbg.common.Result;
import com.jarcheng.mbg.model.User;
import com.jarcheng.mbg.model.WXAuth;
import com.jarcheng.mbg.model.dto.UserDto;


/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 起凡
 * @since 2020-12-02
 */
public interface UserService extends IService<User> {
    UserDto login(UserDto user);

    Result signUp(UserDto user);

    Result authLogin(WXAuth wxAuth);

    UserDto getUserInfo(String uuid, Boolean refresh);
    Boolean initUserInfo(UserDto userDto);
    String getSessionId(String code);

    Result deleteUser(String uuid);

    UserDto updateInfo(User user);
}