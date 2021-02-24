package com.jarcheng.courseware.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jarcheng.mbg.dao.CwCoursewareMapper;
import com.jarcheng.mbg.dao.CwUserCoursewareMapper;
import com.jarcheng.mbg.model.CwCourseware;
import com.jarcheng.mbg.model.CwOrder;
import com.jarcheng.mbg.dao.CwOrderMapper;
import com.jarcheng.courseware.service.CwOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jarcheng.mbg.model.CwUserCourseware;
import com.jarcheng.mbg.model.dto.UserDto;
import com.yungouos.pay.util.PaySignUtil;
import com.yungouos.pay.wxpay.WxPay;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 起凡
 * @since 2021-02-19
 */
@Service
@Slf4j
public class CwOrderServiceImpl extends ServiceImpl<CwOrderMapper, CwOrder> implements CwOrderService {
    @Autowired
    CwCoursewareMapper cwCoursewareMapper;
    @Autowired
    CwOrderMapper cwOrderMapper;
    @Autowired
    CwUserCoursewareMapper cwUserCoursewareMapper;
    @Value("${wxpay.mchid}")
    String mchid;
    @Value("${wxpay.key}")
    String key;

    @Override
    @Transactional
    public JSONObject createOrder(CwOrder cwOrder) {
        UserDto userDto = (UserDto) SecurityUtils.getSubject().getPrincipal();
        CwUserCourseware cwUserCourseware = cwUserCoursewareMapper.selectOne(Wrappers.<CwUserCourseware>lambdaQuery().eq(CwUserCourseware::getUserId, userDto.getId()).eq(CwUserCourseware::getCwId, cwOrder.getCwId()));
        if (cwUserCourseware == null) {
            cwOrder.setIsPay(null);
            cwOrder.setCreateTime(null);
            cwOrder.setPayTime(null);
            cwOrder.setId(null);
            CwCourseware cwCourseware = cwCoursewareMapper.selectById(cwOrder.getCwId());
            cwOrder.setOrderSn(UUID.randomUUID().toString());
            cwOrder.setPrice(cwCourseware.getPrice());
            cwOrder.setUserId(userDto.getId());
            cwOrderMapper.insert(cwOrder);
            return WxPay.minAppPay(cwOrder.getOrderSn(), "" + cwOrder.getPrice(), mchid, "购买课件ID为:"+cwOrder.getCwId(), "初七课件", null, "https://www.jarcheng.top/cw-api/cw-order/callback", null, null, null, key);
        } else {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("message", "已经购买请勿重复购买");
            return jsonObject;
        }
    }

    @Override
    @Transactional
    public boolean callBack(JSONObject callback, HttpServletRequest request) {
        try {
            log.info("正在进行签名校验");
            boolean b = PaySignUtil.checkNotifySign(request, key);
            if (b) {
                String wxOrder = (String) callback.get("orderNo");
                log.info("获取微信订单" + wxOrder);
                CwOrder outTradeNo = cwOrderMapper.selectOne(Wrappers.<CwOrder>lambdaQuery().eq(CwOrder::getOrderSn, callback.get("outTradeNo")));
                log.info("用户的订单" + outTradeNo.toString());
                CwCourseware cwCourseware = cwCoursewareMapper.selectById(outTradeNo.getCwId());
                cwCourseware.setCount(cwCourseware.getCount()+1);
                cwCoursewareMapper.updateById(cwCourseware);
                outTradeNo.setIsPay(true);
                outTradeNo.setPayTime(LocalDateTime.now());
                outTradeNo.setPayType(0);
                outTradeNo.setWxOrder(wxOrder);
                CwUserCourseware cwUserCourseware = new CwUserCourseware();
                cwUserCourseware.setCwId(outTradeNo.getCwId());
                cwUserCourseware.setUserId(outTradeNo.getUserId());
                cwUserCoursewareMapper.insert(cwUserCourseware);
                cwOrderMapper.updateById(outTradeNo);
                log.info("购买成功");
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
