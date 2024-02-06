package com.openclassrooms.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.openclassrooms.DTO.ArticleDTO;
import com.openclassrooms.models.Article;
import com.openclassrooms.models.Theme;
import com.openclassrooms.models.User;
import com.openclassrooms.models.UserTheme;
import com.openclassrooms.repository.ArticleRepository;
import com.openclassrooms.repository.ThemeRepository;

@Service
public class ArticleService {

	private ArticleRepository articleRepository;
	private ThemeRepository themeRepository;


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
	
	public List<Article> findAllByUserThemes(List<UserTheme> userThemes) {
        List<Long> themeIds = userThemes.stream()
                .map(UserTheme::getThemeId)
                .collect(Collectors.toList());

        // Find all articles
        List<Article> allArticles = articleRepository.findAll();

        // Filter articles by themeIds
        List<Article> filteredArticles = allArticles.stream()
                .filter(article -> themeIds.contains(article.getId()))
                .collect(Collectors.toList());

        return filteredArticles;
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
