package com.whitecat.blog.services;

import com.whitecat.blog.domain.entities.User;

import java.util.UUID;

public interface UserService {
    User getUserById(UUID id);
}
