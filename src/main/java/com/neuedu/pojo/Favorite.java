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
public class Favorite extends Bus {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 字典：收藏的是单词还是文章（1单词2文章）
     */
    private Integer typeNum;

    /**
     * 内容类别
     */
    private String type;

    /**
     * 内容ID（单词还是文章）
     */
    private Integer contentId;

    /**
     * 状态
     */
    private Integer status;


}
