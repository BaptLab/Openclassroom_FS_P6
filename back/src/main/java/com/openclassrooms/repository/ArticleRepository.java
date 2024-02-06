package com.openclassrooms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.models.Article;
import com.openclassrooms.models.Theme;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long>{

}
