package com.kazma233.blog.config.scheduling;

import com.kazma233.blog.entity.log.MongoLog;
import com.kazma233.blog.service.visits.IVisitsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.LinkedBlockingQueue;

@EnableScheduling
@AllArgsConstructor
@Component
@Slf4j
public class VisitsMessageTask {

    private IVisitsService visitsService;
    private static LinkedBlockingQueue<MongoLog> VISITS_LIST = new LinkedBlockingQueue<>();

    @Scheduled(fixedDelay = 1)
    public void add() {

        MongoLog[] mongoLogs = VISITS_LIST.toArray(new MongoLog[]{});
        VISITS_LIST.clear();

        if (mongoLogs.length <= 0) {
            return;
        }

        log.info("当前有{}访问次数", mongoLogs.length);

        for (MongoLog mongoLog : mongoLogs) {
            visitsService.insert(mongoLog);
        }

    }

    public static class VisitsHelp {
        public static void addVisitsMessage(MongoLog mongoLog) {
            try {
                VISITS_LIST.put(mongoLog);
            } catch (InterruptedException e) {
                log.error("新增访问记录失败: ", e);
            }
        }
    }

}
