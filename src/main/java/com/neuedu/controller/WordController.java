package com.neuedu.controller;


import com.neuedu.pojo.Result;
import com.neuedu.pojo.User;
import com.neuedu.pojo.Word;
import com.neuedu.service.IWordService;
import com.neuedu.util.UserLocalThread;
import org.apache.mahout.cf.taste.common.TasteException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhao
 * @since 2020-03-26
 */
@RestController
@RequestMapping("/word")
public class WordController {
    @Resource
    IWordService wordService;

    @GetMapping("/showWord")
    public Result showWord() throws TasteException {
        return wordService.showWord();
    }
    @GetMapping("/searchWord")
    public Result searchWord(String keyword){
        return wordService.searchWord(keyword);
    }

    @GetMapping("/favWord")
    public Result favWord(Integer wordId) throws IOException {
        return wordService.favWord(wordId);
    }

    @GetMapping("/deFavWord")
    public Result deFavWord(Integer wordId) throws IOException {
        return wordService.deFavWord(wordId);
    }

    @GetMapping("/nextGroup")
    public Result nextGroup(){
        return wordService.nextGroup();
    }

    @GetMapping("/wordsByVocabId")
    public Result wordsByVocabId(Integer vocabId,Integer pageNum,Integer pageSize){
        return wordService.wordsByVocabId(vocabId,pageNum,pageSize);
    }



    @GetMapping("/reviseWordById")
    public Result reviseWordById(Word word){
        return wordService.reviseWordById(word);

                
    }

}
