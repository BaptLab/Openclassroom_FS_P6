package com.openclassrooms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.models.Theme;

@Repository
public interface ThemeRepository extends JpaRepository<Theme, Long>{

}
