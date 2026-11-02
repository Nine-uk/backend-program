package me.gdu.springdeveloper.repository;


import me.gdu.springdeveloper.dao.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Article, Long> {
    // findById() , findAll(), save(), saveAll(), deleteById(), deleteAll()

}
