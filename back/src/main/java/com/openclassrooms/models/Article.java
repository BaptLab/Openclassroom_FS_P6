package com.openclassrooms.models;

import java.time.LocalDateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name="ARTICLES")
@EntityListeners(AuditingEntityListener.class)
public class Article {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull(message = "title may not be null")
	private String title;
	
	@NotNull(message = "theme may not be null")
	private String theme;
	
	@NotNull(message = "userId may not be null")
	@Column(name="user_id")
	private  Long userId;
	
	@CreatedDate
	@Column(name="created_at")
	private LocalDateTime createdAt;
	
	@UpdateTimestamp
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

}
