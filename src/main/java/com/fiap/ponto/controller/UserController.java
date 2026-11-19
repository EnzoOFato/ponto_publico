package com.fiap.ponto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fiap.ponto.service.UserService;
import com.fiap.ponto.dto.input.UserInDto;
import com.fiap.ponto.dto.output.UserOutDto;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/getall")
    public List<UserOutDto> getAll() {
        return userService.getAll();
    }

    @GetMapping("/get/{userId)")
    public UserOutDto getById(@PathVariable Long userId) {
        return userService.getById(userId);
    }

    @PostMapping ("/create")
    public ResponseEntity<String> createUser(@RequestBody UserInDto userIn) {
        return userService.create(userIn);
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<String> updateuser(@RequestBody UserInDto userIn, @PathVariable Long userId) {
        return userService.update(userId, userIn);
    }

    @PutMapping("/update/{userId}/lesson/{lessonId}")
    public ResponseEntity<String> inscribeInLesson(@PathVariable Long userId, @PathVariable Long lessonId) {
        return userService.inscribeInLesson(userId, lessonId);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        return userService.delete(userId);
    }

}
