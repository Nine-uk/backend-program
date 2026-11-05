package me.gdu.springdeveloper.service;

import lombok.RequiredArgsConstructor;
import me.gdu.springdeveloper.dao.Article;
import me.gdu.springdeveloper.dto.ArticleCreationRequest;
import me.gdu.springdeveloper.repository.BlogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
