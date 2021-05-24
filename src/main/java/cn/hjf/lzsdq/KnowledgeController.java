package cn.hjf.lzsdq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/knowledges")
public class KnowledgeController {

    @Autowired
    private KnowledgeRepository knowledgeRepository;

    @RequestMapping("/")
    @CrossOrigin
    public ResponseEntity<List<Knowledge>> getKnowledgeByPage(@RequestParam(value = "page_num", defaultValue = "1") Integer pageNum,
                                                              @RequestParam(value = "page_size", defaultValue = "10") Integer pageSize) {
        System.out.println("pageNum  " + pageNum);
        System.out.println("pageSize  " + pageSize);
        return ResponseEntity.ok(knowledgeRepository.findAll(PageRequest.of(pageNum - 1, pageSize)).getContent());
    }

    @GetMapping("/{kid}")
    @CrossOrigin
    public ResponseEntity<Knowledge> getKnowledgeById(@PathVariable(value = "kid") Integer knowledgeId) {
        System.out.println("knowledgeId  " + knowledgeId);
        Optional<Knowledge> optional = knowledgeRepository.findById(Long.valueOf(knowledgeId));
        if (optional.isPresent()) {
            return ResponseEntity.ok(optional.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}