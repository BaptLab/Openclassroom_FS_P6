package com.openclassrooms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.models.Article;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long>{

}
