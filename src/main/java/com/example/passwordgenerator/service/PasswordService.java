package com.example.passwordgenerator.service;

import com.example.passwordgenerator.entity.Password;
import com.example.passwordgenerator.repository.PasswordRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

@Service
public class PasswordService {

    private static final String NUMBERS = "0123456789";
    private static final String LETTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String SYMBOLS = "!@#$%^&*()_-+=<>?/{}[]|";
    private final PasswordRepository passwordRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public PasswordService(PasswordRepository passwordRepository) {
        this.passwordRepository = passwordRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public String generatePassword(int length, int complexity) {
        String characters = NUMBERS;
        if (complexity >= 2) {
            characters += LETTERS;
        }
        if (complexity >= 3) {
            characters += SYMBOLS;
        }

        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            password.append(characters.charAt(index));
        }
        return password.toString();
    }

    public Password create(Password password) {
        String plainPassword = password.getPassword();
        String hashedPassword = passwordEncoder.encode(plainPassword);
        password.setPassword(hashedPassword);
        return passwordRepository.save(password);
    }

    public List<Password> findAll() {
        return passwordRepository.findAll();
    }

    public Optional<Password> findById(Long id) {
        return passwordRepository.findById(id);
    }

    public Password update(Password password) {
        String plainPassword = password.getPassword();
        String hashedPassword = passwordEncoder.encode(plainPassword);
        password.setPassword(hashedPassword);
        return passwordRepository.save(password);
    }

    public void delete(Long id) {
        passwordRepository.deleteById(id);
    }
}