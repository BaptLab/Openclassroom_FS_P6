package com.openclassrooms.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.openclassrooms.DTO.ArticleDTO;
import com.openclassrooms.models.Article;
import com.openclassrooms.models.User;
import com.openclassrooms.repository.ArticleRepository;

@Service
public class ArticleService {

	private ArticleRepository articleRepository;

	public ArticleService(ArticleRepository articleRepository) {
		this.articleRepository = articleRepository;
	}

	public Article findById(Long id) {
		return articleRepository.findById(id).orElse(null);
	}

	public List<Article> findAll() {
		return articleRepository.findAll();
	}

	public void delete(Long id) {
		articleRepository.deleteById(id);
	}

	public Article save(Article article) {
		return articleRepository.save(article);
	}
	
	public Article convertDtoToArticle(User user, ArticleDTO articleDTO) {
		Article article = new Article();
		article.setTitle(articleDTO.getTitle());
		article.setTheme(articleDTO.getTheme());
		article.setDescription(articleDTO.getDescription());
		article.setUserId(user.getId());
		return article;
	}
}
