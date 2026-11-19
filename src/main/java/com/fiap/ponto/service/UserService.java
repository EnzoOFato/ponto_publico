package com.fiap.ponto.service;

import java.lang.classfile.Attribute;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fiap.ponto.dto.input.UserInDto;
import com.fiap.ponto.dto.output.UserOutDto;
import com.fiap.ponto.exception.PontoException;
import com.fiap.ponto.model.User;
import com.fiap.ponto.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LessonService lessonService;

    public List<UserOutDto> getAll() {
        return userRepository.findAll().stream()
        .map(u -> userToDto(u))
        .collect(Collectors.toList());
    }

    @Transactional
    public ResponseEntity<String> create(UserInDto userIn) {
        try {
            validation(userIn);

            User user = dtoToUser(userIn);

            userRepository.save(user);

            return ResponseEntity.ok("User created!");
        }
        catch (PontoException p) {
            return ResponseEntity.badRequest().body(p.getMessage()); 
        }
    }

    @Transactional
    public ResponseEntity<String> update(Long userId, UserInDto userIn) {
        try {
            User user = getUserById(userId);

            String name = userIn.name();
            String password = userIn.password();

            if (password.length() <= 5 ) throw new PontoException("User Password must have at least 6 characters");

            if (Boolean.TRUE.equals(userIn.changePassword())) {
                stringValidation(password, "Password can't be null");
                user.setPassword(password);
                return ResponseEntity.ok("User password changed");
            }
            else {
                stringValidation(name, "Name can't be null");
                user.setNickName(name);
                return ResponseEntity.ok("User nickname changed");
            }
        }
        catch (PontoException p) {
            return ResponseEntity.badRequest().body(p.getMessage());
        }
    }

    @Transactional
    public ResponseEntity<String> delete(Long userId) {
        try {
            getUserById(userId);

            userRepository.deleteById(userId);

            return ResponseEntity.ok("User deleted!");
        }
        catch (PontoException p) {
            return ResponseEntity.badRequest().body(p.getMessage());
        }
    }

    private User getUserById(Long id) {
        return userRepository.findById(id)
        .orElseThrow(() -> new PontoException("User (%d) could not be found".formatted(id)));
    }

    public void stringValidation(String txt, String message) {
        if (txt == null || txt.isEmpty()) throw new PontoException(message);
    }

    private void validation(UserInDto userInDto) {
        String name = userInDto.name();
        String password = userInDto.password();

        stringValidation(name, "Name can't be null");

        stringValidation(password, "Password can't be null");

        if (password.length() <= 5) throw new PontoException("User Password must have at least 6 characters");
    }

    private User dtoToUser(UserInDto userIn) {
        User user = new User();
        user.setNickName(userIn.name());
        user.setPassword(userIn.password());
        user.setLastLogin(null);
        user.setLessons(new HashSet<>());
        return user;
    }

    private UserOutDto userToDto(User user) {
        return new UserOutDto(
            user.getId(), 
            user.getNickName(), 
            lessonService.getIds(user.getLessons()), 
            user.getLastLogin()
        );
    }
}
