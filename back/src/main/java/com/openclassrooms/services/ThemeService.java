package com.openclassrooms.services;

import com.openclassrooms.models.Theme;
import com.openclassrooms.models.User;
import com.openclassrooms.repository.ThemeRepository;

import java.util.List;

public class ThemeService {

    private final ThemeRepository themeRepository;

    public ThemeService(ThemeRepository themeRepository) {
        this.themeRepository = themeRepository;
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


}
