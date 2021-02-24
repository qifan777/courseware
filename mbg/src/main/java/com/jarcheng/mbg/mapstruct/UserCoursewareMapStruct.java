package com.jarcheng.mbg.mapstruct;

import com.jarcheng.mbg.base.BaseMapper;
import com.jarcheng.mbg.model.CwUserCourseware;
import com.jarcheng.mbg.model.dto.CwUserCoursewareDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Repository;

@Repository
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)

public interface UserCoursewareMapStruct extends BaseMapper<CwUserCoursewareDto, CwUserCourseware> {
}
