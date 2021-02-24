package com.jarcheng.shiro.config.shiro;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jarcheng.shiro.exception.CustomException;
import com.jarcheng.shiro.utils.JWTUtils;
import com.jarcheng.mbg.common.RedisKey;
import com.jarcheng.mbg.common.ResultCode;
import com.jarcheng.mbg.dao.SysRoleMapper;
import com.jarcheng.mbg.dao.UserMapper;
import com.jarcheng.mbg.mapstruct.UserMapStruct;
import com.jarcheng.mbg.model.SysRole;
import com.jarcheng.mbg.model.User;
import com.jarcheng.mbg.model.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 起凡
 */
@Slf4j
public class UserRealm extends AuthorizingRealm {
    // 授权
    @Autowired
    UserMapper userDao;
    @Autowired
    SysRoleMapper roleMapper;
    @Autowired
    RedisTemplate<String, Object> redisTemplate;
    @Autowired
    UserMapStruct userMapStruct;

    /**
     * 大坑！，必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        //获取当前线程的用户信息,在认证的时候已经存起来了
        UserDto user = (UserDto) SecurityUtils.getSubject().getPrincipal();

        //把用户信息内的角色信息给shiro,到时候角色认证会和这里面的信息进行比较,判断用户是否拥有某个权限
        if (user.getPermissions() != null) {
            for (String per : user.getPermissions()) {
                authorizationInfo.addStringPermission(per);
            }
        }
        //把用户信息内的角色信息给shiro,到时候角色认证会和这里面的信息进行比较,判断用户是否拥有某个角色
        if (user.getRoles() != null) {
            user.getRoles().forEach(authorizationInfo::addRole);
        }
        return authorizationInfo;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String userToken = (String) authenticationToken.getCredentials();
        String password = (String) authenticationToken.getPrincipal();
        //获取是否存在token
        UserDto o = (UserDto) redisTemplate.opsForValue().get(RedisKey.TOKEN_KEY + userToken);
        //存在则直接将对应的用户信息返回,跳过下面的验证
        if (o != null) {
            return new SimpleAuthenticationInfo(o, userToken, getName());
        }
        //如果token过期,抛出异常
        if (JWTUtils.isExpire(userToken)) {
            throw new AuthenticationException(new CustomException(ResultCode.PERMISSION_EXPIRE));
        }
        //从token中的payload拿出username,用户名为空抛出异常
        String username = JWTUtils.getUsername(userToken);
        String phoneNumber = JWTUtils.getPhoneNumber(userToken);
        if (username == null && phoneNumber == null) {
            throw new AuthenticationException(new CustomException(ResultCode.PARAM_NOT_COMPLETE));
        }
        User userBean;
        //查询用户(mybatis-plus)
        if (phoneNumber == null) {
            userBean = userDao.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, username));
        } else {
            userBean = userDao.selectOne(Wrappers.<User>lambdaQuery().eq(User::getPhoneNumber, phoneNumber));

        }
        //如果用户不存在,抛出异常
        if (userBean == null) {

            throw new AuthenticationException(new CustomException(ResultCode.USER_NOT_EXIST));
        }
        //用户第一次登录,且数据库的密码与传来的密码相等。
        if (!password.equals("") && !userBean.getPassword().equals(password)) {
            throw new AuthenticationException(new CustomException(ResultCode.USER_LOGIN_ERROR));

        }
        //验证这个token是不是服务器签发的
        if (!JWTUtils.verify(userToken)) {
            throw new AuthenticationException(new CustomException(ResultCode.PERMISSION_TOKEN_INVALID));
        }

        //查询用户的权限
        UserDto userPermission = userDao.getUserPermission(userBean.getId());
        UserDto userDto = userMapStruct.toDto(userBean);
        //查询用户的角色
        List<SysRole> roles = roleMapper.listUserRoles(userBean.getId());
        if (userPermission != null) {
            if (!userPermission.getPermissions().isEmpty()) {
                userDto.setPermissions(userPermission.getPermissions());
            }
        }
        List<String> r = new ArrayList<>();
        if (roles != null) {
            roles.forEach((SysRole u) -> r.add(u.getName()));
            userDto.setRoles(r);
        }

        //保存到当前线程,后面可以通过 UserDto user = (UserDto) SecurityUtils.getSubject().getPrincipal();获取
        return new SimpleAuthenticationInfo(userDto, userToken, getName());
    }
}
