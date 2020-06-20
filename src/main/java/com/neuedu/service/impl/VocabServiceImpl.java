package com.neuedu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.neuedu.Listener.WordDataListener;
import com.neuedu.factory.ConstantFactory;
import com.neuedu.mapper.WordMapper;
import com.neuedu.pojo.Result;
import com.neuedu.pojo.User;
import com.neuedu.pojo.Vocab;
import com.neuedu.mapper.VocabMapper;
import com.neuedu.pojo.Word;
import com.neuedu.service.IVocabService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neuedu.util.ConstantDict;
import com.neuedu.util.UserLocalThread;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhao
 * @since 2020-04-05
 */
@Service
public class VocabServiceImpl extends ServiceImpl<VocabMapper, Vocab> implements IVocabService {
    @Resource
    VocabMapper vocabMapper;
    @Resource
    WordMapper wordMapper;

    @Override
    public Result newVocab(Vocab vocab, MultipartFile excelfile) throws IOException {
        vocab.setTag(ConstantFactory.me().getDictByKeywordAndNum(ConstantDict.STUDY_MAJOR, vocab.getTagNum()));
        vocabMapper.insert(vocab);
        Integer vocabId= vocab.getId();
        String vocabName= vocab.getVocabName();
        ExcelReader excelReader= EasyExcel.read(excelfile.getInputStream(), Word.class,new WordDataListener(wordMapper,vocabId,vocabName)).build();
        ReadSheet readSheet= EasyExcel.readSheet(0).build();
        excelReader.read(readSheet);
        excelReader.finish();
        return new Result(1,vocab.getId(),"upload vocab cover success");
    }

    @Override
    public Result vocabCover(Integer id, MultipartFile coverfile) throws IOException {
        Vocab vocab=vocabMapper.selectById(id);
        vocab.setCoverFileLocation(ConstantFactory.me().getFileURLWhenUpdate(coverfile));
        vocabMapper.updateById(vocab);
        return new Result(1,"yes","Cover upload success");
    }

    @Override
    public Result vocabsByAllTags() throws IOException {
        List<Map<String,Object>> list=ConstantFactory.me().getDictsListByKeyword(ConstantDict.STUDY_MAJOR);
        List<Map<String,Object>> res=new ArrayList<>();
        for(Map<String,Object> map :list){
            Map<String,Object> maP=new HashMap<>();
            maP.put("num",map.get("num"));
            maP.put("name",map.get("name"));
            List<Vocab> vocabs=this.searchVocabs(Integer.parseInt(map.get("num").toString()));
            maP.put("data",vocabs);
            res.add(maP);
        }
        return new Result(1,res,"success");
    }

    @Override
    public List<Vocab> searchVocabs(Integer tag) {
        QueryWrapper<Vocab> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("tag_num",tag);
        return vocabMapper.selectList(queryWrapper);
    }

    @Override
    public Result vocabsRecommend(Integer size) {
        User user=UserLocalThread.getUser();
        Integer tagNum=user.getTagNum();
        QueryWrapper<Vocab> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("tag_num",tagNum);
        queryWrapper.orderByDesc("id");
        IPage<Vocab> page = vocabMapper.selectPage(new Page<>(1,size), queryWrapper);
        return new Result(1,page,"success");
    }


}
