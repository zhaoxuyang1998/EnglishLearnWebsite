package com.neuedu.service;

import com.neuedu.pojo.Articlerating;
import com.baomidou.mybatisplus.extension.service.IService;
import com.neuedu.pojo.Result;

import java.math.BigDecimal;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhao
 * @since 2020-04-15
 */
public interface IArticleratingService extends IService<Articlerating> {

    BigDecimal averageScore(Integer articleId);

    Result rateArticle(Integer articleId, String socre);

    BigDecimal myScore(Integer articleId);
}
