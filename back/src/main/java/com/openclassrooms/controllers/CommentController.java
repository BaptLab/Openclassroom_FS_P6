package com.openclassrooms.controllers;

import com.openclassrooms.DTO.CommentDTO;
import com.openclassrooms.models.Article;
import com.openclassrooms.models.Comment;
import com.openclassrooms.models.User;
import com.openclassrooms.services.ArticleService;
import com.openclassrooms.services.CommentService;
import com.openclassrooms.services.UserService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class CommentController {

	@Autowired
	private UserService userService;

	@Autowired
	private CommentService commentService;

	@Autowired
	private ArticleService articleService;

	@GetMapping("/{articleId}/comments")
	public ResponseEntity<?> findAll(@PathVariable("articleId") String articleId) {
		try {
			List<Comment> comments = commentService.findAllByArticleId(Long.valueOf(articleId));
			return ResponseEntity.ok().body(comments);
		} catch (NumberFormatException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request format");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving articles");
		}
	}

	@PostMapping("/{id}/comment/{articleId}")
	public ResponseEntity<?> postComment(@PathVariable("id") String id, @PathVariable("articleId") String articleId,
			@RequestBody CommentDTO commentDTO) {
		try {
			User user = userService.findById(Long.valueOf(id));
			Article article = articleService.findById(Long.valueOf(articleId));

			if (user == null || article == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or article not found");
			}

			Comment comment = commentService.convertDtoToComment(user, article, commentDTO);
			Comment savedComment = commentService.save(comment);
			return ResponseEntity.ok().body(savedComment);
		} catch (NumberFormatException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid user ID or article ID format");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error posting comment");
		}
	}
}
