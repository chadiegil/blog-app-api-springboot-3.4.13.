package com.whitecat.blog.services.impl;

import com.whitecat.blog.domain.entities.Tag;
import com.whitecat.blog.repositories.TagRepository;
import com.whitecat.blog.services.TagService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    @Override
    public List<Tag> getTags() {
        return tagRepository.findAllWithPostCount();
    }

    @Transactional
    @Override
    public List<Tag> createTags(Set<String> tagNames) {
        List<Tag> existingTag = tagRepository.findByNameIn(tagNames);
        Set<String> existingTagNames = existingTag.stream().map(Tag::getName).collect(Collectors.toSet());

        List<Tag> newTags = tagNames.stream().filter(name-> !existingTagNames.contains(name))
                .map(name->Tag.builder().name(name).posts(new HashSet<>())
                        .build()
                ).toList();
        List<Tag> saveTags = new ArrayList<>();
        if(!newTags.isEmpty()){
            saveTags =  tagRepository.saveAll(newTags);
        }
        saveTags.addAll(existingTag);
        return saveTags;
    }

    @Transactional
    @Override
    public void deleteTag(UUID id) {
        tagRepository.findById(id).ifPresent(tag -> {
            if(!tag.getPosts().isEmpty()){
                throw new IllegalArgumentException("Cannot delete tag with post");
            }
            tagRepository.deleteById(id);
        });
    }

    @Override
    public Tag getTagById(UUID id) {
        return tagRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Tag not found with id"+id));
    }
}
