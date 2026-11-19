package com.fiap.ponto.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fiap.ponto.dto.input.TagInDto;
import com.fiap.ponto.dto.output.TagOutDto;
import com.fiap.ponto.service.TagService;

@RestController
@RequestMapping("/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    @PostMapping("/create") 
    public ResponseEntity<String> create(@RequestBody TagInDto tagInDto) {
        return tagService.create(tagInDto);
    }

    @GetMapping("/getall")
    public Set<TagOutDto> getAll() {
        return tagService.getAll();
    }
}
