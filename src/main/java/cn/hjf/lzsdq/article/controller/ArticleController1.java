//package cn.hjf.lzsdq.article.controller;
//
//import cn.hjf.lzsdq.article.biz.model.Article;
//import cn.hjf.lzsdq.article.biz.model.ReadRecord;
//import cn.hjf.lzsdq.article.biz.transfer.ArticleTransfer;
//import cn.hjf.lzsdq.article.biz.transfer.ParagraphTransfer;
//import cn.hjf.lzsdq.article.biz.transfer.ReadRecordTransfer;
//import cn.hjf.lzsdq.article.biz.transfer.VoteTransfer;
//import cn.hjf.lzsdq.article.dao.entity.ArticleEntity;
//import cn.hjf.lzsdq.article.dao.entity.ParagraphEntity;
//import cn.hjf.lzsdq.article.dao.entity.ReadRecordEntity;
//import cn.hjf.lzsdq.article.dao.entity.VoteEntity;
//import cn.hjf.lzsdq.article.dao.repository.ArticleRepository;
//import cn.hjf.lzsdq.article.dao.repository.ParagraphRepository;
//import cn.hjf.lzsdq.article.dao.repository.ReadRecordRepository;
//import cn.hjf.lzsdq.article.dao.repository.VoteRepository;
//import cn.hjf.lzsdq.utils.Paging;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Example;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Sort;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/api/articles")
//public class ArticleController1 {
//
//    @Autowired
//    private ArticleRepository mArticleRepository;
//    @Autowired
//    private ParagraphRepository mParagraphRepository;
//    @Autowired
//    private ReadRecordRepository mReadRecordRepository;
//    @Autowired
//    private VoteRepository mVoteRepository;
//
//    /**
//     * 分页获取文章列表
//     *
//     * @param pageNum
//     * @param pageSize
//     * @return
//     */
//    @RequestMapping("/")
//    @CrossOrigin
//    public ResponseEntity<Paging<Article>> getArticlesByPage(@RequestParam(value = "page_num", defaultValue = "1") Integer pageNum,
//                                                             @RequestParam(value = "page_size", defaultValue = "10") Integer pageSize) {
//
//        PageRequest pageRequest = PageRequest.of(pageNum - 1, pageSize, Sort.by(Sort.Order.desc("createTime")));
//        Page<ArticleEntity> page = mArticleRepository.findAll(pageRequest);
//        List<ArticleEntity> articleEntityList = page.getContent();
//        List<ReadRecordEntity> readRecordEntityList = new ArrayList<>();
//        List<VoteEntity> voteEntityList = new ArrayList<>();
//        for (ArticleEntity e : articleEntityList) {
//            //查询阅读信息
//            ReadRecordEntity r = new ReadRecordEntity();
//            r.setArticleId(e.getId());
//            Example<ReadRecordEntity> re = Example.of(r);
//            Optional<ReadRecordEntity> rrOptional = mReadRecordRepository.findOne(re);
//            ReadRecordEntity readRecordEntity;
//            if (rrOptional.isPresent()) {
//                readRecordEntity = rrOptional.get();
//                readRecordEntity.setReadCount(readRecordEntity.getReadCount());
//            } else {
//                readRecordEntity = new ReadRecordEntity();
//                readRecordEntity.setArticleId(e.getId());
//                readRecordEntity.setReadCount(Long.valueOf(0));
//            }
//            readRecordEntityList.add(readRecordEntity);
//            //查询点赞信息
//            VoteEntity v = new VoteEntity();
//            v.setArticleId(e.getId());
//            Example<VoteEntity> ve = Example.of(v);
//            Optional<VoteEntity> vOptional = mVoteRepository.findOne(ve);
//            VoteEntity voteEntity;
//            if (vOptional.isPresent()) {
//                voteEntity = vOptional.get();
//            } else {
//                voteEntity = new VoteEntity();
//                voteEntity.setArticleId(e.getId());
//                voteEntity.setVoteCount(Long.valueOf(0));
//            }
//            voteEntityList.add(voteEntity);
//        }
//
//        // 构建业务层返回值
//        List<Article> articleList = new ArticleTransfer().fromEntityList(articleEntityList);
//        ReadRecordTransfer readRecordTransfer = new ReadRecordTransfer();
//        VoteTransfer voteTransfer = new VoteTransfer();
//        for (int i = 0; i < articleList.size(); i++) {
//            articleList.get(i).setReadRecord(readRecordTransfer.fromEntity(readRecordEntityList.get(i)));
//            articleList.get(i).setVote(voteTransfer.fromEntity(voteEntityList.get(i)));
//        }
//
//        Paging<Article> paging = new Paging<>();
//        paging.setPage(pageNum);
//        paging.setTotalPage(page.getTotalPages());
//        paging.setData(articleList);
//        return ResponseEntity.ok(paging);
//    }
//
//    /**
//     * 根据文章id获取文章详细信息
//     *
//     * @param articleId
//     * @return
//     */
//    @GetMapping("/{article_id}")
//    @CrossOrigin
//    public ResponseEntity<Article> getArticleById(@PathVariable(value = "article_id") Integer articleId) {
//        Optional<ArticleEntity> optional = mArticleRepository.findById(Long.valueOf(articleId));
//        if (optional.isPresent()) {
//            ArticleEntity articleEntity = optional.get();
//
//            // 查询段落
//            ParagraphEntity p = new ParagraphEntity();
//            p.setArticleId(articleEntity.getId());
//            Example<ParagraphEntity> pe = Example.of(p);
//            Sort s = Sort.by(Sort.Order.asc("sequence"));
//            List<ParagraphEntity> paragraphEntityList = mParagraphRepository.findAll(pe, s);
//
//            //查询并更新阅读信息
//            ReadRecordEntity r = new ReadRecordEntity();
//            r.setArticleId(articleEntity.getId());
//            Example<ReadRecordEntity> re = Example.of(r);
//            Optional<ReadRecordEntity> rrOptional = mReadRecordRepository.findOne(re);
//            ReadRecordEntity readRecordEntity;
//            if (rrOptional.isPresent()) {
//                readRecordEntity = rrOptional.get();
//                readRecordEntity.setReadCount(readRecordEntity.getReadCount() + 1);
//            } else {
//                readRecordEntity = new ReadRecordEntity();
//                readRecordEntity.setArticleId(articleEntity.getId());
//                readRecordEntity.setReadCount(Long.valueOf(1));
//            }
//            readRecordEntity.setLastReadTime(new Date());
//            mReadRecordRepository.save(readRecordEntity);
//
//            //查询点赞信息
//            VoteEntity v = new VoteEntity();
//            v.setArticleId(articleEntity.getId());
//            Example<VoteEntity> ve = Example.of(v);
//            Optional<VoteEntity> vOptional = mVoteRepository.findOne(ve);
//            VoteEntity voteEntity;
//            if (vOptional.isPresent()) {
//                voteEntity = vOptional.get();
//            } else {
//                voteEntity = new VoteEntity();
//                voteEntity.setArticleId(articleEntity.getId());
//                voteEntity.setVoteCount(Long.valueOf(0));
//            }
//
//            // 构建业务层返回值
//            Article article = new ArticleTransfer().fromEntity(articleEntity);
//            article.setParagraphs(new ParagraphTransfer().fromEntityList(paragraphEntityList));
//            article.setReadRecord(new ReadRecordTransfer().fromEntity(readRecordEntity));
//            article.setVote(new VoteTransfer().fromEntity(voteEntity));
//
//            return ResponseEntity.ok(article);
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//    }
//
//    /**
//     * 获取阅读量最高的文章列表
//     *
//     * @return
//     */
//    @RequestMapping("/hot")
//    @CrossOrigin
//    public ResponseEntity<List<Article>> getHotArticles(@RequestParam(value = "limit", defaultValue = "10") Integer limit) {
//        // 查询阅读量最高的文章id
//        Sort s = Sort.by(Sort.Order.desc("readCount"));
//        PageRequest pageRequest = PageRequest.of(0, limit, s);
//        List<ReadRecordEntity> readRecordEntityList = mReadRecordRepository.findAll(pageRequest).getContent();
//        List<ArticleEntity> articleEntityList = new ArrayList<>(readRecordEntityList.size());
//        final ArticleEntity nullArticleEntity = new ArticleEntity();
//        for (ReadRecordEntity e : readRecordEntityList) {
//            Optional<ArticleEntity> optional = mArticleRepository.findById(e.getArticleId());
//            if (optional.isPresent()) {
//                articleEntityList.add(optional.get());
//            } else {
//                articleEntityList.add(nullArticleEntity);
//            }
//        }
//
//        // 构建业务层返回值
//        ArticleTransfer articleTransfer = new ArticleTransfer();
//        ReadRecordTransfer readRecordTransfer = new ReadRecordTransfer();
//        List<Article> articleList = new ArrayList<>(articleEntityList.size());
//        for (int i = 0; i < articleEntityList.size(); i++) {
//            ArticleEntity ae = articleEntityList.get(i);
//            ReadRecordEntity re = readRecordEntityList.get(i);
//            if (ae != nullArticleEntity) {
//                Article a = articleTransfer.fromEntity(ae);
//                ReadRecord r = readRecordTransfer.fromEntity(re);
//                a.setReadRecord(r);
//                articleList.add(a);
//            }
//        }
//
//        return ResponseEntity.ok(articleList);
//    }
//
//    /**
//     * 给文章点赞
//     *
//     * @return
//     */
//    @PostMapping("/vote")
//    @CrossOrigin
//    public ResponseEntity<List<Article>> voteArticle(@RequestBody Article article) {
//        System.out.println("---------------------------");
//        System.out.println(article.getId());
//        System.out.println("---------------------------");
//        Optional<VoteEntity> optional = mVoteRepository.findById(article.getId());
//        VoteEntity voteEntity;
//        if (optional.isPresent()) {
//            voteEntity = optional.get();
//            voteEntity.setVoteCount(voteEntity.getVoteCount() + 1);
//        } else {
//            voteEntity = new VoteEntity();
//            voteEntity.setArticleId(article.getId());
//            voteEntity.setVoteCount(Long.valueOf(1));
//        }
//        mVoteRepository.save(voteEntity);
//
//        return ResponseEntity.ok(null);
//    }
//}