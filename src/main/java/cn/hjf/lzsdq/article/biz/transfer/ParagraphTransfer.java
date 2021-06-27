package cn.hjf.lzsdq.article.biz.transfer;

import cn.hjf.lzsdq.article.biz.model.Paragraph;
import cn.hjf.lzsdq.article.dao.model.ParagraphEntity;

import java.util.ArrayList;
import java.util.List;

public class ParagraphTransfer {

    public Paragraph fromEntity(ParagraphEntity entity) {
        Paragraph paragraph = new Paragraph();
        paragraph.setSequence(entity.getSequence());
        paragraph.setContent(entity.getContent());
        return paragraph;
    }

    public List<Paragraph> fromEntityList(List<ParagraphEntity> entityList) {
        List<Paragraph> paragraphList = new ArrayList<>();
        for (ParagraphEntity e : entityList) {
            paragraphList.add(fromEntity(e));
        }
        return paragraphList;
    }
}
