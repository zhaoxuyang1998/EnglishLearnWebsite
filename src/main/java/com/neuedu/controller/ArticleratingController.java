package com.neuedu.controller;


import com.neuedu.pojo.Result;
import com.neuedu.service.IArticleratingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhao
 * @since 2020-04-15
 */
@RestController
@RequestMapping("/articlerating")
public class ArticleratingController {

    @Resource
    IArticleratingService articleratingService;

    @GetMapping("rateArticle")
    public Result rateArticle(Integer articleId, Double socre){
        return articleratingService.rateArticle(articleId,String.valueOf(socre));
    }

}
