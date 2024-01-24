package com.openclassrooms.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.DTO.CommentDTO;
import com.openclassrooms.exceptions.BadRequest;
import com.openclassrooms.models.Article;
import com.openclassrooms.models.Comment;
import com.openclassrooms.models.User;
import com.openclassrooms.services.ArticleService;
import com.openclassrooms.services.CommentService;
import com.openclassrooms.services.UserService;

@RestController
@RequestMapping("/api")
public class CommentController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private ArticleService articleService;
	
	@PostMapping("/{id}/comment/{articleId}")
	public ResponseEntity<?> postComment(@PathVariable("id") String id, @PathVariable("articleId") String userId, @RequestBody CommentDTO commentDTO) {
	    try {
	        User user = userService.findById(Long.valueOf(userId));
	        Article article = articleService.findById(Long.valueOf(id));
	        Comment comment = commentService.convertDtoToComment(user,article ,commentDTO);
	        Comment savedComment = commentService.save(comment);
	        return ResponseEntity.ok().body(savedComment);
	    } catch (NumberFormatException e) {
	        return ResponseEntity.badRequest().body(new BadRequest());
	    }
	}

}
