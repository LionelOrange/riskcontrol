package com.example.riskcontrol.dao;

import com.example.riskcontrol.model.PageList;
import com.example.riskcontrol.model.RiskEventEsModel;
import com.example.riskcontrol.model.RiskEventEsModelQueryParam;
import com.example.riskcontrol.service.DimensionService;
import com.example.riskcontrol.util.DateUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.lucene.search.TotalHits;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
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

    public PageList<Map<String, Object>> filterRiskEventSearch(RiskEventEsModelQueryParam riskEventEsModelQueryParam) {
        SearchRequest searchRequest = new SearchRequest(riskEventEsModelQueryParam.getIndexName());
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if (StringUtils.isNotBlank(riskEventEsModelQueryParam.getMobile())) {
            boolQueryBuilder.filter(QueryBuilders.termQuery("mobile.keyword", riskEventEsModelQueryParam.getMobile()));
        }
        if (StringUtils.isNotBlank(riskEventEsModelQueryParam.getDeviceId())) {
            boolQueryBuilder.filter(QueryBuilders.termQuery("deviceId.keyword", riskEventEsModelQueryParam.getDeviceId()));
        }
        if (StringUtils.isNotBlank(riskEventEsModelQueryParam.getOperateIp())) {
            boolQueryBuilder.filter(QueryBuilders.termQuery("operateIp.keyword", riskEventEsModelQueryParam.getOperateIp()));
        }
        if (StringUtils.isNotBlank(riskEventEsModelQueryParam.getOpenId())) {
            boolQueryBuilder.filter(QueryBuilders.termQuery("openId.keyword", riskEventEsModelQueryParam.getOpenId()));
        }
        if (StringUtils.isNotBlank(riskEventEsModelQueryParam.getOrderNo())) {
            boolQueryBuilder.filter(QueryBuilders.termQuery("orderNo.keyword", riskEventEsModelQueryParam.getOrderNo()));
        }

        try {
            if (riskEventEsModelQueryParam.getOperateStartTime() != null) {
                Calendar beginTime = DateUtils.parse(riskEventEsModelQueryParam.getOperateStartTime(),
                        "yyyy-MM-dd HH:mm:ss");
                boolQueryBuilder.filter(QueryBuilders.rangeQuery("operateTime").gte(beginTime.getTimeInMillis()));
            }
            if (riskEventEsModelQueryParam.getOperateEndTime() != null) {
                Calendar endTime = DateUtils.parse(riskEventEsModelQueryParam.getOperateEndTime(), "yyyy-MM-dd HH:mm:ss");
                boolQueryBuilder.filter(QueryBuilders.rangeQuery("operateTime").lte(endTime.getTimeInMillis()));
            }
        } catch (Exception e) {
            logger.error("error time", e);
            return null;
        }
        searchSourceBuilder.query(boolQueryBuilder);
        searchSourceBuilder.from(riskEventEsModelQueryParam.getCurrentPage() - 1);
        searchSourceBuilder.size(riskEventEsModelQueryParam.getPageSize());
        searchSourceBuilder.sort("operateTime", SortOrder.DESC);
        searchRequest.source(searchSourceBuilder);
        try {
            SearchResponse searchResponse = client.search(searchRequest,
                    RequestOptions.DEFAULT);
            SearchHits searchHits = searchResponse.getHits();
            List<Map<String, Object>> mapList = new ArrayList<>();
            PageList<Map<String, Object>> pageList = new PageList<>();
            for (SearchHit searchHit : searchHits) {
                String index = searchHit.getIndex();          //获取索引名称
                String id = searchHit.getId();                //获取文档_id
                float score = searchHit.getScore();           //获取得分
                String source = searchHit.getSourceAsString(); //获取文档内容
                System.out.println("index=" + index + ",id=" + id + ",score= " + score + ",source=" + source);                          //打印数据
                Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
                mapList.add(sourceAsMap);
                //最后一条SearchAfter用于searchAfter
                pageList.setSortValues(searchHit.getSortValues());
            }
            TotalHits totalHits = searchHits.getTotalHits();

            pageList.setCurrentPage(riskEventEsModelQueryParam.getCurrentPage());
            pageList.setPageSize(riskEventEsModelQueryParam.getPageSize());
            pageList.setTotalElements(totalHits.value);
            pageList.setTotalPages((int) Math.ceil((double) totalHits.value / riskEventEsModelQueryParam.getPageSize()));
            pageList.setList(mapList);
            return pageList;
        } catch (Exception e) {
            logger.error("is error", e);
        }
        return null;
    }
}