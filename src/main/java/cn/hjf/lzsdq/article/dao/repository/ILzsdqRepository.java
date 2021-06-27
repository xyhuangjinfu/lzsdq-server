package cn.hjf.lzsdq.article.dao.repository;

import cn.hjf.lzsdq.article.dao.model.ArticleEntity;
import cn.hjf.lzsdq.article.dao.model.ParagraphEntity;

import java.util.List;

public interface ILzsdqRepository {

    /**
     * 查询所有文章的信息
     *
     * @return
     */
    List<ArticleEntity> getArticleList();

    /**
     * 根据文章id列表查询文章信息
     *
     * @param articleIdList
     * @return
     */
    List<ArticleEntity> getArticleList(List<Long> articleIdList);

    /**
     * 根据文章id查询文章段落
     *
     * @param articleId
     * @return
     */
    List<ParagraphEntity> getParagraphList(Long articleId);

    /**
     * 给文章点赞
     *
     * @param articleId
     * @return
     */
    boolean vote(Long articleId);

    /**
     * 更新文章阅读记录
     *
     * @param articleId
     * @return
     */
    boolean readRecord(Long articleId);
}
