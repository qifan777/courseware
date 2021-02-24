package com.jarcheng.mbg.dao;

import com.jarcheng.mbg.model.CwUserCourseware;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jarcheng.mbg.model.dto.CwUserCoursewareDto;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author 起凡
 * @since 2021-02-17
 */
@Repository
public interface CwUserCoursewareMapper extends BaseMapper<CwUserCourseware> {
    List<CwUserCoursewareDto> getMyCW(Integer id);



}
