package com.neuedu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.neuedu.pojo.Result;
import com.neuedu.pojo.Word;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.mahout.cf.taste.common.TasteException;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhao
 * @since 2020-03-26
 */
public interface IWordService extends IService<Word> {



    Result showWord() throws TasteException;

    Result searchWord(String keyword);

    List<Word> wordListByRecommend(Integer userID,Integer size) throws TasteException;
    IPage<Word> wordListByPage(Integer vocabID, Integer currentPage, Integer size);

    Result favWord(Integer wordId) throws IOException;

    Result deFavWord(Integer wordId) throws IOException;


    Result nextGroup();


    Result wordsByVocabId(Integer vocabId, Integer pageNum, Integer pageSize);

    Word searchWordById(Integer wordId);

    Result reviseWordById(Word word);
}
