package com.neuedu.service;

import com.neuedu.pojo.Result;
import com.neuedu.pojo.Uservocablog;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhao
 * @since 2020-04-07
 */
public interface IUservocablogService extends IService<Uservocablog> {

    Result addOrChangeVocab(Integer vocabId);
}
