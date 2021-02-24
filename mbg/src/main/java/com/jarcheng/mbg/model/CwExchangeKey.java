package com.jarcheng.mbg.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author 起凡
 * @since 2021-02-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class CwExchangeKey implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String exKey;

    private Integer cwId;

    private Boolean isUsed;

    private LocalDateTime createTime;

    private LocalDateTime useTime;

    private Integer userId;


}
