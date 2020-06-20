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
 * @since 2020-04-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Vocab extends Bus {

    private static final long serialVersionUID = 1L;

    /**
     * 词表名称
     */
    private String vocabName;

    /**
     * 词表标签
     */
    private Integer tagNum;

    private String coverFileLocation;

    /**
     * 标签名称
     */
    private String tag;

    /**
     * 状态
     */
    private Integer status;


}
