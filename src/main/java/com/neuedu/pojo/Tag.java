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
 * @since 2020-03-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Tag extends Bus {

    private static final long serialVersionUID = 1L;

    private Integer articleId;

    /**
     * 标签序号
     */
    private Integer tagNum;

    /**
     * 标签名
     */
    private String tagName;


}
