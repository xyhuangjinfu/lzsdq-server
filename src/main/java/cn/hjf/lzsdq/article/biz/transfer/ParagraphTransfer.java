package cn.hjf.lzsdq.article.biz.transfer;

import cn.hjf.lzsdq.article.biz.model.Paragraph;
import cn.hjf.lzsdq.article.dao.entity.ParagraphEntity;

import java.util.ArrayList;
import java.util.List;

public class ParagraphTransfer {

    public Paragraph fromEntity(ParagraphEntity paragraphEntity) {
        if (paragraphEntity == null) {
            return null;
        }
        Paragraph paragraph = new Paragraph();
        paragraph.setId(paragraphEntity.getId());
        paragraph.setArticleId(paragraphEntity.getArticleId());
        paragraph.setSequence(paragraphEntity.getSequence());
        paragraph.setContent(paragraphEntity.getContent());
        return paragraph;
    }

    public List<Paragraph> fromEntityList(List<ParagraphEntity> paragraphEntityList) {
        if (paragraphEntityList == null) {
            return null;
        }
        List<Paragraph> paragraphList = new ArrayList<>();
        for (ParagraphEntity e : paragraphEntityList) {
            paragraphList.add(fromEntity(e));
        }
        return paragraphList;
    }
}
