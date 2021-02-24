package com.jarcheng.mbg.mapstruct;

import com.jarcheng.mbg.base.BaseMapper;
import com.jarcheng.mbg.model.User;
import com.jarcheng.mbg.model.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Repository;

@Repository
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapStruct extends BaseMapper<UserDto, User> {
}
