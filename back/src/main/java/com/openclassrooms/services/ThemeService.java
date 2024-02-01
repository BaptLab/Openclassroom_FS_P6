package com.openclassrooms.services;

import com.openclassrooms.models.Theme;
import com.openclassrooms.models.User;
import com.openclassrooms.models.UserTheme;
import com.openclassrooms.repository.ThemeRepository;
import com.openclassrooms.repository.UserThemeRepository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class ThemeService {

    private final ThemeRepository themeRepository;
    private final UserThemeRepository userThemeRepository;

    public ThemeService(ThemeRepository themeRepository, UserThemeRepository userThemeRepository) {
        this.themeRepository = themeRepository;
        this.userThemeRepository = userThemeRepository;
    }

    public Theme findById(Long id) {
        return themeRepository.findById(id).orElse(null);
    }

    public List<Theme> findAll() {
        return themeRepository.findAll();
    }

    public void delete(Long id) {
        themeRepository.deleteById(id);
    }

    public Theme save(Theme theme) {
        return themeRepository.save(theme);
    }

	public void subscribeToTheme(User user, Theme theme) {
		
	}
	
	public List<Theme> findThemesByUserId(Long userId) {
	    System.out.println("User ID: " + userId);
	    List<UserTheme> userThemes = userThemeRepository.findByUserId(userId);
	    List<Long> themeIds = userThemes.stream().map(UserTheme::getThemeId).collect(Collectors.toList());
	    List<Theme> themes = themeRepository.findAllById(themeIds);
	    System.out.println("Themes: " + themes);
	    return themes;
	}


}
