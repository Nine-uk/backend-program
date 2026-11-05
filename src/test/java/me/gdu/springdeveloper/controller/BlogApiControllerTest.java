package me.gdu.springdeveloper.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.gdu.springdeveloper.dao.Article;
import me.gdu.springdeveloper.dto.ArticleCreationRequest;
import me.gdu.springdeveloper.repository.BlogRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class BlogApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BlogRepository blogRepository;

    @AfterEach
    public void cleanUp(){
        blogRepository.deleteAll();
    }

    @Test
    @DisplayName("addArticle : 블로그 글 추가에 성공한다.")
    public void addAriticle() throws Exception {
        // 준비 given : 저장할 데이터를 준비, 요청 URL 준비
        String title = "JUnit 테스트를 통한 블로그 글 생성";
        String content = "수업 종료 10분전, 이 테스트 성공 후 점심 식사한다.";
        String url = "/api/articles";
        ArticleCreationRequest req = new ArticleCreationRequest(title, content);
        String articleStr = objectMapper.writeValueAsString(req);

        // 실행 when
        ResultActions result = mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(articleStr));

        // 검증 then : DB에서 인출해보겠다.
        List<Article> articles = blogRepository.findAll();
        assertThat(articles.size()).isEqualTo(1);
        Article article = articles.get(0);
        assertThat(article.getTitle()).isEqualTo(title);
        assertThat(article.getContent()).isEqualTo(content);
    }

    @DisplayName("모든 블로그글 조회에 성공한다")
    @Test
    public void getAllArticles() throws Exception{
        // 준비 given : url, 데이터준비
        String url = "/api/articles";
        String title = "11월 5일 두번째 시간";
        String content = "모든 블로그글 조회 테스트 코드 작성";

        blogRepository.save(Article.builder().content(content).title(title).build());

        // 실행 when
        ResultActions resultActions = mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON));

        // 검증 then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value(title))
                .andExpect(jsonPath("$[0].content").value(content));
    }
}