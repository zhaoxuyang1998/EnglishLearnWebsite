package com.neuedu.mapper;

import com.neuedu.pojo.Word;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhao
 * @since 2020-03-26
 */
public interface WordMapper extends BaseMapper<Word> {

    void saveBatch(List<Word> list);
}
