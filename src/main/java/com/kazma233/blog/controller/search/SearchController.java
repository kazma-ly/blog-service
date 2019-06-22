package com.kazma233.blog.controller.search;

import com.kazma233.blog.entity.dto.BaseResult;
import com.kazma233.blog.service.search.ISearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private ISearchService searchService;

    @GetMapping("/article")
    public BaseResult searchArticle(@RequestParam String keyword) {
        List<Map<String, Object>> searchArticle = searchService.searchArticle(keyword);

        return BaseResult.createResult(BaseResult.SUCCESS, searchArticle);
    }

    @PutMapping("/syncSearchIndex")
    public BaseResult syncSearchIndex() {
        searchService.syncArticleEngine();
        return BaseResult.createResult(BaseResult.SUCCESS);
    }

}
