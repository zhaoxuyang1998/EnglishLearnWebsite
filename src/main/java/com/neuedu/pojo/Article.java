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
 * @since 2020-04-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Article extends Bus {

    private static final long serialVersionUID = 1L;

    /**
     * 标题
     */
    private String title;

    /**
     * 标签
     */
    private String tags;

    /**
     * 词典ID
     */
    private String coverFileLocation;

    /**
     * word文件位置
     */
    private String wrodFileLocation;

    /**
     * pdf文件位置
     */
    private String pdfFileLocation;

    private Integer status;


}
