package com.jarcheng.courseware.service.impl;


import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jarcheng.courseware.service.UserService;
import com.jarcheng.mbg.common.RedisKey;
import com.jarcheng.mbg.common.Result;
import com.jarcheng.mbg.common.ResultCode;
import com.jarcheng.mbg.dao.UserMapper;
import com.jarcheng.mbg.mapstruct.UserMapStruct;
import com.jarcheng.mbg.model.User;
import com.jarcheng.mbg.model.WXAuth;
import com.jarcheng.mbg.model.dto.UserDto;
import com.jarcheng.mbg.uitls.CommonUtils;
import com.jarcheng.shiro.config.shiro.JWTToken;
import com.jarcheng.shiro.utils.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 起凡
 * @since 2020-12-02
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    UserMapper userDao;
    @Autowired
    UserMapStruct userMapStruct;
    @Autowired
    CommonUtils commonUtils;
    @Autowired
    RedisTemplate<String, Object> redisTemplate;
    @Autowired
    OSSServiceImpl uploadService;
    @Value("${wxmini.secret}")
    String secret;
    @Value("${wxmini.appid}")
    String appid;

    @Override
    public UserDto login(UserDto user) {

        Subject subject = SecurityUtils.getSubject();
        String token = JWTUtils.sign("", user.getUsername(), user.getPhoneNumber());
        subject.login(new JWTToken(token, user.getPassword()));
        // 登录成功 封装用户信息到token
        UserDto userDto = (UserDto) SecurityUtils.getSubject().getPrincipal();
        userDto.setPassword("");
        userDto.setToken(JWTUtils.sign(userDto.getUuid(), userDto.getUsername(), userDto.getPhoneNumber()));
        //保存到redis内,下次就直接跳过验证
        redisTemplate.opsForValue().set(RedisKey.TOKEN_KEY + userDto.getToken(), userDto, 2, TimeUnit.HOURS);
        return userDto;
    }

    @Override
    public Result signUp(UserDto user) {
        JSONObject result = new JSONObject();
        String uuid = UUID.randomUUID().toString();
        user.setUuid(uuid);
        String stringRandom = commonUtils.getStringRandom(10);
        user.setUsername(stringRandom);
        user.setNickname(stringRandom);
        if (user.getPhoneNumber() != null) {
            User queryUser = userDao.selectOne(Wrappers.<User>lambdaQuery().eq(User::getPhoneNumber, user.getPhoneNumber()));
            if (queryUser == null) {
                userDao.insert(userMapStruct.toEntity(user));
                String token = JWTUtils.sign(uuid, user.getUsername(), user.getPhoneNumber());
                result.put("token", token);
                Result.SUCCESS(result);
                return Result.SUCCESS(result);
            } else {
                //已存在直接登录
                return Result.SUCCESS(login(userMapStruct.toDto(queryUser)));
            }
        } else {
            return new Result(ResultCode.PARAM_TYPE_BIND_ERROR);
        }
    }


    @Override
    public UserDto getUserInfo(String uuid, Boolean refresh) {
        UserDto userDto = (UserDto) SecurityUtils.getSubject().getPrincipal();
        //如果是自己获取自己的信息且刷新token
        if ("".equals(uuid) && refresh) {
            redisTemplate.delete(RedisKey.TOKEN_KEY + userDto.getToken());
            return login(userDto);
            //如果是自己获取自己的信息
        } else if ("".equals(uuid)) {
            userDto.setPassword("");
            return userDto;
        } else {
            //如果是获取他人的信息
            UserDto userInfo = userDao.getUserInfo(uuid);
            userInfo.setPhoneNumber("");
            userInfo.setPassword("");
            return userInfo;
        }
    }


    @Override
    public Result authLogin(WXAuth wxAuth) {
        try {
            String phoneRes = commonUtils.wxDecrypt(wxAuth.getEncryptedData(), wxAuth.getSessionId(), wxAuth.getIv());
            JSONObject jsonObject = JSONObject.parseObject(phoneRes);
            String phoneNumber = (String) jsonObject.get("phoneNumber");
            User user = userDao.selectOne(Wrappers.<User>lambdaQuery().eq(User::getPhoneNumber, phoneNumber));
            if (user != null) {
                user.setUsername(null);
                return Result.SUCCESS(this.login(userMapStruct.toDto(user)));
            } else {
                String initPassword = RandomUtil.randomString(10);
                UserDto userDto = new UserDto();
                userDto.setPhoneNumber(phoneNumber);
                userDto.setPassword(initPassword);
                return this.signUp(userDto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.FAIL();
    }

    @Override
    public Boolean initUserInfo(UserDto userDto) {
        userDto.setPassword(null);
        userDto.setUsername(null);
        userDao.updateById(userMapStruct.toEntity(userDto));
        return true;
    }

    @Override
    public String getSessionId(String code) {
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid={0}&secret={1}&js_code={2}&grant_type=authorization_code";
        url = url.replace("{0}", appid).replace("{1}", secret).replace("{2}", code);
        String res = HttpUtil.get(url);
        JSONObject jsonObject = JSONObject.parseObject(res);
        String s = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(RedisKey.WX_SESSION_ID + s, jsonObject);
        return s;
    }

    @Override
    public Result deleteUser(String uuid) {
        return null;
    }

    @Override
    public UserDto updateInfo(User user) {
        UserDto user1 = (UserDto) SecurityUtils.getSubject().getPrincipal();
        user.setId(user1.getId());
        userDao.updateById(user);
        User user2 = userDao.selectById(user.getId());
        user2.setPassword("");
        user2.setPhoneNumber("");
        return userMapStruct.toDto(user2);
    }
}