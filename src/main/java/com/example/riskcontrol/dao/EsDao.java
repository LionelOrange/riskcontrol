package com.example.riskcontrol.dao;

import com.example.riskcontrol.service.DimensionService;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class EsDao {
    private static final Logger logger = LoggerFactory.getLogger(DimensionService.class);

    @Autowired
    RestHighLevelClient client;

    //单条写入文档
    public void singleIndexDoc(Map<String, Object> dataMap, String indexName, String indexId) {
        IndexRequest indexRequest = new IndexRequest(indexName).id(indexId).source(dataMap);         //构建IndexRequest对象并设置对应的索引和_id字段名称
        try {
            IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT); //执行写入
            //通过IndexResponse获取索引名称
            String index = indexResponse.getIndex();
            String id = indexResponse.getId();//通过IndexResponse获取文档ID
            //通过IndexResponse获取文档版本
            Long version = indexResponse.getVersion();
            logger.info("写入成功：index=" + index + ",id=" + id + ",version=" + version);
        } catch (Exception e) {
            logger.error("写入失败：index=" + indexName + ",id=" + indexId, e);
        }
    }
}