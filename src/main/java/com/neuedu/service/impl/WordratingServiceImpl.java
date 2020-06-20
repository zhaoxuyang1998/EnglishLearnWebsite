package com.neuedu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.neuedu.mapper.WordMapper;
import com.neuedu.pojo.Result;
import com.neuedu.pojo.User;
import com.neuedu.pojo.Word;
import com.neuedu.pojo.Wordrating;
import com.neuedu.mapper.WordratingMapper;
import com.neuedu.service.IWordratingService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neuedu.util.UserLocalThread;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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
 * @since 2020-04-14
 */
@Service
public class WordratingServiceImpl extends ServiceImpl<WordratingMapper, Wordrating> implements IWordratingService {

    @Resource
    WordratingMapper wordratingMapper;
    @Resource
    WordMapper wordMapper;

    @Override
    public Result rate(Integer wordId,String score) {
        User user= UserLocalThread.getUser();
        QueryWrapper<Wordrating> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("user_id",user.getId());
        queryWrapper.eq("word_id",wordId);
        Wordrating wordrating=wordratingMapper.selectOne(queryWrapper);
        if (wordrating==null){
            wordrating=new Wordrating();
            wordrating.setUserId(Long.valueOf(user.getId()));
            wordrating.setWordId(String.valueOf(wordId));
            wordrating.setRating(score);
            wordratingMapper.insert(wordrating);
        }else{
            wordrating.setRating(score);
            wordrating.setRatetime(Timestamp.valueOf(LocalDateTime.now()));
            wordratingMapper.update(wordrating,queryWrapper);
        }
        return new Result(1,"success","success");
    }

    @Override
    public void wrongWords(HttpServletResponse response) throws IOException, DocumentException {
        User user=UserLocalThread.getUser();
        QueryWrapper<Wordrating> wordratingQueryWrapper=new QueryWrapper<>();
        wordratingQueryWrapper.eq("user_id",user.getId());
        List<String> stringList=new ArrayList<>();
        stringList.add("4.0");
        stringList.add("5.0");
        wordratingQueryWrapper.in("rating",stringList);
        List<Wordrating> wordratingList=wordratingMapper.selectList(wordratingQueryWrapper);
        Map<String,List<Word>> map=new HashMap<>();
        List<String> keys=new ArrayList<>();
        for(Wordrating wordrating: wordratingList){
            QueryWrapper<Word> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("id",wordrating.getWordId());
            Word word=wordMapper.selectOne(queryWrapper);
            if(word!=null){
                if(!map.containsKey(word.getVocabName())){
                    List<Word> wordList=new ArrayList<>();
                    wordList.add(word);
                    map.put(word.getVocabName(),wordList);
                    keys.add(word.getVocabName());
                }else{
                    map.get(word.getVocabName()).add(word);
                }
            }
        }
        System.out.println(map);
        //-------------------------------------------------------
        response.setHeader("Content-Type","application/pdf");
        response.setHeader("Content-Disposition", "attachment;filename=");
        OutputStream out=response.getOutputStream();
        Rectangle rect = new Rectangle(PageSize.B5);
        Document document=new Document(rect);
        document.setMargins(13, 13, 5, 5);

        PdfWriter writer = PdfWriter.getInstance(document,out);

        document.open();
        BaseFont bfChinese = BaseFont.createFont("STSong-Light","UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        Font blueFont = new Font(bfChinese);
        blueFont.setColor(BaseColor.BLUE);
        Font greenFont = new Font(bfChinese);
        greenFont.setColor(BaseColor.GREEN);
        greenFont.setStyle(2);

        for(int i=0;i<map.size();i++){
            List<Word> list=map.get(keys.get(i));
            Paragraph chapterTitle = new Paragraph(keys.get(i));
            chapterTitle.add(new Chunk(new LineSeparator()));
            Chapter chapter1 = new Chapter(chapterTitle, 1);
            chapter1.setNumberDepth(0);
            for(int j=0;j<list.size();j++){
                Word word=list.get(j);
                Chunk chunk=new Chunk(word.getSpell(),blueFont);
                Chunk chunk1=new Chunk("   ");
                Chunk chunk2=new Chunk(word.getPhonetic(),greenFont);
                Paragraph sectionTitle = new Paragraph();
                sectionTitle.add(chunk);
                sectionTitle.add(chunk1);
                sectionTitle.add(chunk2);
                Section section1 = chapter1.addSection(sectionTitle);
                Paragraph sectionContent = new Paragraph(word.getParaphrase(), blueFont);
                section1.add(sectionContent);
                Paragraph sentence=new Paragraph(word.getSentence(),blueFont);
                section1.add(sentence);
            }
            document.add(chapter1);
        }

        //关闭文档
        document.close();
        //关闭书写器
        writer.close();

        out.flush();
        out.close();
    }
}
