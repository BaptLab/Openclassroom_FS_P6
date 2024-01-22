package com.openclassrooms.services;

import java.util.List;

import com.openclassrooms.DTO.CommentDTO;
import com.openclassrooms.models.Comment;
import com.openclassrooms.models.User;
import com.openclassrooms.repository.CommentRepository;

public class CommentService {
	private CommentRepository commentRepository;

	public CommentService(CommentRepository commentRepository) {
		this.commentRepository = commentRepository;
	}

	public Comment findById(Long id) {
		return this.commentRepository.findById(id).orElse(null);
	}

	public void delete(Long id) {
		this.commentRepository.deleteById(id);
	}

	public Comment save(Comment comment) {
		return this.commentRepository.save(comment);
	}

	public List<Comment> findAll() {
		return this.commentRepository.findAll();
	}

	public Comment convertDtoToComment(User user, CommentDTO commentDTO) {
		Comment comment = new Comment();
		comment.setArticlId(commentDTO.getArticleId());
		comment.setUserId(commentDTO.getUserId());
		comment.setDescription(commentDTO.getDescription());
		return comment;
	}
}
