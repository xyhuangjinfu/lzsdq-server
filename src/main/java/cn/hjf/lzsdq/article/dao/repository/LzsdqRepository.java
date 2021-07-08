package cn.hjf.lzsdq.article.dao.repository;

import cn.hjf.lzsdq.article.dao.model.ArticleEntity;
import cn.hjf.lzsdq.article.dao.model.ParagraphEntity;
import cn.hjf.lzsdq.article.dao.model.ReadRecordEntity;
import cn.hjf.lzsdq.article.dao.model.VoteEntity;
import cn.hjf.lzsdq.utils.DateTimeUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class LzsdqRepository implements ILzsdqRepository {

    @Override
    public List<ArticleEntity> getArticleList() {
        Connection connection = ConnectionManager.getInstance().getConnection();
        ResultSet rs = null;
        Statement stmt = null;

        String sql = "SELECT article.id, article.title, article.summary, article.cover_url, article.create_time, read_record.read_count, vote.vote_count FROM article " +
                "LEFT JOIN offline ON article.id=offline.article_id " +
                "LEFT JOIN read_record ON article.id=read_record.article_id " +
                "LEFT JOIN vote ON article.id=vote.article_id " +
                "WHERE offline.article_id IS NULL;";

        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery(sql);

            List<ArticleEntity> list = new ArrayList<>();
            while (rs.next()) {
                ArticleEntity entity = new ArticleEntity();
                entity.setId(rs.getLong(1));
                entity.setTitle(rs.getString(2));
                entity.setSummary(rs.getString(3));
                entity.setCoverUrl(rs.getString(4));
                entity.setCreateTime(rs.getTimestamp(5));

                ReadRecordEntity readRecordEntity = new ReadRecordEntity();
                readRecordEntity.setReadCount(rs.getLong(6));
                entity.setReadRecord(readRecordEntity);

                VoteEntity voteEntity = new VoteEntity();
                voteEntity.setVoteCount(rs.getLong(7));
                entity.setVote(voteEntity);

                list.add(entity);
            }

            return list;
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ResourceCloser.closeSilent(stmt);
            ResourceCloser.closeSilent(rs);
            ResourceCloser.closeSilent(connection);
        }

        return new ArrayList<>();
    }

    @Override
    public List<ArticleEntity> getArticleList(List<Long> articleIdList) {
        if (articleIdList == null || articleIdList.isEmpty()) {
            return new ArrayList<>();
        }

        Connection connection = ConnectionManager.getInstance().getConnection();
        ResultSet rs = null;
        Statement stmt = null;

        StringBuilder idStrBuilder = new StringBuilder();
        for (Long id : articleIdList) {
            idStrBuilder.append(id);
            idStrBuilder.append(",");
        }
        if (idStrBuilder.length() > 0) {
            idStrBuilder.deleteCharAt(idStrBuilder.length() - 1);
        }
        String idStr = idStrBuilder.toString();

        String sql = "SELECT article.id, article.title, article.summary, article.cover_url, article.create_time, read_record.read_count, vote.vote_count FROM article " +
                "LEFT JOIN offline ON article.id=offline.article_id " +
                "LEFT JOIN read_record ON article.id=read_record.article_id " +
                "LEFT JOIN vote ON article.id=vote.article_id " +
                "WHERE article.id in (" + idStr + ") " +
                "AND offline.article_id IS NULL " +
                "ORDER BY FIND_IN_SET(article.id, '" + idStr + "');";

        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery(sql);

            List<ArticleEntity> list = new ArrayList<>();
            while (rs.next()) {
                ArticleEntity entity = new ArticleEntity();
                entity.setId(rs.getLong(1));
                entity.setTitle(rs.getString(2));
                entity.setSummary(rs.getString(3));
                entity.setCoverUrl(rs.getString(4));
                entity.setCreateTime(rs.getTimestamp(5));

                ReadRecordEntity readRecordEntity = new ReadRecordEntity();
                readRecordEntity.setReadCount(rs.getLong(6));
                entity.setReadRecord(readRecordEntity);

                VoteEntity voteEntity = new VoteEntity();
                voteEntity.setVoteCount(rs.getLong(7));
                entity.setVote(voteEntity);

                list.add(entity);
            }

            return list;
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ResourceCloser.closeSilent(stmt);
            ResourceCloser.closeSilent(rs);
            ResourceCloser.closeSilent(connection);
        }

        return new ArrayList<>();
    }

    @Override
    public List<ParagraphEntity> getParagraphList(Long articleId) {
        Connection connection = ConnectionManager.getInstance().getConnection();
        ResultSet rs = null;
        Statement stmt = null;

        String sql = "SELECT * FROM paragraph WHERE paragraph.article_id=" + articleId + " ORDER BY sequence ASC;";

        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery(sql);

            List<ParagraphEntity> list = new ArrayList<>();
            while (rs.next()) {
                ParagraphEntity entity = new ParagraphEntity();
                entity.setArticleId(rs.getLong(1));
                entity.setSequence(rs.getLong(2));
                entity.setContent(rs.getString(3));
                list.add(entity);
            }

            return list;
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ResourceCloser.closeSilent(stmt);
            ResourceCloser.closeSilent(rs);
            ResourceCloser.closeSilent(connection);
        }

        return new ArrayList<>();
    }

    @Override
    public List<ArticleEntity> getMostVotedList(int limit) {
        if (limit <= 0) {
            return new ArrayList<>();
        }

        Connection connection = ConnectionManager.getInstance().getConnection();
        ResultSet rs = null;
        Statement stmt = null;

        String sql = "SELECT vote.article_id, vote.vote_count, article.id, article.title, article.summary, article.cover_url, article.create_time FROM lzsdq.vote " +
                "LEFT JOIN article ON vote.article_id=article.id " +
                "ORDER BY vote_count DESC " +
                "LIMIT " +
                limit;

        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery(sql);

            List<ArticleEntity> list = new ArrayList<>();
            while (rs.next()) {
                ArticleEntity entity = new ArticleEntity();
                entity.setId(rs.getLong(3));
                entity.setTitle(rs.getString(4));
                entity.setSummary(rs.getString(5));
                entity.setCoverUrl(rs.getString(6));
                entity.setCreateTime(rs.getTimestamp(7));

                VoteEntity voteEntity = new VoteEntity();
                voteEntity.setArticleId(rs.getLong(1));
                voteEntity.setVoteCount(rs.getLong(2));
                entity.setVote(voteEntity);

                list.add(entity);
            }

            return list;
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ResourceCloser.closeSilent(stmt);
            ResourceCloser.closeSilent(rs);
            ResourceCloser.closeSilent(connection);
        }

        return new ArrayList<>();
    }

    @Override
    public boolean vote(Long articleId) {
        boolean result = false;

        Connection connection = ConnectionManager.getInstance().getConnection();
        Statement stmt = null;

        String sql = "INSERT INTO vote(article_id, vote_count) values(" + articleId + ", 1) ON DUPLICATE KEY UPDATE vote_count=vote_count+1;";

        try {
            stmt = connection.createStatement();
            int affectRows = stmt.executeUpdate(sql);
            result = affectRows > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ResourceCloser.closeSilent(stmt);
            ResourceCloser.closeSilent(connection);
        }

        return result;
    }

    @Override
    public boolean readRecord(Long articleId) {
        boolean result = false;

        Connection connection = ConnectionManager.getInstance().getConnection();
        Statement stmt = null;

        String dateStr = new DateTimeUtil().getNow();
        String sql = "INSERT INTO read_record(article_id, read_count, last_read_time) values(" + articleId + ", 1, '" + dateStr + "') ON DUPLICATE KEY UPDATE read_count=read_count+1;";

        try {
            stmt = connection.createStatement();
            int affectRows = stmt.executeUpdate(sql);
            result = affectRows > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ResourceCloser.closeSilent(stmt);
            ResourceCloser.closeSilent(connection);
        }

        return result;
    }
}
