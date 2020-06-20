package com.neuedu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.neuedu.factory.ConstantFactory;
import com.neuedu.mapper.FavoriteMapper;
import com.neuedu.mapper.UservocablogMapper;
import com.neuedu.mapper.VocabMapper;
import com.neuedu.pojo.*;
import com.neuedu.mapper.WordMapper;
import com.neuedu.service.IFavoriteService;
import com.neuedu.service.IWordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neuedu.util.ConstantDict;
import com.neuedu.util.UserLocalThread;
import org.apache.mahout.cf.taste.common.TasteException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhao
 * @since 2020-03-26
 */
@Service
public class WordServiceImpl extends ServiceImpl<WordMapper, Word> implements IWordService {

    @Resource
    WordMapper wordMapper;
    @Resource
    IFavoriteService favoriteService;
    @Resource
    FavoriteMapper favoriteMapper;
    @Resource
    UservocablogMapper uservocablogMapper;
    @Resource
    VocabMapper vocabMapper;




    @Override
    public Result showWord() throws TasteException {
        User user= UserLocalThread.getUser();
        System.out.println(user);
        //--------从推荐中选取8个单词---------------------------
        List<Word> recommendList=this.wordListByRecommend(user.getId(),8);
        //---------从词表中通过页数记录选取7个单词--------------------------
        QueryWrapper<Uservocablog> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("username",user.getUsername());
        queryWrapper.orderByDesc("up_time");
        List<Uservocablog> uservocablogList=uservocablogMapper.selectList(queryWrapper);
        Uservocablog uservocablog=uservocablogList.get(0);
        Integer currentPage=uservocablog.getPageNum();
        IPage<Word> page = this.wordListByPage(uservocablog.getVocabId(), uservocablog.getPageNum(), 7);
        List<Word> records =page.getRecords();
        double percent= page.getCurrent()*1.0/page.getPages()*100;
        int percentInt= (int) percent;
        if(recommendList!=null) {
            records.addAll(recommendList);
        }
        for(Word record : records){
            QueryWrapper<Favorite> favoriteQueryWrapper=new QueryWrapper<>();
            favoriteQueryWrapper.eq("user_id",user.getId());
            favoriteQueryWrapper.eq("content_id",record.getId());
            favoriteQueryWrapper.eq("type_num",ConstantDict.FAV_WORD_TYPE_NUM);
            Favorite favorite = favoriteMapper.selectOne(favoriteQueryWrapper);
            if (favorite==null){
                record.setStatus(0);
            }
        }
        Map<String,Object> map=new HashMap<>();
        Vocab vocab=vocabMapper.selectById(uservocablog.getVocabId());
        map.put("vocabTag",vocab.getTag());
        map.put("vocabName",vocab.getVocabName());
        map.put("data",records);
        map.put("percent",percentInt);
        return new Result(1,map,"success");
    }

    @Override
    public Result searchWord(String keyword) {
        QueryWrapper<Word> queryWrapper=new QueryWrapper<>();
        queryWrapper.like("spell","%"+keyword+"%");
        List<Word> list = wordMapper.selectList(queryWrapper);
        System.out.println("searchword");
        System.out.println(list);
        return new Result(1,list,"success");
    }

    @Override
    public List<Word> wordListByRecommend(Integer userID, Integer size) throws TasteException {
        List<Long> recommendList= ConstantFactory.me().getUserBasedRecommender(Long.valueOf(1),size);
        System.out.println("-----wordrec");
        System.out.println(recommendList);
        if(recommendList==null||recommendList.size()==0){
            return null;
        }
        QueryWrapper<Word> queryWrapper=new QueryWrapper<>();
        queryWrapper.in("id",recommendList);
        List<Word> recommend=wordMapper.selectList(queryWrapper);
        return recommend;
    }

    @Override
    public IPage<Word> wordListByPage(Integer vocabID, Integer currentPage, Integer size) {
        QueryWrapper<Word> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("vocab_id",vocabID);
        IPage<Word> page=new Page<>(currentPage,size);
        IPage<Word> wordIPage = wordMapper.selectPage(page, queryWrapper);
        return wordIPage;
    }

    @Override
    public Result favWord(Integer wordId) throws IOException {
        return favoriteService.favorite(wordId,ConstantDict.FAV_WORD_TYPE_NUM);
    }

    @Override
    public Result deFavWord(Integer wordId) throws IOException {
        return favoriteService.deFavorite(wordId,ConstantDict.FAV_WORD_TYPE_NUM);
    }

    @Override
    public Result nextGroup() {
        User user= UserLocalThread.getUser();
        QueryWrapper<Uservocablog> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("username",user.getUsername());
        queryWrapper.orderByDesc("up_time");
        List<Uservocablog> uservocablogList=uservocablogMapper.selectList(queryWrapper);
        Uservocablog uservocablog=uservocablogList.get(0);
        Integer currentPage=uservocablog.getPageNum();
        uservocablog.setPageNum(currentPage+1);
        uservocablogMapper.updateById(uservocablog);
        return new Result(1,"success","success");
    }

    @Override
    public Result wordsByVocabId(Integer vocabId, Integer pageNum, Integer pageSize) {
        QueryWrapper<Word> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("vocab_id",vocabId);
        IPage<Word> page = wordMapper.selectPage(new Page<>(pageNum, pageSize), queryWrapper);
        return new Result(1,page,"success");
    }

    @Override
    public Word searchWordById(Integer wordId) {
        QueryWrapper<Word> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("id",wordId);
        Word word=wordMapper.selectOne(queryWrapper);
        return word;
    }

    @Override
    public Result reviseWordById(Word word) {
        Word word1=searchWordById(word.getId());
        word1.setSpell(word.getSpell());
        word1.setPhonetic(word.getPhonetic());
        word1.setParaphrase(word.getParaphrase());
        word1.setSentence(word.getSentence());
        wordMapper.updateById(word1);
        return new Result(1,"success","success");
    }


}
