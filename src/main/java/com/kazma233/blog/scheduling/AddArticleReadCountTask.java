package com.kazma233.blog.scheduling;

import com.kazma233.blog.filter.ArticleVisitsCountFilter;
import com.kazma233.blog.service.article.impl.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 每隔30s插入一次统计的点击量
 */
@Component
@EnableScheduling
@Slf4j
public class AddArticleReadCountTask {

    @Autowired
    private ArticleService articleService;

    /**
     * 每60s插入一次
     */
    @Scheduled(cron = "*/60 * * * * ?")
    public void insertReadNum() {
        ConcurrentHashMap<String, Integer> readNumMap = ArticleVisitsCountFilter.CONCURRENT_HASH_MAP;

        Iterator<Map.Entry<String, Integer>> iterator = readNumMap.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<String, Integer> next = iterator.next();
            Integer num = next.getValue();
            String uri = next.getKey();
            // 等于0没必要插入
            if (num == 0) {
                continue;
            }
            String[] urlSplit = uri.split("/");
            String tid = urlSplit[urlSplit.length - 1];
            log.info("insert: " + tid + ", num: " + num);
            articleService.updateTextReadNum(tid, num);
            iterator.remove();
        }
    }
}
