package com.jarcheng.courseware.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jarcheng.mbg.dao.CwUserCoursewareMapper;
import com.jarcheng.mbg.model.CwExchangeKey;
import com.jarcheng.mbg.dao.CwExchangeKeyMapper;
import com.jarcheng.courseware.service.CwExchangeKeyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jarcheng.mbg.model.CwUserCourseware;
import com.jarcheng.mbg.model.dto.UserDto;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 起凡
 * @since 2021-02-20
 */
@Service
public class CwExchangeKeyServiceImpl extends ServiceImpl<CwExchangeKeyMapper, CwExchangeKey> implements CwExchangeKeyService {
    @Autowired
    CwUserCoursewareMapper userCoursewareMapper;

    @Override
    @Transactional
    public boolean use(String key) {
        CwExchangeKey cwExchangeKey = getOne(Wrappers.<CwExchangeKey>lambdaQuery().eq(CwExchangeKey::getExKey, key));
        if (cwExchangeKey != null && !cwExchangeKey.getIsUsed()) {
            UserDto userDto = (UserDto) SecurityUtils.getSubject().getPrincipal();
            CwUserCourseware cwUserCourseware1 = userCoursewareMapper.selectOne(Wrappers.<CwUserCourseware>lambdaQuery().eq(CwUserCourseware::getCwId, cwExchangeKey.getCwId()).eq(CwUserCourseware::getUserId, userDto.getId()));
            if (cwUserCourseware1 == null) {
                CwUserCourseware cwUserCourseware = new CwUserCourseware();
                cwUserCourseware.setCwId(cwExchangeKey.getCwId());
                cwUserCourseware.setUserId(userDto.getId());
                userCoursewareMapper.insert(cwUserCourseware);
                cwExchangeKey.setIsUsed(true);
                cwExchangeKey.setUserId(userDto.getId());
                cwExchangeKey.setUseTime(LocalDateTime.now());
                updateById(cwExchangeKey);
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
}
