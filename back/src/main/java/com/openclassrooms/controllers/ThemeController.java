package com.openclassrooms.controllers;

import com.openclassrooms.DTO.UserDTO;
import com.openclassrooms.models.Theme;
import com.openclassrooms.models.User;
import com.openclassrooms.models.UserTheme;
import com.openclassrooms.payload.response.MessageResponse;
import com.openclassrooms.services.ThemeService;
import com.openclassrooms.services.UserService;
import com.openclassrooms.services.UserThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request format");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving themes");
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> findAllSubscribedTheme(@PathVariable("id") Long userId) {
        try {
            List<Theme> themes = themeService.findThemesByUserId(userId);
            System.out.println(themes);
            return ResponseEntity.ok().body(themes);
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request format");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving themes");
        }
    }

    @PostMapping("/{id}/subscribe/{themeId}")
    public ResponseEntity<?> subscribeToTheme(@PathVariable("id") Long userId, @PathVariable("themeId") Long themeId) {
        try {
            User user = userService.findById(userId);
            Theme theme = themeService.findById(themeId);

            if (user == null || theme == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or theme not found");
            }

            UserTheme userTheme = userThemeService.subscribeUserToTheme(user, theme);

            if (userTheme != null) {
                return ResponseEntity.ok(new MessageResponse("Vous êtes désormais abonné à ce thème !"));
            } else {
                return ResponseEntity.ok(new MessageResponse("Vous êtes déjà abonné à ce thème."));
            }
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid user ID or theme ID format");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error subscribing to theme");
        }
    }

    @DeleteMapping("/{id}/subscribe/{themeId}")
    public ResponseEntity<?> unSubscribeToTheme(@PathVariable("id") Long userId, @PathVariable("themeId") Long themeId) {
        try {
            User user = userService.findById(userId);
            Theme theme = themeService.findById(themeId);

            if (user == null || theme == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or theme not found");
            }

            userThemeService.unSubscribeUserToTheme(user, theme);
            return ResponseEntity.ok(new MessageResponse("Vous êtes désormais désabonné de ce thème !"));
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid user ID or theme ID format");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error unsubscribing from theme");
        }
    }
}
