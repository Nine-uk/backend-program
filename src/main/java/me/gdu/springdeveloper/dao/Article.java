package me.gdu.springdeveloper.dao;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id; // PK
    @Column(nullable = false)
    private String title; // NN
    @Column(nullable = false)
    private String content; // NN

    @Builder
    public Article(String title, String content){
        this.title = title;
        this.content = content;
        // 이걸로 객체생성을 Ariticle.builder().title("A").content("B"); 로 생성가능
    }
}
