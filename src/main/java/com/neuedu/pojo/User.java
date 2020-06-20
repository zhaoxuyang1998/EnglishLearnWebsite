package com.neuedu.pojo;

import com.neuedu.pojo.Bus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhao
 * @since 2020-03-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class User extends Bus {

    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
    private Integer roleNum;

    /**
     * 角色
     */
    private String rolename;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 密码盐
     */
    private String salt;

    /**
     * 标签顺序
     */
    private Integer tagNum;

    /**
     * 学习方向标签
     */
    private String tag;

    /**
     * 状态
     */
    private Integer status;


}
