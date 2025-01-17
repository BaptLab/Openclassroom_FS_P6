package com.openclassrooms.models;

import java.util.List;

import jakarta.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@Table(name = "THEMES")
@EntityListeners(AuditingEntityListener.class)
public class Theme {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String description;
	
	private String title;
	
	@ManyToMany(mappedBy = "themes", cascade = CascadeType.ALL)
    private List<User> users;

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
