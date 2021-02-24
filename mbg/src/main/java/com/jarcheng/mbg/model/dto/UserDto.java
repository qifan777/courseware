package com.jarcheng.mbg.model.dto;


import com.jarcheng.mbg.annotation.CheckPhone;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String nickname;
    //更新的时候可以为null(代表不更新)
    private String username;
    private String uuid;
    @NotNull
    private String password;
    private String gender;
    @CheckPhone
    @NotNull
    private String phoneNumber;
    /**
     * 背景图片
     */
    private String background;
    private String portrait;

    //dto拓展属性
    private String token;
    List<String> permissions;
    List<String> roles;
    //验证码
    private String code;

}
