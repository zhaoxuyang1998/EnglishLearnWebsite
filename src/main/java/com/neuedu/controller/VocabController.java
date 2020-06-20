package com.neuedu.controller;


import com.neuedu.config.PassToken;
import com.neuedu.pojo.Result;
import com.neuedu.pojo.Vocab;
import com.neuedu.service.IVocabService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhao
 * @since 2020-04-05
 */
@RestController
@RequestMapping("/vocab")
public class VocabController {
    @Resource
    IVocabService vocabService;

    @PostMapping("/newVocab")
    @PassToken
    public Result newVocab(Vocab vocab, MultipartFile excelfile) throws IOException {
        System.out.println(vocab);
        System.out.println(excelfile.getOriginalFilename());
        System.out.println("into upload");
        return vocabService.newVocab(vocab,excelfile);

    }
    @PostMapping("/vocabCover")
    @PassToken
    public Result vocabCover(Integer id,MultipartFile coverfile) throws IOException {
        return vocabService.vocabCover(id,coverfile);
    }

    @GetMapping("/vocabsByAllTags")
    @PassToken
    public Result vocabsByAllTags() throws IOException {
        return vocabService.vocabsByAllTags();
    }

    @GetMapping("/vocabsRecommend")
    public  Result vocabsRecommend(Integer size){
        return vocabService.vocabsRecommend(size);
    }
}
