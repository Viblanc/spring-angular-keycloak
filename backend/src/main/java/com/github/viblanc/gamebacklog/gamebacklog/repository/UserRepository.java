package com.github.viblanc.gamebacklog.gamebacklog.repository;

import com.github.viblanc.gamebacklog.gamebacklog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {}
