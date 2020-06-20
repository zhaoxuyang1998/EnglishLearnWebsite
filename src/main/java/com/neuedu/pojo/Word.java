package com.neuedu.pojo;

import com.alibaba.excel.annotation.ExcelProperty;
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

public class Word extends Bus {

    private static final long serialVersionUID = 1L;

    /**
     * 词表ID
     */
    private Integer vocabId;

    private String vocabName;

    /**
     * 单词拼写
     */
    @ExcelProperty("spell")
    private String spell;

    /**
     * 音标
     */
    @ExcelProperty("phonetic")
    private String phonetic;

    /**
     * 释义
     */
    @ExcelProperty("paraphrase")
    private String paraphrase;

    /**
     * 造句
     */
    @ExcelProperty("sentence")
    private String sentence;

    /**
     * 状态
     */
    private Integer status;


}
