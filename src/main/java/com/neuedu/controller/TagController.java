package com.neuedu.controller;


import com.neuedu.config.PassToken;
import com.neuedu.pojo.Result;
import org.apache.hadoop.conf.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Properties;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhao
 * @since 2020-03-27
 */
@RestController
@RequestMapping("/tag")
public class TagController {
//    private Connection connection;
//    private Configuration configuration;
//    //private static Properties properties;
//
//    @GetMapping("/test")
//    @PassToken
//    public Result test() throws IOException {
//        //properties = System.getProperties();
//        configuration = HBaseConfiguration.create();
//
//        //configuration.set("hbase.rootdir", "hdfs:namenode:9000/hbase");
//        // zookeeper集群的URL配置信息
//        //properties.setProperty("hadoop.home.dir", "D:\\hadoop-2.7.7");
//        configuration.set("hbase.rootdir","hdfs://125.124.55.136:9000/hbase");
//        configuration.set("hbase.zookeeper.quorum", "125.124.55.136");
//        // HBase的Master
//        //configuration.set("hbase.master", "hbase-master:16010");
//        //configuration.set("zookeeper.znode.parent","/hbase");
//        // 客户端连接zookeeper端口
//        configuration.set("hbase.zookeeper.property.clientPort", "2181");
//
//        // HBase RPC请求超时时间，默认60s(60000)
//        configuration.setInt("hbase.rpc.timeout", 20000);
//        // 客户端重试最大次数，默认35
//        configuration.setInt("hbase.client.retries.number", 10);
//        // 客户端发起一次操作数据请求直至得到响应之间的总超时时间，可能包含多个RPC请求，默认为2min
//        configuration.setInt("hbase.client.operation.timeout", 30000);
//        // 客户端发起一次scan操作的rpc调用至得到响应之间的总超时时间
//        configuration.setInt("hbase.client.scanner.timeout.period", 200000);
//        // 获取hbase连接对象
//        connection = ConnectionFactory.createConnection(configuration);
//        Admin admin =connection.getAdmin();
//        System.out.println("beforeadmin");
//        //connection.getTable(TableName.valueOf("member"));
//        boolean table1 = admin.tableExists(TableName.valueOf("table1"));
//        System.out.println("afteradmin");
//        return new Result(1,table1,"after");
//    }

}
