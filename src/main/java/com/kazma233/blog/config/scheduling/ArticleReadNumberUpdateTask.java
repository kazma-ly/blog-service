package com.kazma233.blog.config.scheduling;

import com.kazma233.blog.service.article.IArticleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@EnableScheduling
@AllArgsConstructor
@Component
@Slf4j
public class ArticleReadNumberUpdateTask {

    private static Map<String, Long> ARTICLE_READ_NUMBER_COUNTER_MAP = new ConcurrentHashMap<>();

    @Autowired
    private IArticleService articleService;

    @Scheduled(fixedDelay = 1000 * 60 * 60)
    public void execTask() {

        Iterator<Map.Entry<String, Long>> iterator = ARTICLE_READ_NUMBER_COUNTER_MAP.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<String, Long> entry = iterator.next();
            articleService.updateViewNum(entry.getKey(), entry.getValue());
            iterator.remove();
        }

    }

    public static class ReadNumberHelper {

        public static void addOnce(String articleId) {
            Long currentReadNum = ARTICLE_READ_NUMBER_COUNTER_MAP.getOrDefault(articleId, 0L);
            ARTICLE_READ_NUMBER_COUNTER_MAP.put(articleId, currentReadNum + 1);
        }

    }

}
