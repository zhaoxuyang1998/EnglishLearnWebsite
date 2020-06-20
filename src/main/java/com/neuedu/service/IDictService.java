package com.neuedu.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.neuedu.pojo.Dict;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 字典表 服务类
 * </p>
 *
 * @author zhao
 * @since 2020-03-26
 */
public interface IDictService extends IService<Dict> {


    Object allByKeyword(String keyword) throws JsonProcessingException;
}
