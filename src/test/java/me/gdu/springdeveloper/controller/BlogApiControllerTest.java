package me.gdu.springdeveloper.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.gdu.springdeveloper.dao.Article;
import me.gdu.springdeveloper.dto.ArticleCreationRequest;
import me.gdu.springdeveloper.dto.UpdateArticleRequest;
import me.gdu.springdeveloper.repository.BlogRepository;
import org.junit.jupiter.api.BeforeEach;
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

    @BeforeEach
    public void cleanUp(){
        blogRepository.deleteAll();
    }

    @Test
    @DisplayName("addArticle : 블로그 글 추가에 성공한다.")
    public void addArticle() throws Exception {
        // 준비 given : 저장할 데이터를 준비, 요청 URL 준비
        String title = "JUnit 테스트를 통한 블로그 글 생성";
        String content = "수업 종료 10분전, 이 테스트 성공 후 점심 식사한다.";
        String url = "/api/articles";
        ArticleCreationRequest req = new ArticleCreationRequest(title, content);
        String articleStr = objectMapper.writeValueAsString(req);

        // 실행 when
        ResultActions result = mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(articleStr));

        // 검증 then
        List<Article> articles = blogRepository.findAll();
        // 200반환 확인
        result.andExpect(status().isCreated());
        // findAll이 1개 반환하는지
        assertThat(articles.size()).isEqualTo(1);
        // 저장된 데이터가 전달한 데이터와 같은지확인
        Article article = articles.get(0);
        assertThat(article.getTitle()).isEqualTo(title);
        assertThat(article.getContent()).isEqualTo(content);
    }

    @DisplayName("findArticle: 블로그 글 조회에 성공한다.")
    @Test
    public void findArticle() throws Exception {
        // given
        final String url = "/api/articles/{id}";
        final String title = "title";
        final String content = "content";
        Article savedArticle = blogRepository.save(Article.builder()
                .title(title)
                .content(content)
                .build());

        // when
        final ResultActions resultActions = mockMvc.perform(get(url, savedArticle.
                getId()));

        // then
        // 200인지 확인, 전달한 데이터가 저장된 데이터와 일치하는지 판단
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value(content))
                .andExpect(jsonPath("$.title").value(title));
    }

    @DisplayName("updateArticle: 블로그 글 수정에 성공한다.")
    @Test
    public void updateArticle() throws Exception{
        // given
        final String url = "/api/articles/{id}";
        final String title = "title";
        final String content = "content";
        Article savedArticle = blogRepository.save(Article.builder().title(title).content(content).build());

        // update할 내용 dto에 저장
        final String newTitle = "title!";
        final String newContent = "content!";
        UpdateArticleRequest req = new UpdateArticleRequest(newTitle, newContent);

        // when
        // savedArticle 실행한 id에 해당하는 데이터에 req전송해 수정
        ResultActions result = mockMvc.perform(put(url, savedArticle.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(req)));

        // then
        // 200인지 확인
        result.andExpect(status().isOk());

        // 업데이트 내용이 newTitle과 newContent와 같은지 비교
        Article article = blogRepository.findById(savedArticle.getId()).get();
        assertThat(article.getTitle()).isEqualTo(newTitle);
        assertThat(article.getContent()).isEqualTo(newContent);
    }

    @DisplayName("deleteArticle: 블로그 글 삭제에 성공한다.")
    @Test
    public void deleteArticle() throws Exception {
        // given
        final String url = "/api/articles/{id}";
        final String title = "title";
        final String content = "content";
        Article savedArticle = blogRepository.save(Article.builder()
                .title(title)
                .content(content)
                .build());

        // when
        final ResultActions resultActions = mockMvc.perform(delete(url, savedArticle.getId()));

        // then
        // 200인지
        resultActions.andExpect(status().isOk());
        List<Article> articles = blogRepository.findAll();
        // findAll해온 배열이 비어있는지 확인
        assertThat(articles).isEmpty();
    }

    @DisplayName("모든 블로그글 조회에 성공한다")
    @Test
    public void getAllArticles() throws Exception{
        // 준비 given : url, 데이터준비
        String url = "/api/articles";
        String title = "11월 5일 두번째 시간";
        String content = "모든 블로그글 조회 테스트 코드 작성";

        // 2개의 데이터 저장
        blogRepository.save(Article.builder().content(content).title(title).build());
        blogRepository.save(Article.builder().content(content).title(title).build());

        // 실행 when
        ResultActions resultActions = mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON));

        // 검증 then
        // 총 갯수가 2개인지, 1번째로 저장된 데이터가 전달한 데이터와 같은지 확인
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].title").value(title))
                .andExpect(jsonPath("$[0].content").value(content));
    }
}