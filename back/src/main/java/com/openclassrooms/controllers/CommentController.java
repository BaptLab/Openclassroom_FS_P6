package com.openclassrooms.controllers;

import com.openclassrooms.DTO.CommentDTO;
import com.openclassrooms.models.Article;
import com.openclassrooms.models.Comment;
import com.openclassrooms.models.User;
import com.openclassrooms.services.ArticleService;
import com.openclassrooms.services.CommentService;
import com.openclassrooms.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> postComment(@PathVariable("id") String id, @PathVariable("articleId") String userId,
                                         @RequestBody CommentDTO commentDTO) {
        try {
            User user = userService.findById(Long.valueOf(userId));
            Article article = articleService.findById(Long.valueOf(id));

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
