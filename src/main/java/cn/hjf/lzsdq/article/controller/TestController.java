//package cn.hjf.lzsdq.article.controller;//package cn.hjf.lzsdq.article.controller;
//
//import cn.hjf.lzsdq.article.biz.model.Article;
//import cn.hjf.lzsdq.article.dao.repository.LzsdqRepository;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/test/articles")
//public class TestController {
//
////    @Autowired
////    private LzsdqRepository mLzsdqRepository;
//
//    /**
//     * 给文章点赞
//     *
//     * @return
//     */
//    @PostMapping("/vote")
//    @CrossOrigin
//    public ResponseEntity<Boolean> voteArticle(@RequestBody Article article) {
//        System.out.println("---------------------------");
//        System.out.println(article.getId());
//        System.out.println("---------------------------");
//
//        LzsdqRepository mLzsdqRepository = new LzsdqRepository();
//
//        boolean result = mLzsdqRepository.vote(article.getId());
//        System.out.println(result);
//        System.out.println("---------------------------");
//        return ResponseEntity.ok(result);
//
////        Optional<VoteEntity> optional = mVoteRepository.findById(article.getId());
////        VoteEntity voteEntity;
////        if (optional.isPresent()) {
////            voteEntity = optional.get();
////            voteEntity.setVoteCount(voteEntity.getVoteCount() + 1);
////        } else {
////            voteEntity = new VoteEntity();
////            voteEntity.setArticleId(article.getId());
////            voteEntity.setVoteCount(Long.valueOf(1));
////        }
////        mVoteRepository.save(voteEntity);
////
////        return ResponseEntity.ok(null);
//    }
//}