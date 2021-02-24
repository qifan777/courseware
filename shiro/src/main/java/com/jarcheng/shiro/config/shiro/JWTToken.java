package com.jarcheng.shiro.config.shiro;

import org.apache.shiro.authc.AuthenticationToken;

public class JWTToken implements AuthenticationToken {

    private String token;
    private String password;

    public JWTToken(String token) {
        this(token, "");
    }

    public JWTToken(String token, String password) {
        this.token = token;
        this.password = password;
    }

    @Override
    public Object getPrincipal() {
        return password;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
