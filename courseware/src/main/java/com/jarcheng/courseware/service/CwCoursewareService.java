package com.jarcheng.courseware.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jarcheng.mbg.model.CwCourseware;
import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.models.auth.In;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 起凡
 * @since 2021-02-17
 */
public interface CwCoursewareService extends IService<CwCourseware> {
     boolean addCW(CwCourseware cwCourseware);
     boolean updateCW(CwCourseware cwCourseware);
     boolean deleteCW(Integer id);
     CwCourseware getCW(Integer id);
     IPage<CwCourseware> listCW(Integer start);
     List<CwCourseware> getCarousel();
}
