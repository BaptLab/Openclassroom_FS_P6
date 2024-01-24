package com.openclassrooms.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.DTO.UserDTO;
import com.openclassrooms.exceptions.BadRequest;
import com.openclassrooms.models.Theme;
import com.openclassrooms.models.User;
import com.openclassrooms.models.UserTheme;
import com.openclassrooms.payload.response.MessageResponse;
import com.openclassrooms.services.ThemeService;
import com.openclassrooms.services.UserService;
import com.openclassrooms.services.UserThemeService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/themes")
public class ThemeController {

	@Autowired
	private ThemeService themeService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserThemeService userThemeService;

	@GetMapping("")
	public ResponseEntity<?> findAll() {
		try {
			List<Theme> themes = themeService.findAll();
			return ResponseEntity.ok().body(themes);
		} catch (NumberFormatException e) {
			return ResponseEntity.badRequest().body(new BadRequest());
		}
	}

	@PostMapping("/{id}/subscribe/{themeId}")
	public ResponseEntity<?> subscribeToTheme(@PathVariable("id") Long userId, @PathVariable("themeId") Long themeId) {
		try {
			User user = userService.findById(userId);
			if (user == null) {
				return ResponseEntity.notFound().build();
			}
			Theme theme = themeService.findById(themeId);
			if (theme == null) {
				return ResponseEntity.notFound().build();
			}
			UserTheme userTheme = userThemeService.subscribeUserToTheme(user, theme);
			if (userTheme != null) {
				return ResponseEntity.ok(new MessageResponse("Vous êtes désormais abonné à ce thème !"));
			} else {
				return ResponseEntity.ok(new MessageResponse("Vous êtes déjà abonné à ce thème."));
			}
		} catch (NumberFormatException e) {
			return ResponseEntity.badRequest().body(new BadRequest());
		}
	}

	@DeleteMapping("/{id}/subscribe/{themeId}")
	public ResponseEntity<?> unSubscribeToTheme(@PathVariable("id") Long userId, @PathVariable("themeId") Long themeId,
			@RequestBody UserDTO userDto) {
		try {
			User user = userService.findById(userId);
			if (user == null) {
				return ResponseEntity.notFound().build();
			}
			Theme theme = themeService.findById(themeId);
			if (theme == null) {
				return ResponseEntity.notFound().build();
			}
			userThemeService.unSubscribeUserToTheme(user, theme);
			return ResponseEntity.ok(new MessageResponse("Vous êtes désormais désabonné de ce thème !"));
		} catch (NumberFormatException e) {
			return ResponseEntity.badRequest().body(new BadRequest());
		}
	}
}
