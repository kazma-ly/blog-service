package com.kazma233.blog.controller.search;

import com.kazma233.blog.entity.result.BaseResult;
import com.kazma233.blog.service.search.ISearchService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/search")
public class SearchController {

    private ISearchService searchService;

    @GetMapping("/article")
    public BaseResult searchArticle(@RequestParam String keyword) {
        List<Map<String, Object>> searchArticle = searchService.searchArticle(keyword);

        return BaseResult.success(searchArticle);
    }

    @PutMapping("/syncSearchIndex")
    public BaseResult syncSearchIndex() {
        searchService.syncArticleEngine();

        return BaseResult.success();
    }

}
