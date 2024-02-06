package com.openclassrooms.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.openclassrooms.models.Theme;
import com.openclassrooms.models.User;
import com.openclassrooms.models.UserTheme;
import com.openclassrooms.repository.UserThemeRepository;

@Service
public class UserThemeService {

	@Autowired
	private UserThemeRepository userThemeRepository;

	@Transactional
	public UserTheme save(UserTheme userTheme) {
		return this.userThemeRepository.save(userTheme);
	}

	@Transactional
	public void delete(UserTheme userTheme) {
		this.userThemeRepository.delete(userTheme);
	}

	@Transactional
	public UserTheme subscribeUserToTheme(User user, Theme theme) {
		if (!this.isUserAlreadySubscribed(user, theme)) {
			UserTheme userTheme = new UserTheme();
			userTheme.setThemeId(theme.getId());
			userTheme.setUserId(user.getId());
			// Save the userTheme entity using the save method
			return this.save(userTheme);
		}
		return null;
	}

	@Transactional
	public boolean isUserAlreadySubscribed(User user, Theme theme) {
		return this.userThemeRepository.existsByUserIdAndThemeId(user.getId(), theme.getId());
	}

	@Transactional
	public void unSubscribeUserToTheme(User user, Theme theme) {
		if (this.isUserAlreadySubscribed(user, theme)) {
			this.userThemeRepository.deleteByUserIdAndThemeId(user.getId(), theme.getId());
		}
	}
	
	@Transactional
    public List<UserTheme> findUserThemesByUserId(Long userId) {
        return userThemeRepository.findByUserId(userId);
    }
}
