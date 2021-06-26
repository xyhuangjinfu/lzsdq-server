package cn.hjf.lzsdq.article.biz.transfer;

import cn.hjf.lzsdq.article.biz.model.Vote;
import cn.hjf.lzsdq.article.dao.entity.VoteEntity;

import java.util.ArrayList;
import java.util.List;

public class VoteTransfer {

    public Vote fromEntity(VoteEntity voteEntity) {
        if (voteEntity == null) {
            return null;
        }
        Vote vote = new Vote();
        vote.setArticleId(voteEntity.getArticleId());
        vote.setVoteCount(voteEntity.getVoteCount());
        return vote;
    }

    public List<Vote> fromEntityList(List<VoteEntity> voteEntityList) {
        if (voteEntityList == null) {
            return null;
        }
        List<Vote> voteList = new ArrayList<>();
        for (VoteEntity e : voteEntityList) {
            voteList.add(fromEntity(e));
        }
        return voteList;
    }
}
