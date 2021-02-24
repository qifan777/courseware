package com.jarcheng.courseware.service;

import com.alibaba.fastjson.JSONObject;
import com.jarcheng.mbg.model.CwOrder;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 起凡
 * @since 2021-02-19
 */
public interface CwOrderService extends IService<CwOrder> {
    JSONObject createOrder(CwOrder cwOrder);
    boolean callBack(JSONObject callback, HttpServletRequest request);
}
