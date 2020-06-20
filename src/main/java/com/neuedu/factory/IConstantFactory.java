package com.neuedu.factory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.neuedu.pojo.Result;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface IConstantFactory {

    String getDictsByKeyword(String keyword) throws JsonProcessingException;
    String getDictByKeywordAndNum(String keyword,Integer num) throws IOException;
    String getRoleNameByNum(Integer num);
    String getFileURLWhenUpdate(MultipartFile file) throws IOException;
    List<Map<String,Object>> getDictsListByKeyword(String keyword) throws IOException;
    List<Long> getRecommendedItemIDs(List<RecommendedItem> recommendations);
    List<Long> getUserBasedRecommender(Long userID, int size) throws TasteException;
    List<Long> getItemBasedRecommender(Long userID, int size) throws TasteException;

    void downloadFile(String filePath, HttpServletResponse response) throws IOException;

}
