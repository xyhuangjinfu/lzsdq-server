package cn.hjf.lzsdq.article.biz.transfer;

import cn.hjf.lzsdq.article.biz.model.Article;
import cn.hjf.lzsdq.article.dao.model.ArticleEntity;

public class ArticleTransfer {

    public Article fromEntity(ArticleEntity articleEntity) {
        Article article = new Article();
        article.setId(articleEntity.getId());
        article.setTitle(articleEntity.getTitle());
        article.setSummary(articleEntity.getSummary());
        article.setCoverUrl(articleEntity.getCoverUrl());
        article.setCreateTime(articleEntity.getCreateTime());
        article.setReadCount(articleEntity.getReadRecord().getReadCount());
        article.setVoteCount(articleEntity.getVote().getVoteCount());
        return article;
    }
}
