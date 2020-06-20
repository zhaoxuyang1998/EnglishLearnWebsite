package com.neuedu.pojo;


import java.sql.Timestamp;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhao
 * @since 2020-04-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Wordrating implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 词表id
     */
    private String wordId;

    /**
     * 评分
     */
    private String rating;

    /**
     * 评价时间
     */
    private Timestamp ratetime;


}
