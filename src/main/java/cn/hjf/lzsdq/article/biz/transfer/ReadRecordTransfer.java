package cn.hjf.lzsdq.article.biz.transfer;

import cn.hjf.lzsdq.article.biz.model.ReadRecord;
import cn.hjf.lzsdq.article.dao.entity.ReadRecordEntity;

import java.util.ArrayList;
import java.util.List;

public class ReadRecordTransfer {

    public ReadRecord fromEntity(ReadRecordEntity readRecordEntity) {
        if (readRecordEntity == null) {
            return null;
        }
        ReadRecord readRecord = new ReadRecord();
        readRecord.setId(readRecordEntity.getId());
        readRecord.setArticleId(readRecordEntity.getArticleId());
        readRecord.setReadCount(readRecordEntity.getReadCount());
        readRecord.setLastReadTime(readRecordEntity.getLastReadTime());
        return readRecord;
    }

    public List<ReadRecord> fromEntityList(List<ReadRecordEntity> readRecordEntityList) {
        if (readRecordEntityList == null) {
            return null;
        }
        List<ReadRecord> readRecordList = new ArrayList<>();
        for (ReadRecordEntity e : readRecordEntityList) {
            readRecordList.add(fromEntity(e));
        }
        return readRecordList;
    }
}
