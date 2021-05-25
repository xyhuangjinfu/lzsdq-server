package cn.hjf.lzsdq.article.dao.repository;

import cn.hjf.lzsdq.article.dao.table.Paragraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParagraphRepository extends JpaRepository<Paragraph, Long> {
}
