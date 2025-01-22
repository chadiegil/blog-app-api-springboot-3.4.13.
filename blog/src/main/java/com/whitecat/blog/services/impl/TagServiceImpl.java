package com.whitecat.blog.services.impl;

import com.whitecat.blog.domain.entities.Tag;
import com.whitecat.blog.repositories.TagRepository;
import com.whitecat.blog.services.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    @Override
    public List<Tag> getTags() {
        return tagRepository.findAllWithPostCount();
    }
}
