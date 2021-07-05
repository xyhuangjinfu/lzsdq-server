package cn.hjf.lzsdq.article.biz.service;

import cn.hjf.lzsdq.article.dao.model.ArticleEntity;
import cn.hjf.lzsdq.article.dao.repository.ILzsdqRepository;
import cn.hjf.lzsdq.article.dao.repository.LzsdqRepository;
import cn.hjf.lzsdq.utils.DateTimeUtil;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ArticleManager {

    private List<Long> mSortedArticleIdList;
    private List<ArticleEntity> mArticleEntityList;
    private ILzsdqRepository mLzsdqRepository;

    private static class InstanceHolder {
        private static ArticleManager sInstance = new ArticleManager();
    }

    public static ArticleManager getInstance() {
        return InstanceHolder.sInstance;
    }

    private ArticleManager() {
        mLzsdqRepository = new LzsdqRepository();

        update();
    }

    /**
     * ********************************************************************************************************************
     * <p>
     * ********************************************************************************************************************
     */

    public void update() {
        List<ArticleEntity> articleEntityList = mLzsdqRepository.getArticleList();
        updateArticleEntityList(articleEntityList);

        sortByScore(articleEntityList);
        List<Long> sortedIdList = new ArrayList<>(articleEntityList.size());
        for (ArticleEntity e : articleEntityList) {
            sortedIdList.add(e.getId());
        }
        updateSortedIdList(sortedIdList);
    }

    /**
     * ********************************************************************************************************************
     * <p>
     * ********************************************************************************************************************
     */

    public synchronized void updateSortedIdList(List<Long> idList) {
        mSortedArticleIdList = idList;
    }

    public synchronized List<Long> getSortedIdList() {
        return new ArrayList<>(mSortedArticleIdList);
    }

    public synchronized void updateArticleEntityList(List<ArticleEntity> articleEntityList) {
        mArticleEntityList = articleEntityList;
    }

    public synchronized List<ArticleEntity> getArticleEntityList() {
        return new ArrayList<>(mArticleEntityList);
    }

    /**
     * ********************************************************************************************************************
     * <p>
     * ********************************************************************************************************************
     */

    private void sortByScore(List<ArticleEntity> list) {
        DateTimeUtil dateTimeUtil = new DateTimeUtil();
        list.sort(new Comparator<ArticleEntity>() {
            @Override
            public int compare(ArticleEntity o1, ArticleEntity o2) {
                int day1 = dateTimeUtil.betweenDaysToNow(o1.getCreateTime());
                int day2 = dateTimeUtil.betweenDaysToNow(o2.getCreateTime());
                if (day1 == day2 && o1.getVote().getVoteCount().equals(o2.getVote().getVoteCount())) {
                    return o2.getId().compareTo(o1.getId());
                }

                double s1 = articleScore(o1, day1);
                double s2 = articleScore(o2, day2);
                return Double.compare(s2, s1);
            }
        });
    }

    private double articleScore(ArticleEntity articleEntity, int days) {
        long vote = articleEntity.getVote().getVoteCount();
        vote = Math.max(vote, 1);
        double score = vote / Math.pow(days + 2, 1.8);
        return score;
    }
}
