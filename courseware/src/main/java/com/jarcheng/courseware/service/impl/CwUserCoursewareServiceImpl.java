package com.jarcheng.courseware.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jarcheng.mbg.model.CwUserCourseware;
import com.jarcheng.mbg.dao.CwUserCoursewareMapper;
import com.jarcheng.courseware.service.CwUserCoursewareService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jarcheng.mbg.model.dto.CwUserCoursewareDto;
import com.jarcheng.mbg.model.dto.UserDto;
import io.swagger.models.auth.In;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 起凡
 * @since 2021-02-17
 */
@Service
public class CwUserCoursewareServiceImpl extends ServiceImpl<CwUserCoursewareMapper, CwUserCourseware> implements CwUserCoursewareService {
    @Autowired
    CwUserCoursewareMapper cwUserCoursewareMapper;

    @Override
    public List<CwUserCoursewareDto> getMyCW() {
        UserDto userDto = (UserDto) SecurityUtils.getSubject().getPrincipal();
        return cwUserCoursewareMapper.getMyCW(userDto.getId());
    }
}
