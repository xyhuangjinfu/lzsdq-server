package cn.hjf.lzsdq.article.dao.model;

import java.util.Date;
import java.util.List;

public class ArticleEntity {

    private Long id;
    private String title;
    private String summary;
    private String coverUrl;
    private Date createTime;
    private List<ParagraphEntity> paragraphs;
    private ReadRecordEntity readRecord;
    private VoteEntity vote;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<ParagraphEntity> getParagraphs() {
        return paragraphs;
    }

    public void setParagraphs(List<ParagraphEntity> paragraphs) {
        this.paragraphs = paragraphs;
    }

    public ReadRecordEntity getReadRecord() {
        return readRecord;
    }

    public void setReadRecord(ReadRecordEntity readRecord) {
        this.readRecord = readRecord;
    }

    public VoteEntity getVote() {
        return vote;
    }

    public void setVote(VoteEntity vote) {
        this.vote = vote;
    }
}