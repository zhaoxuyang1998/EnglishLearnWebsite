package com.neuedu.config;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;


import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;
import org.apache.mahout.cf.taste.model.DataModel;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;


@Configuration
public class MahoutConfig {

    private MysqlDataSource getDataSource(){
        MysqlDataSource dataSource=new MysqlDataSource();
        dataSource.setServerName("127.0.0.1");
        dataSource.setUser("root");
        dataSource.setPassword("root");
        dataSource.setDatabaseName("db");
        return dataSource;
    }



    @Bean(autowire = Autowire.BY_NAME,value = "wordDataModel")
    public DataModel getWordDataModel(){
        DataSource dataSource=getDataSource();//加载数据源//建立数据模型
        DataModel dataModel=new MySQLJDBCDataModel(getDataSource(),"wordrating","user_id",
                "word_id","rating","ratetime");
        return  dataModel;    }
    @Bean(autowire = Autowire.BY_NAME,value = "articleDataModel")
    public DataModel getArticleDataModel(){
        DataSource dataSource=getDataSource();
        DataModel dataModel=new MySQLJDBCDataModel(getDataSource(),"articlerating","user_id",
                "article_id","rating","ratetime");
        return  dataModel;    }

}
