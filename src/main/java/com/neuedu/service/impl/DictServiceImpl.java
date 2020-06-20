package com.neuedu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neuedu.factory.ConstantFactory;
import com.neuedu.pojo.Dict;
import com.neuedu.mapper.DictMapper;
import com.neuedu.pojo.Result;
import com.neuedu.service.IDictService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 字典表 服务实现类
 * </p>
 *
 * @author zhao
 * @since 2020-03-26
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements IDictService {
    @Resource
    StringRedisTemplate stringRedisTemplate;
    @Resource
    ObjectMapper objectMapper;
    @Resource
    DictMapper dictMapper;

    @Override
    public Object allByKeyword(String keyword) throws JsonProcessingException {

         return ConstantFactory.me().getDictsByKeyword(keyword);
//        QueryWrapper<Dict> queryWrapper=new QueryWrapper<>();
//        queryWrapper.eq("name",keyword);
//        Dict dict=dictMapper.selectOne(queryWrapper);
//        QueryWrapper<Dict> queryWrapper1=new QueryWrapper<>();
//        queryWrapper1.eq("pid",dict.getId());
//        queryWrapper1.select("num","name").getSqlSelect();
//        if(!stringRedisTemplate.hasKey(keyword)){
//            List<Map<String,Object>> res=dictMapper.selectMaps(queryWrapper1);
//            String data=objectMapper.writeValueAsString(res);
//            stringRedisTemplate.opsForValue().set(keyword,data,30, TimeUnit.MINUTES);
//            return new Result(1,res,"dict: all "+keyword);
//        }else {
//            return new Result(1,stringRedisTemplate.opsForValue().get(keyword),"redis: dict: all "+keyword);
//        }
    }
}
