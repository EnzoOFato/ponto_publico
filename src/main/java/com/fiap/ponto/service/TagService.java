package com.fiap.ponto.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.Set;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fiap.ponto.dto.input.TagInDto;
import com.fiap.ponto.dto.output.TagOutDto;
import com.fiap.ponto.exception.PontoException;
import com.fiap.ponto.repository.TagRepository;

import com.fiap.ponto.model.Tag;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    protected Set<Tag> resolveTags(Set<Long> tagIds) {
        if (tagIds == null || tagIds.isEmpty()) return new HashSet<>();

        List<Tag> found = tagRepository.findAllById(tagIds);

        if (found.size() != tagIds.size()) {
            Set<Long> foundIds = found.stream().map(Tag::getId).collect(Collectors.toSet());
            Set<Long> missing = new HashSet<>(tagIds);
            missing.removeAll(foundIds);
            throw new PontoException("Tags não encontradas: " + missing);
        }

    return new HashSet<>(found);
    }

    public TagOutDto tagToDto (Tag tag) {
        return new TagOutDto(tag.getId(), tag.getTag());
    }

    @Transactional
    public ResponseEntity<String> create(TagInDto tagInDto) {
        try {
            String content = tagInDto.content();

            if (content == null || content.isEmpty()) throw new PontoException("Tag content is empty");

            Tag tag = new Tag();
            tag.setTag(tagInDto.content());

            tagRepository.save(tag);

            return ResponseEntity.ok("New #tag: " + content);
        }
        catch (PontoException p) {
            return ResponseEntity.badRequest().body("Tag with no content");
        }
    }

    public Tag getTagById(Long id) {
        return tagRepository.findById(id).orElseThrow(() -> new PontoException("Tag could not be found"));
    }

    public Set<Tag> getAllByIds(Set<Long> ids) {
        Set<Tag> tagResult = new HashSet<>();
        for (Long id : ids) {
            tagResult.add(getTagById(id));
        }
        return tagResult;
    }

    public Set<TagOutDto> getAll() {
        return tagRepository.findAll().stream().map(t -> tagToDto(t)).collect(Collectors.toSet());
    }
}
