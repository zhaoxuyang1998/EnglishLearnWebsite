package com.neuedu.pojo;

import com.neuedu.pojo.Bus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 字典表
 * </p>
 *
 * @author zhao
 * @since 2020-03-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Dict extends Bus {

    private static final long serialVersionUID = 1L;

    /**
     * 排序
     */
    private Integer num;

    /**
     * 父级字典
     */
    private Integer pid;

    /**
     * 名称
     */
    private String name;

    /**
     * 提示
     */
    private String tips;

    /**
     * 值
     */
    private String code;


}
