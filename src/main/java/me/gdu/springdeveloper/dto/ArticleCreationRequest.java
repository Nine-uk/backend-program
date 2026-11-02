package me.gdu.springdeveloper.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.gdu.springdeveloper.dao.Article;

@Getter
@AllArgsConstructor
public class ArticleCreationRequest {
    private String title;
    private String content;

    public Article toEntity(){
        // return new Article(this.title, this.content);
        return  Article.builder().title(this.title).content(this.content).build();
    }
}
