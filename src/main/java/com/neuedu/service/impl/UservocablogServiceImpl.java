package com.neuedu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.neuedu.mapper.VocabMapper;
import com.neuedu.pojo.Result;
import com.neuedu.pojo.User;
import com.neuedu.pojo.Uservocablog;
import com.neuedu.mapper.UservocablogMapper;
import com.neuedu.service.IUservocablogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neuedu.util.ConstantDict;
import com.neuedu.util.UserLocalThread;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhao
 * @since 2020-04-07
 */
@Service
public class UservocablogServiceImpl extends ServiceImpl<UservocablogMapper, Uservocablog> implements IUservocablogService {
    @Resource
    UservocablogMapper uservocablogMapper;
    @Resource
    VocabMapper vocabMapper;

    @Override
    public Result addOrChangeVocab(Integer vocabId) {
        User user = UserLocalThread.getUser();
        QueryWrapper<Uservocablog>  queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("username",user.getUsername());
        queryWrapper.eq("vocab_id",vocabId);
        Uservocablog uservocablog=uservocablogMapper.selectOne(queryWrapper);
        if (uservocablog==null){
            uservocablog=new Uservocablog();
            uservocablog.setPageNum(ConstantDict.INIT_VOCAB_PAGE_NUM);
            uservocablog.setUsername(user.getUsername());
            uservocablog.setVocabId(vocabId);
            uservocablog.setVocabName(vocabMapper.selectById(vocabId).getVocabName());
            uservocablogMapper.insert(uservocablog);
        }else{
            uservocablog.setUpTime(Timestamp.valueOf(LocalDateTime.now()));
            uservocablogMapper.updateById(uservocablog);
        }

        return new Result(1,"success","success");
    }
}
