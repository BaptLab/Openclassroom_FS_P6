package com.openclassrooms.DTO;

import jakarta.validation.constraints.NotNull;

public class ArticleDTO {
	
    @NotNull
	private String title;
    
    @NotNull
	private String theme;
    
    @NotNull
	private String description;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
