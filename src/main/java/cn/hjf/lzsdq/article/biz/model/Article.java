package cn.hjf.lzsdq.article.biz.model;

import java.util.Date;
import java.util.List;

public class Article {

    private Long id;
    private String title;
    private String summary;
    private String coverUrl;
    private Date createTime;
    private List<Paragraph> paragraphs;
    private ReadRecord readRecord;

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

    public List<Paragraph> getParagraphs() {
        return paragraphs;
    }

    public void setParagraphs(List<Paragraph> paragraphs) {
        this.paragraphs = paragraphs;
    }

    public ReadRecord getReadRecord() {
        return readRecord;
    }

    public void setReadRecord(ReadRecord readRecord) {
        this.readRecord = readRecord;
    }
}