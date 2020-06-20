package com.neuedu.factory;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tobato.fastdfs.domain.fdfs.FileInfo;
import com.github.tobato.fastdfs.domain.fdfs.MetaData;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadFileStream;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadFileWriter;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.neuedu.mapper.DictMapper;
import com.neuedu.mapper.RoleMapper;
import com.neuedu.pojo.Dict;
import com.neuedu.pojo.Result;
import com.neuedu.pojo.Role;
import com.neuedu.util.ConstantDict;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import com.neuedu.util.SpringContextHolder;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
@DependsOn("springContextHolder")
public class ConstantFactory implements IConstantFactory {

    private DictMapper dictMapper=SpringContextHolder.getBean(DictMapper.class);
    private RoleMapper roleMapper=SpringContextHolder.getBean(RoleMapper.class);
    private ObjectMapper objectMapper=SpringContextHolder.getBean(ObjectMapper.class);
    private StringRedisTemplate stringRedisTemplate=SpringContextHolder.getBean(StringRedisTemplate.class);
    private FastFileStorageClient fastFileStorageClient=SpringContextHolder.getBean(FastFileStorageClient.class);
    //private DataModel  dataModel=SpringContextHolder.getBean(DataModel.class);
    private DataModel wordDatamodel=SpringContextHolder.getBean("wordDataModel");
    private DataModel articleDatamodel=SpringContextHolder.getBean("articleDataModel");

    public static IConstantFactory me() {
        return SpringContextHolder.getBean("constantFactory");
    }


    @Override
    public String getDictsByKeyword(String keyword) throws JsonProcessingException {
        if(stringRedisTemplate.hasKey(keyword)){
            return stringRedisTemplate.opsForValue().get(keyword);
        }
        QueryWrapper<Dict> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("name",keyword);
        Dict dict=dictMapper.selectOne(queryWrapper);
        QueryWrapper<Dict> queryWrapper1=new QueryWrapper<>();
        queryWrapper1.eq("pid",dict.getId());
        queryWrapper1.select("num","name").getSqlSelect();
        List<Map<String,Object>> res=dictMapper.selectMaps(queryWrapper1);
        String data=objectMapper.writeValueAsString(res);
        stringRedisTemplate.opsForValue().set(keyword,data);
        return data;
    }

    @Override
    public List<Map<String, Object>> getDictsListByKeyword(String keyword) throws IOException {
        JavaType javaType=objectMapper.getTypeFactory().constructParametricType(List.class,Map.class);
        String res=this.getDictsByKeyword(keyword);
        List<Map<String, Object>> list=(List<Map<String, Object>>)objectMapper.readValue(res,javaType);
        return list;
    }
    @Override
    public String getDictByKeywordAndNum(String keyword,Integer num) throws IOException {
        JavaType javaType=objectMapper.getTypeFactory().constructParametricType(List.class,Dict.class);
        List<Dict> dicts=(List<Dict>) objectMapper.readValue(getDictsByKeyword(keyword),javaType);
        for(Dict dict : dicts){
            if(dict.getNum()==num){
                return dict.getName();
            }
        }
        return null;
    }

    @Override
    public String getRoleNameByNum(Integer num) {
        QueryWrapper<Role> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("number",num);
        Role role=roleMapper.selectOne(queryWrapper);
        if(role!=null){
            return role.getRolename();
        }
        return null;
    }

    @Override
        public String getFileURLWhenUpdate(MultipartFile file) throws IOException {
        String fileName=file.getOriginalFilename();
        String extName= fileName.substring(fileName.lastIndexOf(".")+1);
        StorePath storePath = fastFileStorageClient.uploadFile(file.getInputStream(), file.getSize(), extName, null);
        return storePath.getFullPath();
    }
    @Override
    public List<Long> getRecommendedItemIDs(List<RecommendedItem> recommendations){
        List<Long> recommendItems = new ArrayList<>();
        for(int i = 0 ; i < recommendations.size() ; i++) {
            RecommendedItem recommendedItem=recommendations.get(i);
            recommendItems.add(recommendedItem.getItemID());
        }
        return recommendItems;
    }

    //基于用户推荐
    @Override
    public List<Long> getUserBasedRecommender(Long userID, int size) throws TasteException {
        //皮特森相似度模型
        UserSimilarity similarity  = new PearsonCorrelationSimilarity(wordDatamodel );
        NearestNUserNeighborhood neighbor = new NearestNUserNeighborhood(ConstantDict.NEIGHBORHOOD_NUM, similarity, wordDatamodel );
        //加载基于用户的推荐器
        Recommender recommender = new GenericUserBasedRecommender(wordDatamodel,neighbor,similarity);
        //获取推荐列表
        List<RecommendedItem> recommendations = recommender.recommend(userID, size);
        //抽取ID信息
        return getRecommendedItemIDs(recommendations);
    }
    //基于物品推荐
    @Override
    public List<Long> getItemBasedRecommender(Long userID, int size) throws TasteException {
        //欧氏距离相似度模型
        ItemSimilarity similarity  = new EuclideanDistanceSimilarity(articleDatamodel);
        //加载基于物品的推荐器
        Recommender recommender = new GenericItemBasedRecommender(articleDatamodel,similarity);
        //获取推荐列表
        List<RecommendedItem> recommendations = recommender.recommend(userID, size);
        //抽取ID信息
        return getRecommendedItemIDs(recommendations);
    }

    @Override
        public void downloadFile(String filePath, HttpServletResponse response) throws IOException {

            response.setHeader("Content-Type","application/pdf");
            response.setHeader("Content-Disposition", "attachment;filename=");
            OutputStream out=response.getOutputStream();
            DownloadFileStream callback=new DownloadFileStream(out);
            System.out.println(filePath);
            Object o = fastFileStorageClient.downloadFile(StringUtils.substringBefore(filePath, "/"), StringUtils.substringAfter(filePath, "/"), callback);
            out.flush();
            out.close();

    }

}
