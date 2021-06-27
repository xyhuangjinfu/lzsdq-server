package cn.hjf.lzsdq.article.biz.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SortArticleTask {

    @Scheduled(cron = "0 0 1 * * ?")
    public void execute() {
        ArticleManager.getInstance().update();
    }
}