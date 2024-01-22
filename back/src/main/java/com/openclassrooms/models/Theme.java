package com.openclassrooms.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@Table(name = "THEMES")
@EntityListeners(AuditingEntityListener.class)
public class Theme {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "description may not be null")
	private String description;
	
	@NotNull(message = "title may not be null")
	private String title;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	
}
