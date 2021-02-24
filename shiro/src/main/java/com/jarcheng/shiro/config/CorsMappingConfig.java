package com.jarcheng.shiro.config;

import com.jarcheng.shiro.fillder.JwtInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class CorsMappingConfig implements WebMvcConfigurer {
    //
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        String mapping = "/**"; // 所有请求，也可配置成特定请求，如/api/**
//        String origins = "*"; // 所有来源，也可以配置成特定的来源才允许跨域，如http://www.xxxx.com
//        String methods = "*"; // 所有方法，GET、POST、PUT等
//        registry.addMapping(mapping).allowedOrigins(origins).allowedMethods(methods);
//    }
    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
            "classpath:/META-INF/resources/", "classpath:/resources/",
            "classpath:/static/", "classpath:/public/"};

    /**
     * 跨域支持
     *
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "DELETE", "PUT", "PATCH", "OPTIONS", "HEAD")
                .maxAge(3600 * 24);
    }

    /**
     * 添加拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String[] SWAGGER_WHITELIST = {
                "/swagger-ui.html",
                "/swagger-ui/*",
                "/swagger-resources/**",
                "/v2/api-docs",
                "/v3/api-docs",
                "/webjars/**"
        };

        //拦截路径可自行配置多个 可用 ，分隔开
        InterceptorRegistration interceptorRegistration = registry.addInterceptor(new JwtInterceptor());
        interceptorRegistration.addPathPatterns("/**")
                .excludePathPatterns(SWAGGER_WHITELIST);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (!registry.hasMappingForPattern("/webjars/**")) {
            registry.addResourceHandler("/webjars/**").addResourceLocations(
                    "classpath:/META-INF/resources/webjars/");
        }
        if (!registry.hasMappingForPattern("/**")) {
            registry.addResourceHandler("/**").addResourceLocations(
                    CLASSPATH_RESOURCE_LOCATIONS);
        }

    }
}