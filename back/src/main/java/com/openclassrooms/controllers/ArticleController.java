package com.openclassrooms.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.DTO.ArticleDTO;
import com.openclassrooms.exceptions.BadRequest;
import com.openclassrooms.models.Article;
import com.openclassrooms.models.User;
import com.openclassrooms.services.ArticleService;
import com.openclassrooms.services.UserService;

@RestController
@RequestMapping("/api")
public class ArticleController {
	
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private UserService userService;


	@GetMapping("/articles")
	public ResponseEntity<?> findAll() {
		try {
			List<Article> articles = articleService.findAll();
			return ResponseEntity.ok().body(articles );
		} catch (NumberFormatException e) {
			return ResponseEntity.badRequest().body(new BadRequest());
		}
	}
	
	@GetMapping("/article/{id}")
	public ResponseEntity<?> findById(@PathVariable("id") Long articleId) {
		try {
			Article article = articleService.findById(articleId);
			return ResponseEntity.ok().body(article);
		} catch (NumberFormatException e) {
			return ResponseEntity.badRequest().body(new BadRequest());
		}
	}
	
	@PostMapping("/{id}/article")
	public ResponseEntity<?> createComment(@PathVariable("id") String userId, @RequestBody ArticleDTO articleDTO) {
	    try {
	        User user = userService.findById(Long.valueOf(userId));
	        Article article = articleService.convertDtoToArticle(user, articleDTO);
	        Article savedArticle = articleService.save(article);
	        return ResponseEntity.ok().body(savedArticle);
	    } catch (NumberFormatException e) {
	        return ResponseEntity.badRequest().body(new BadRequest());
	    }
	}

}
