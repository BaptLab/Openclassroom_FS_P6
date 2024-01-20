package com.openclassrooms.services;

import java.util.List;

import com.openclassrooms.models.Article;
import com.openclassrooms.repository.ArticleRepository;

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
}
