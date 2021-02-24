package com.jarcheng.courseware.controller;


import com.alibaba.fastjson.JSONObject;
import com.jarcheng.courseware.service.CwOrderService;
import com.jarcheng.mbg.annotation.JwtIgnore;
import com.jarcheng.mbg.common.Result;
import com.jarcheng.mbg.model.CwOrder;
import com.yungouos.pay.util.PaySignUtil;
import com.yungouos.pay.wxpay.WxPay;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 起凡
 * @since 2021-02-19
 */
@RestController
@RequestMapping("/cw-order")
@Slf4j
public class CwOrderController {
    @Autowired
    CwOrderService cwOrderService;

    @PostMapping("/create")
    public Result createOrder(@RequestBody CwOrder cwOrder) {

        return Result.SUCCESS(cwOrderService.createOrder(cwOrder));
    }

    @JwtIgnore
    @PostMapping("/callback")
    public String asyncCallBack(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        JSONObject jsonObject = new JSONObject();
        for (String key : parameterMap.keySet()) {
            String[] strings = parameterMap.get(key);
            jsonObject.put(key, strings[0]);
        }
        log.info(jsonObject.toJSONString());
        boolean b = cwOrderService.callBack(jsonObject, request);
        return b ? "SUCCESS" : null;
    }
}

