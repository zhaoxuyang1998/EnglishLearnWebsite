package com.neuedu.Listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.neuedu.mapper.WordMapper;
import com.neuedu.pojo.Word;

import java.util.ArrayList;
import java.util.List;


public class WordDataListener extends AnalysisEventListener<Word> {
    private static final int BATCH_COUNT = 100;
    List<Word> list=new ArrayList<>();

    private WordMapper wordMapper;
    private Integer vocabId;
    private String vocabName;

    public WordDataListener(WordMapper wordMapper, Integer vocabId, String vocabName){
        this.wordMapper=wordMapper;
        this.vocabId=vocabId;
        this.vocabName=vocabName;
    }
    @Override
    public void invoke(Word word, AnalysisContext analysisContext) {
        word.setVocabId(vocabId);
        word.setVocabName(vocabName);
        System.out.println(word);
        list.add(word);
        if(list.size()>=BATCH_COUNT){
            saveData();
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        saveData();
        System.out.println("all data complete");
    }

    private void saveData(){
        wordMapper.saveBatch(list);
    }
}
