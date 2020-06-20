package com.neuedu.controller;


import com.neuedu.config.PassToken;
import com.neuedu.pojo.Result;
import com.neuedu.pojo.User;
import com.neuedu.service.IUservocablogService;
import com.neuedu.util.UserLocalThread;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhao
 * @since 2020-04-07
 */
@RestController
@RequestMapping("/uservocablog")
public class UservocablogController {

    @Resource
    IUservocablogService uservocablogService;

    @GetMapping("addOrChangeVocab")
    public Result addOrChangeVocab(Integer vocabId){
        return uservocablogService.addOrChangeVocab(vocabId);
    }

}
