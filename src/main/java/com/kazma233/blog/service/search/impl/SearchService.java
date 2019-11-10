package com.kazma233.blog.service.search.impl;

import com.kazma233.blog.dao.article.ArticleDao;
import com.kazma233.blog.entity.article.vo.ArticleCategoryVO;
import com.kazma233.blog.entity.article.vo.ArticleQuery;
import com.kazma233.blog.entity.common.enums.Status;
import com.kazma233.blog.entity.search.exception.SearchException;
import com.kazma233.blog.service.search.ISearchService;
import com.kazma233.blog.utils.BeanUtils;
import lombok.AllArgsConstructor;
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
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Slf4j
@Service
public class SearchService implements ISearchService {

    private ArticleDao articleDao;

    private static final String ARTICLE_INDEX = "article_index";
    private static final String SEARCH_ENGINE_HOST = "TODO";
    private static final String TYPE = "base";

    private RestHighLevelClient client = null;

    @Override
    public void syncArticleEngine() {
        client = getElasticSearchClient();

        ArticleQuery articleQueryVO = new ArticleQuery();

        int page = 1;
        Integer count = 100;

        while (true) {
            articleQueryVO.setLimit(count);
            articleQueryVO.setOffset((page - 1) * count);
            List<ArticleCategoryVO> articleCategoryVOS = articleDao.queryPublishArticle(articleQueryVO);

            try {
                for (ArticleCategoryVO articleCategoryVO : articleCategoryVOS) {
                    Map<String, Object> source = BeanUtils.bean2Map(articleCategoryVO);
                    IndexRequest indexRequest = new IndexRequest(ARTICLE_INDEX, TYPE, articleCategoryVO.getId());
                    indexRequest.source(source);

                    IndexResponse res = client.index(indexRequest, RequestOptions.DEFAULT);
                    log.info("add index status: " + res.status());
                }
            } catch (IOException e) {
                log.error("process index has error: ", e);

                throw new SearchException(Status.SOME_SEARCH_ENGINE_INDEX_ERROR);
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
            log.error("search error: ", e);

            throw new SearchException(Status.SEARCH_ENGINE_ERROR);
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
        return new RestHighLevelClient(RestClient.builder(new HttpHost(SEARCH_ENGINE_HOST, 9200, "http")));
    }

}
