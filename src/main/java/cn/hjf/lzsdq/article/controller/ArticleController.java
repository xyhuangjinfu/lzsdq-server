package cn.hjf.lzsdq.article.controller;

import cn.hjf.lzsdq.article.biz.model.Article;
import cn.hjf.lzsdq.article.biz.service.ArticleService;
import cn.hjf.lzsdq.utils.Paging;
import cn.hjf.lzsdq.utils.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    /**
     * 分页获取文章列表
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("/")
    @CrossOrigin
    public ResponseEntity<Paging<Article>> getArticlesByPage(@RequestParam(value = "page_num", defaultValue = "1") Integer pageNum,
                                                             @RequestParam(value = "page_size", defaultValue = "10") Integer pageSize) {
        ArticleService articleService = new ArticleService();
        Paging<Article> paging = articleService.pageQuery(pageNum, pageSize);
        return ResponseEntity.ok(paging);
    }

    /**
     * 根据文章id获取文章详细信息
     *
     * @param articleId
     * @return
     */
    @GetMapping("/{article_id}")
    @CrossOrigin
    public ResponseEntity<Article> getArticleById(@PathVariable(value = "article_id") Integer articleId) {
        ArticleService articleService = new ArticleService();
        Article article = articleService.articleDetail(Long.valueOf(articleId));

        if (article == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(article);
    }

    /**
     * 获取阅读量最高的文章列表
     *
     * @return
     */
    @RequestMapping("/hot")
    @CrossOrigin
    public ResponseEntity<List<Article>> getHotArticles(@RequestParam(value = "limit", defaultValue = "10") Integer limit) {
        ArticleService articleService = new ArticleService();
        List<Article> articleList = articleService.hotArticleList(limit);
        return ResponseEntity.ok(articleList);
    }

    /**
     * 给文章点赞
     *
     * @return
     */
    @PostMapping("/vote")
    @CrossOrigin
    public ResponseEntity<Result> voteArticle(@RequestBody Article article) {
        ArticleService articleService = new ArticleService();
        boolean success = articleService.updateVoteCount(article.getId());
        Result result = new Result();
        result.setSuccess(success);
        return ResponseEntity.ok(result);
    }

    /**
     * 记录阅读次数
     *
     * @return
     */
    @PostMapping("/read")
    @CrossOrigin
    public ResponseEntity<Result> readArticle(@RequestBody Article article) {
        ArticleService articleService = new ArticleService();
        boolean success = articleService.updateReadCount(article.getId());
        Result result = new Result();
        result.setSuccess(success);
        return ResponseEntity.ok(result);
    }
}