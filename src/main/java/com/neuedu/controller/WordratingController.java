package com.neuedu.controller;


import com.itextpdf.text.DocumentException;
import com.neuedu.pojo.Result;
import com.neuedu.service.IWordratingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhao
 * @since 2020-04-14
 */
@RestController
@RequestMapping("/wordrating")
public class WordratingController {
    @Resource
    IWordratingService wordratingService;

    @GetMapping("/rate")
    public Result rate(Integer wordId,String score){
        return wordratingService.rate(wordId,score);
    }

    @GetMapping("/wrongWords")
    public void wrongWords(HttpServletResponse response) throws IOException, DocumentException {
        wordratingService.wrongWords(response);
    }

}
