package com.neuedu.pojo;

import com.neuedu.pojo.Bus;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhao
 * @since 2020-04-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Uservocablog extends Bus {

    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    private String username;

    private Integer vocabId;

    /**
     * 单词表名
     */
    private String vocabName;

    /**
     * 当前单词表学到第几页了
     */
    private Integer pageNum;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 选择当前词表的时间
     */
    private Timestamp upTime;


}
