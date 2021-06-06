package cn.hjf.lzsdq.article.biz.transfer;

import cn.hjf.lzsdq.article.biz.model.Article;
import cn.hjf.lzsdq.article.dao.entity.ArticleEntity;

import java.util.ArrayList;
import java.util.List;

public class ArticleTransfer {

    public Article fromEntity(ArticleEntity articleEntity) {
        if (articleEntity == null) {
            return null;
        }
        Article article = new Article();
        article.setId(articleEntity.getId());
        article.setTitle(articleEntity.getTitle());
        article.setSummary(articleEntity.getSummary());
        article.setCoverUrl(articleEntity.getCoverUrl());
        article.setCreateTime(articleEntity.getCreateTime());
        return article;
    }

    public List<Article> fromEntityList(List<ArticleEntity> articleEntityList) {
        if (articleEntityList == null) {
            return null;
        }
        List<Article> articleList = new ArrayList<>();
        for (ArticleEntity e : articleEntityList) {
            articleList.add(fromEntity(e));
        }
        return articleList;
    }
}
