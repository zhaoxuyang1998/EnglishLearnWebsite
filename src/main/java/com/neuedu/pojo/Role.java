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
public class Role extends Bus {

    private static final long serialVersionUID = 1L;

    /**
     * 序号
     */
    private Integer number;

    /**
     * 角色名
     */
    private String rolename;

    /**
     * 提示
     */
    private String tips;


}
