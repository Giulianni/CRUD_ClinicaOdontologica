package com.dh.clinica.service;

import com.dh.clinica.persistence.entities.User;
import com.dh.clinica.persistence.entities.UserRole;
import com.dh.clinica.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    private UserRepository userRepository;

    public void run(ApplicationArguments args) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode("admin");
        BCryptPasswordEncoder passwordEncoder2 = new BCryptPasswordEncoder();
        String hashedPassword2 = passwordEncoder2.encode("user");
        userRepository.save(new User("Laura", "lau", "lau@gmail.com", hashedPassword, UserRole.ADMIN));
        userRepository.save(new User("Paula", "paula", "paula@digital.com", hashedPassword2, UserRole.USER));
    }
}
