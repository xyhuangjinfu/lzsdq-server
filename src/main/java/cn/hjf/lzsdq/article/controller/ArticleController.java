package cn.hjf.lzsdq.article.controller;

import cn.hjf.lzsdq.article.biz.model.Article;
import cn.hjf.lzsdq.article.biz.transfer.ArticleTransfer;
import cn.hjf.lzsdq.article.biz.transfer.ParagraphTransfer;
import cn.hjf.lzsdq.article.biz.transfer.ReadRecordTransfer;
import cn.hjf.lzsdq.article.dao.entity.ArticleEntity;
import cn.hjf.lzsdq.article.dao.entity.ParagraphEntity;
import cn.hjf.lzsdq.article.dao.entity.ReadRecordEntity;
import cn.hjf.lzsdq.article.dao.repository.ArticleRepository;
import cn.hjf.lzsdq.article.dao.repository.ParagraphRepository;
import cn.hjf.lzsdq.article.dao.repository.ReadRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    @Autowired
    private ArticleRepository mArticleRepository;
    @Autowired
    private ParagraphRepository mParagraphRepository;
    @Autowired
    private ReadRecordRepository mReadRecordRepository;

    /**
     * 分页获取文章列表
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("/")
    @CrossOrigin
    public ResponseEntity<List<Article>> getArticlesByPage(@RequestParam(value = "page_num", defaultValue = "1") Integer pageNum,
                                                           @RequestParam(value = "page_size", defaultValue = "10") Integer pageSize) {

        PageRequest pageRequest = PageRequest.of(pageNum - 1, pageSize, Sort.by(Sort.Order.desc("createTime")));
        List<ArticleEntity> articleEntityList = mArticleRepository.findAll(pageRequest).getContent();
        List<ReadRecordEntity> readRecordEntityList = new ArrayList<>();
        for (ArticleEntity e : articleEntityList) {
            //查询阅读信息
            ReadRecordEntity r = new ReadRecordEntity();
            r.setArticleId(e.getId());
            Example<ReadRecordEntity> re = Example.of(r);
            Optional<ReadRecordEntity> rrOptional = mReadRecordRepository.findOne(re);
            ReadRecordEntity readRecordEntity;
            if (rrOptional.isPresent()) {
                readRecordEntity = rrOptional.get();
                readRecordEntity.setReadCount(readRecordEntity.getReadCount());
            } else {
                readRecordEntity = new ReadRecordEntity();
                readRecordEntity.setArticleId(e.getId());
                readRecordEntity.setReadCount(Long.valueOf(0));
            }
            readRecordEntityList.add(readRecordEntity);
        }

        // 构建业务层返回值
        List<Article> articleList = new ArticleTransfer().fromEntityList(articleEntityList);
        ReadRecordTransfer readRecordTransfer = new ReadRecordTransfer();
        for (int i = 0; i < articleList.size(); i++) {
            articleList.get(i).setReadRecord(readRecordTransfer.fromEntity(readRecordEntityList.get(i)));
        }

        return ResponseEntity.ok(articleList);
    }

    /**
     * 根据文章id获取文章详细信息
     *
     * @param articleId
     * @return
     */
    @GetMapping("/{aid}")
    @CrossOrigin
    public ResponseEntity<Article> getArticleById(@PathVariable(value = "aid") Integer articleId) {
        Optional<ArticleEntity> optional = mArticleRepository.findById(Long.valueOf(articleId));
        if (optional.isPresent()) {
            ArticleEntity articleEntity = optional.get();

            // 查询段落
            ParagraphEntity p = new ParagraphEntity();
            p.setArticleId(articleEntity.getId());
            Example<ParagraphEntity> pe = Example.of(p);
            Sort s = Sort.by(Sort.Order.asc("sequence"));
            List<ParagraphEntity> paragraphEntityList = mParagraphRepository.findAll(pe, s);

            //查询并更新阅读信息
            ReadRecordEntity r = new ReadRecordEntity();
            r.setArticleId(articleEntity.getId());
            Example<ReadRecordEntity> re = Example.of(r);
            Optional<ReadRecordEntity> rrOptional = mReadRecordRepository.findOne(re);
            ReadRecordEntity readRecordEntity;
            if (rrOptional.isPresent()) {
                readRecordEntity = rrOptional.get();
                readRecordEntity.setReadCount(readRecordEntity.getReadCount() + 1);
            } else {
                readRecordEntity = new ReadRecordEntity();
                readRecordEntity.setArticleId(articleEntity.getId());
                readRecordEntity.setReadCount(Long.valueOf(1));
            }
            readRecordEntity.setLastReadTime(new Date());
            mReadRecordRepository.save(readRecordEntity);

            // 构建业务层返回值
            Article article = new ArticleTransfer().fromEntity(articleEntity);
            article.setParagraphs(new ParagraphTransfer().fromEntityList(paragraphEntityList));
            article.setReadRecord(new ReadRecordTransfer().fromEntity(readRecordEntity));

            return ResponseEntity.ok(article);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}