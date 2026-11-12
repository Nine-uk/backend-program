package me.gdu.springdeveloper.service;

import lombok.RequiredArgsConstructor;
import me.gdu.springdeveloper.dao.Article;
import me.gdu.springdeveloper.dto.ArticleCreationRequest;
import me.gdu.springdeveloper.dto.ArticleUpdateRequest;
import me.gdu.springdeveloper.repository.BlogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BlogService {
    private final BlogRepository blogRepository;

    public Article save(ArticleCreationRequest articleCreationRequest){
        return blogRepository.save(articleCreationRequest.toEntity());
    }

    public List<Article> findAll(){
        return blogRepository.findAll();
    }

    public Article findById(long id) {
        return blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
    }

    public void delete(long id){
        blogRepository.deleteById(id);
    }

//    @Transactional
//    public Article updateArticle(long id, ArticleUpdateRequest request){
//        Article article = blogRepository.findById(id).orElseThrow(()->new IllegalArgumentException("Article not Found"));
//        article.update(request.getTitle(), request.getContent());
//
//        return blogRepository.save(article);
//    }

    @Transactional
    public Article updateArticle(long id, ArticleUpdateRequest request){
        Article article = blogRepository.findById(id).orElseThrow(()->new IllegalArgumentException("Article not Found"));
        article.update(request.getTitle(), request.getContent());

        return article;
    }
}
