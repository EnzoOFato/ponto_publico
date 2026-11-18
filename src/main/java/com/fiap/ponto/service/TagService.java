package com.fiap.ponto.service;

import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.Set;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return new TagOutDto(tag.getTag());
    }
}
