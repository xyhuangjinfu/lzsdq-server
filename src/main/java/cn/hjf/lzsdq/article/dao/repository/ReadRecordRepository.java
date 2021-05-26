package cn.hjf.lzsdq.article.dao.repository;

import cn.hjf.lzsdq.article.dao.entity.ReadRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReadRecordRepository extends JpaRepository<ReadRecordEntity, Long> {
}
