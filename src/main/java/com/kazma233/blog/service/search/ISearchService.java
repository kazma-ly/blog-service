package com.kazma233.blog.service.search;

import java.util.List;
import java.util.Map;

public interface ISearchService {

    public void syncArticleEngine();

    List<Map<String, Object>> searchArticle(String keyword);
}
