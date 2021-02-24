package com.jarcheng.mbg.model;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户角色关联
 * </p>
 *
 * @author 起凡
 * @since 2021-02-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysUsersRoles implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 角色ID
     */
    private Integer roleId;


}
