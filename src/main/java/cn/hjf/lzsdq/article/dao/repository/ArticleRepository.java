package cn.hjf.lzsdq.article.dao.repository;

import cn.hjf.lzsdq.article.dao.entity.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<ArticleEntity, Long> {
}
