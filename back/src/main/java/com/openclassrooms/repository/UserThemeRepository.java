package com.openclassrooms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.models.UserTheme;

@Repository
public interface UserThemeRepository extends JpaRepository<UserTheme, Long>{
    boolean existsByUserIdAndThemeId(Long userId, Long themeId);
    void deleteByUserIdAndThemeId(Long userId, Long themeId);
}
