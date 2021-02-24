package com.jarcheng.shiro.fillder;


import com.jarcheng.shiro.config.shiro.JWTToken;
import com.jarcheng.shiro.exception.CustomException;
import com.jarcheng.shiro.utils.JWTUtils;
import com.jarcheng.mbg.annotation.JwtIgnore;
import com.jarcheng.mbg.common.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ========================
 * token验证拦截器
 * <p>
 * ========================
 */
@Slf4j
public class JwtInterceptor extends HandlerInterceptorAdapter {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws CustomException {
//         忽略带JwtIgnore注解的请求, 不做后续token认证校验
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            if (handlerMethod.getBeanType().isAnnotationPresent(JwtIgnore.class)) {
                return true;
            }
            JwtIgnore jwtIgnore = handlerMethod.getMethodAnnotation(JwtIgnore.class);
            if (jwtIgnore != null) {
                return true;
            }
            // 获取请求头信息authorization信息
            final String authHeader = request.getHeader(JWTUtils.AUTH_HEADER_KEY);

            if (StringUtils.isEmpty(authHeader) || !authHeader.startsWith(JWTUtils.TOKEN_PREFIX)) {
                log.info("### 用户未登录，请先登录 ###");
                throw new CustomException(ResultCode.USER_NOT_LOGGED_IN);
            }

            // 获取token
            final String token = authHeader.substring(7); //去除 “Bearer "
            // 验证token是否有效--无效已做异常抛出，由全局异常处理后返回对应信息
            SecurityUtils.getSubject().login(new JWTToken(token,""));
            return true;
        }
        return true;
    }

}
