package com.openclassrooms.services;

import com.openclassrooms.models.Theme;
import com.openclassrooms.models.User;
import com.openclassrooms.models.UserTheme;
import com.openclassrooms.repository.UserThemeRepository;

public class UserThemeService {

	private UserThemeRepository userThemeRepository;

	public UserTheme save(UserTheme userTheme) {
		return userThemeRepository.save(userTheme);
	}
	
	public void delete(UserTheme userTheme) {
		userThemeRepository.delete(userTheme);
	}

	public UserTheme subscribeUserToTheme(User user, Theme theme) {
		if (!isUserAlreadySubscribed(user, theme)) {
			UserTheme userTheme = new UserTheme();
			userTheme.setThemeId(theme.getId());
			userTheme.setUserId(user.getId());
			// Save the userTheme entity using the save method
			return save(userTheme);
		}
		return null;
	}

	public boolean isUserAlreadySubscribed(User user, Theme theme) {
		return userThemeRepository.existsByUserIdAndThemeId(user.getId(), theme.getId());
	}

	public void unSubscribeUserToTheme(User user, Theme theme) {
	    if (isUserAlreadySubscribed(user, theme)) {
	        userThemeRepository.deleteByUserIdAndThemeId(user.getId(), theme.getId());
	    }
	}
}
