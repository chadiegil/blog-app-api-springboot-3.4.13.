package com.whitecat.blog.repositories;

import com.whitecat.blog.domain.PostStatus;
import com.whitecat.blog.domain.entities.Category;
import com.whitecat.blog.domain.entities.Post;
import com.whitecat.blog.domain.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {
    List<Post> findAllByStatusAndCategoryAndTagsContaining(PostStatus status, Category category, Tag tag);
    List<Post> findAllByStatusAndCategory(PostStatus status,Category category);
    List<Post> findAllByStatusAndTagsContaining(PostStatus postStatus,Tag tag);
    List<Post> findAllByStatus(PostStatus postStatus);
}
