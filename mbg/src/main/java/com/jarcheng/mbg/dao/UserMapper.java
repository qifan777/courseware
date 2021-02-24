package com.jarcheng.mbg.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jarcheng.mbg.model.User;
import com.jarcheng.mbg.model.dto.UserDto;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author 起凡
 * @since 2020-12-02
 */
@Repository
public interface UserMapper extends BaseMapper<User> {
    UserDto getUserPermission(Integer userId);
    UserDto getUserInfo(String uuid);
}
