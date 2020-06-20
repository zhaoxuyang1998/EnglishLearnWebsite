package com.neuedu.service;

import com.neuedu.pojo.Result;
import com.neuedu.pojo.Vocab;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhao
 * @since 2020-04-05
 */
public interface IVocabService extends IService<Vocab> {

    Result newVocab(Vocab vocab, MultipartFile excelfile) throws IOException;

    Result vocabCover(Integer id, MultipartFile coverfile) throws IOException;

    Result vocabsByAllTags() throws IOException;

    List<Vocab> searchVocabs(Integer tag);

    Result vocabsRecommend(Integer size);
}
