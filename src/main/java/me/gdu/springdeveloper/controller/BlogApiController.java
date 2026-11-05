package me.gdu.springdeveloper.controller;

import lombok.RequiredArgsConstructor;
import me.gdu.springdeveloper.dao.Article;
import me.gdu.springdeveloper.dto.ArticleCreationRequest;
import me.gdu.springdeveloper.dto.ArticleResponse;
import me.gdu.springdeveloper.service.BlogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class BlogApiController {


    private final BlogService blogService;

    @PostMapping("/api/articles")
    public ResponseEntity<Article> addArticle(@RequestBody ArticleCreationRequest articleCreationRequest){
        // 서비스에게 블로그 글 등록 의뢰
        Article savedArticle = blogService.save(articleCreationRequest);
        // 성공했다면 ResponseEntity 객체를 반환
        return ResponseEntity.status(HttpStatus.CREATED).body(savedArticle);
    }

    @GetMapping("/api/articles")
    public ResponseEntity<List<ArticleResponse>> getAllArticles(){
        /*
        List<Article> articleList = blogService.findAll();
        List<ArticleResponse> articleResponseList = new ArrayList<>();
        for (Article article : articleList){
            articleResponseList.add(new ArticleResponse(article));
        }
        */

        List<ArticleResponse> articleResponseList = blogService.findAll().stream().map(ArticleResponse::new).toList();

        return ResponseEntity.ok().body(articleResponseList);
    }
}
