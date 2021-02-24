package com.jarcheng.courseware.service;

import com.github.pagehelper.PageInfo;
import com.jarcheng.mbg.model.CwUserCourseware;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jarcheng.mbg.model.dto.CwUserCoursewareDto;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 起凡
 * @since 2021-02-17
 */
public interface CwUserCoursewareService extends IService<CwUserCourseware> {
    List<CwUserCoursewareDto> getMyCW();

}
