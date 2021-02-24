package com.jarcheng.mbg.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * <p>
 *
 * </p>
 *
 * @author 起凡
 * @since 2021-02-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class CwOrder implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String orderSn;
    @NotNull(message = "课件id不能为空")
    private Integer cwId;

    private Integer userId;
    @NotNull(message = "价格不能为空")
    private BigDecimal price;

    private LocalDateTime createTime;

    private LocalDateTime payTime;

    private Boolean isPay;

    /**
     * 0->小程序
     */
    private Integer payType;
    private String wxOrder;
}
