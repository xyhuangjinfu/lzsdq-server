package cn.hjf.lzsdq.article.dao.repository;

import cn.hjf.lzsdq.article.dao.table.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
