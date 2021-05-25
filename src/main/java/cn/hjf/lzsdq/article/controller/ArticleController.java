package cn.hjf.lzsdq.article.controller;

import cn.hjf.lzsdq.article.dao.table.Article;
import cn.hjf.lzsdq.article.dao.repository.ArticleRepository;
import cn.hjf.lzsdq.article.dao.repository.ParagraphRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    @Autowired
    private ArticleRepository mArticleRepository;
    private ParagraphRepository mParagraphRepository;

//    @RequestMapping("/")
//    @CrossOrigin
//    public ResponseEntity<List<Knowledge>> getKnowledgeByPage(@RequestParam(value = "page_num", defaultValue = "1") Integer pageNum,
//                                                              @RequestParam(value = "page_size", defaultValue = "10") Integer pageSize) {
//        System.out.println("pageNum  " + pageNum);
//        System.out.println("pageSize  " + pageSize);
//        return ResponseEntity.ok(knowledgeRepository.findAll(PageRequest.of(pageNum - 1, pageSize)).getContent());
//    }
//
//    @GetMapping("/{kid}")
//    @CrossOrigin
//    public ResponseEntity<Knowledge> getKnowledgeById(@PathVariable(value = "kid") Integer knowledgeId) {
//        System.out.println("knowledgeId  " + knowledgeId);
//        Optional<Knowledge> optional = knowledgeRepository.findById(Long.valueOf(knowledgeId));
//        if (optional.isPresent()) {
//            return ResponseEntity.ok(optional.get());
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//    }

    @GetMapping("/{aid}")
    @CrossOrigin
    public ResponseEntity<Article> getArticleById(@PathVariable(value = "aid") Integer articleId) {
        Optional<Article> optional = mArticleRepository.findById(Long.valueOf(articleId));
        if (optional.isPresent()) {
            Article article = optional.get();

//            Paragraph p = new Paragraph();
//            p.setArticleId(article.getId());
//            Example<Paragraph> example = Example.of(p);
//            Sort s = Sort.by(Sort.Order.desc("sequence"));
//            List<Paragraph> paragraphList = mParagraphRepository.findAll(example, s);
//
//            article.setParagraphs(paragraphList);

            return ResponseEntity.ok(article);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}