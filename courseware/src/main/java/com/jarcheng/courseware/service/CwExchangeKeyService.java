package com.jarcheng.courseware.service;

import com.jarcheng.mbg.model.CwExchangeKey;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 起凡
 * @since 2021-02-20
 */
public interface CwExchangeKeyService extends IService<CwExchangeKey> {
    boolean use(String key);
}
