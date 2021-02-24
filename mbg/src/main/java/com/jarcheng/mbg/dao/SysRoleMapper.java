package com.jarcheng.mbg.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jarcheng.mbg.model.SysRole;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author 起凡
 * @since 2020-12-02
 */
@Repository

public interface SysRoleMapper extends BaseMapper<SysRole> {
    List<SysRole> listUserRoles(Integer userId);
}
