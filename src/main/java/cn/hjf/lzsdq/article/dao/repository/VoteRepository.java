package cn.hjf.lzsdq.article.dao.repository;

import cn.hjf.lzsdq.article.dao.entity.VoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<VoteEntity, Long> {
}
