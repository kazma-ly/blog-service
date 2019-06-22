package com.kazma233.blog.service.search.impl;

import com.kazma233.blog.config.properties.WebSettings;
import com.kazma233.blog.dao.article.ArticleDao;
import com.kazma233.blog.exception.MyException;
import com.kazma233.blog.exception.SearchException;
import com.kazma233.blog.service.search.ISearchService;
import com.kazma233.blog.vo.article.ArticleCategoryVO;
import com.kazma233.blog.vo.article.ArticleQueryVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class SearchService implements ISearchService {

    @Autowired
    private ArticleDao articleDao;
    @Autowired
    private WebSettings webSettings;

    private static final String ARTICLE_INDEX = "article_index";
    private static final String TYPE = "base";

    private RestHighLevelClient client = null;

    @Override
    public void syncArticleEngine() {
        client = getElasticSearchClient();

        ArticleQueryVO articleQueryVO = new ArticleQueryVO();

        int page = 1;
        Integer count = 100;

        while (true) {
            articleQueryVO.setLimit(count);
            articleQueryVO.setOffset((page - 1) * count);
            List<ArticleCategoryVO> articleCategoryVOS = articleDao.queryArticleByArgs(articleQueryVO);

            try {
                for (ArticleCategoryVO articleCategoryVO : articleCategoryVOS) {
                    Map<String, Object> source = bean2Map(articleCategoryVO);
                    IndexRequest indexRequest = new IndexRequest(ARTICLE_INDEX, TYPE, articleCategoryVO.getId());
                    indexRequest.source(source);

                    IndexResponse res = client.index(indexRequest, RequestOptions.DEFAULT);
                    log.info("添加索引数据结果: " + res.status());
                }
            } catch (IOException e) {
                log.error("处理索引时，部分发生错误: ", e);
                throw new SearchException("处理索引时，部分发生错误: " + e.getLocalizedMessage(), 500);
            }

            if (articleCategoryVOS.size() < count) {
                break;
            }
        }
    }

    @Override
    public List<Map<String, Object>> searchArticle(String keyword) {
        client = getElasticSearchClient();

        SearchRequest searchRequest = new SearchRequest(ARTICLE_INDEX);
        searchRequest.types(TYPE);

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(
                QueryBuilders.multiMatchQuery(keyword, "title", "subtitle", "content")
        );

        searchSourceBuilder.fetchSource(true);
        searchSourceBuilder.fetchSource(new String[]{"id", "title", "subtitle", "content", "createTime"}, new String[]{});
        searchRequest.source(searchSourceBuilder);

        List<Map<String, Object>> articleSearchEntities = new ArrayList<>();

        try {
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            SearchHits hits = searchResponse.getHits();
            for (SearchHit searchHit : hits.getHits()) {
                Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
                articleSearchEntities.add(sourceAsMap);
            }
        } catch (IOException e) {
            log.error("处理索引时，部分发生错误: ", e);
            throw new SearchException("搜索发生异常:" + e.getLocalizedMessage());
        }

        return articleSearchEntities;
    }

    private RestHighLevelClient getElasticSearchClient() {
        try {
            if (client != null && client.ping(RequestOptions.DEFAULT)) {
                return client;
            } else {
                return createRestClient();
            }
        } catch (IOException e) {
            client = createRestClient();
        }
        return client;
    }

    private RestHighLevelClient createRestClient() {
        return new RestHighLevelClient(RestClient.builder(new HttpHost(webSettings.getHost(), 9200, "http")));
    }

    /**
     * 把实体类转换成map
     *
     * @param obj 实体类
     * @param <T> 实体类类型
     * @return 返回map
     * @throws MyException 获取数据失败
     */
    private static <T> Map<String, Object> bean2Map(T obj) {
        Map<String, Object> entityMap = new HashMap<>();
        if (obj == null) {
            return entityMap;
        }

        Class base = obj.getClass();
        do {
            Field[] declaredFields = base.getDeclaredFields();
            for (Field declaredField : declaredFields) {
                if (!declaredField.canAccess(obj)) {
                    declaredField.trySetAccessible();
                }

                String name = declaredField.getName();
                try {
                    Object val = declaredField.get(obj);
                    entityMap.put(name, val);
                } catch (IllegalAccessException e) {
                    log.error("Bean to map erro: ", e);
                    throw new SearchException("Bean to map error", 500);
                }
            }
            base = base.getSuperclass();
        } while (base != null);

        return entityMap;
    }

}
