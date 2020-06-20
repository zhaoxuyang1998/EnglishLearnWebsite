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
 * @since 2020-04-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Usercomment extends Bus {

    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    private Integer userId;

    /**
     * 类型id 1单词 2文章
     */
    private Integer typeNum;

    /**
     * 类型
     */
    private String type;

    /**
     * 评论的单词id
     */
    private Integer contentId;

    /**
     * 评论
     */
    private String comment;


}
