package com.openclassrooms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

}
