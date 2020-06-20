package com.neuedu.service;

import com.itextpdf.text.DocumentException;
import com.neuedu.pojo.Result;
import com.neuedu.pojo.Wordrating;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhao
 * @since 2020-04-14
 */
public interface IWordratingService extends IService<Wordrating> {

    Result rate(Integer wordId,String score);

    void wrongWords(HttpServletResponse response) throws IOException, DocumentException;
}
