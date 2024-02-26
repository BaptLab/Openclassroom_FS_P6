package com.openclassrooms.controllers;

import com.openclassrooms.DTO.ArticleDTO;
import com.openclassrooms.models.Article;
import com.openclassrooms.models.Theme;
import com.openclassrooms.models.User;
import com.openclassrooms.models.UserTheme;
import com.openclassrooms.repository.ArticleRepository;
import com.openclassrooms.services.ArticleService;
import com.openclassrooms.services.ThemeService;
import com.openclassrooms.services.UserService;
import com.openclassrooms.services.UserThemeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class ArticleController {

	@Autowired
	private ArticleService articleService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserThemeService userThemeService;

	@GetMapping("/{id}/articles")
	public ResponseEntity<?> findAllByUser(@PathVariable("id") Long userId) {
		try {
			User user = userService.findById(userId);
			if (user != null) {
				List<UserTheme> userThemes = userThemeService.findUserThemesByUserId(userId);
				List<Article> articles = articleService.findAllByUserThemes(userThemes);


				return ResponseEntity.ok().body(articles);
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
			}
		} catch (NumberFormatException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request format");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving articles");
		}
	}

	@GetMapping("/article/{id}")
	public ResponseEntity<?> findById(@PathVariable("id") Long articleId) {
		try {
			Article article = articleService.findById(articleId);
			if (article == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Article not found");
			}
			return ResponseEntity.ok().body(article);
		} catch (NumberFormatException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid article ID format");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving article");
		}
	}

	@PostMapping("/{id}/article")
	public ResponseEntity<?> createArticle(@PathVariable("id") String userId, @RequestBody ArticleDTO articleDTO) {
		try {
			User user = userService.findById(Long.valueOf(userId));
			Article article = articleService.convertDtoToArticle(user, articleDTO);
			Article savedArticle = articleService.save(article);
			return ResponseEntity.ok().body(savedArticle);
		} catch (NumberFormatException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid user ID format");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating article");
		}
	}
}
