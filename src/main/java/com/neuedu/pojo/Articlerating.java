package com.neuedu.pojo;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


import java.sql.Timestamp;


/**
 * <p>
 * 
 * </p>
 *
 * @author zhao
 * @since 2020-04-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Articlerating implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long userId;

    private String articleId;

    private String rating;

    /**
     * 评价时间
     */
    private Timestamp ratetime;


}
