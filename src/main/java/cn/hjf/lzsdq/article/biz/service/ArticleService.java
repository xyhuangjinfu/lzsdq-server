package cn.hjf.lzsdq.article.biz.service;

import cn.hjf.lzsdq.article.biz.model.Article;
import cn.hjf.lzsdq.article.biz.transfer.ArticleTransfer;
import cn.hjf.lzsdq.article.biz.transfer.ParagraphTransfer;
import cn.hjf.lzsdq.article.dao.model.ArticleEntity;
import cn.hjf.lzsdq.article.dao.model.ParagraphEntity;
import cn.hjf.lzsdq.article.dao.repository.ILzsdqRepository;
import cn.hjf.lzsdq.article.dao.repository.LzsdqRepository;
import cn.hjf.lzsdq.utils.Paging;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArticleService {

    private ILzsdqRepository mLzsdqRepository = new LzsdqRepository();

    public Paging<Article> pageQuery(int pageNum, int pageSize) {
        List<Long> idList = pageQueryIds(pageNum, pageSize);
        List<ArticleEntity> articleEntityList = mLzsdqRepository.getArticleList(idList);
        List<Article> articleList = new ArrayList<>(articleEntityList.size());
        ArticleTransfer articleTransfer = new ArticleTransfer();
        for (ArticleEntity e : articleEntityList) {
            Article article = articleTransfer.fromEntity(e);
            articleList.add(article);
        }
        Paging<Article> paging = new Paging<>();
        paging.setData(articleList);
        paging.setPage(pageNum);
        paging.setTotalPage((int) Math.ceil(ArticleManager.getInstance().getSortedIdList().size() * 1.0 / pageSize));
        return paging;
    }

    public Article articleDetail(Long articleId) {
        List<ArticleEntity> articleEntityList = mLzsdqRepository.getArticleList(Arrays.asList(articleId));
        if (articleEntityList == null || articleEntityList.isEmpty()) {
            return null;
        }

        List<ParagraphEntity> paragraphEntityList = mLzsdqRepository.getParagraphList(articleId);
        ArticleEntity articleEntity = articleEntityList.get(0);

        Article article = new ArticleTransfer().fromEntity(articleEntity);
        article.setParagraphs(new ParagraphTransfer().fromEntityList(paragraphEntityList));

        updateReadCount(articleId);

        return article;
    }

    public boolean updateReadCount(Long articleId) {
        return mLzsdqRepository.readRecord(articleId);
    }

    public boolean updateVoteCount(Long articleId) {
        return mLzsdqRepository.vote(articleId);
    }

    public List<Article> hotArticleList(Integer limit) {
//        List<ArticleEntity> articleEntityList = ArticleManager.getInstance().getArticleEntityList();
//        articleEntityList.sort(new Comparator<ArticleEntity>() {
//            @Override
//            public int compare(ArticleEntity o1, ArticleEntity o2) {
//                return o2.getReadRecord().getReadCount().compareTo(o1.getReadRecord().getReadCount());
//            }
//        });
//        int hotNum = limit;
//        List<Long> hotIdList = new ArrayList<>(hotNum);
//        for (int i = 0; i < hotNum; i++) {
//            hotIdList.add(articleEntityList.get(i).getId());
//        }
//
//        List<ArticleEntity> hotArticleEntityList = mLzsdqRepository.getArticleList(hotIdList);
//        List<Article> articleList = new ArrayList<>(articleEntityList.size());
//        ArticleTransfer articleTransfer = new ArticleTransfer();
//        for (ArticleEntity e : hotArticleEntityList) {
//            Article article = articleTransfer.fromEntity(e);
//            articleList.add(article);
//        }
//        return articleList;

        List<ArticleEntity> mostVotedList = mLzsdqRepository.getMostVotedList(10);
        List<Article> articleList = new ArrayList<>(mostVotedList.size());
        ArticleTransfer articleTransfer = new ArticleTransfer();
        for (ArticleEntity e : mostVotedList) {
            Article article = articleTransfer.fromEntity(e);
            articleList.add(article);
        }
        return articleList;
    }

    /**
     * ********************************************************************************************************************
     * <p>
     * ********************************************************************************************************************
     */

    private List<Long> pageQueryIds(int pageNum, int pageSize) {
        List<Long> idList = ArticleManager.getInstance().getSortedIdList();
        List<Long> result = new ArrayList<>(pageSize);
        int start = (pageNum - 1) * pageSize;
        int end = Math.min(start + pageSize - 1, idList.size() - 1);
        for (int i = start; i <= end; i++) {
            result.add(idList.get(i));
        }
        return result;
    }
}
