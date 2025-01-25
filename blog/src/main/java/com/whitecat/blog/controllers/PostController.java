package com.whitecat.blog.controllers;


import com.whitecat.blog.domain.CreatePostRequest;
import com.whitecat.blog.domain.UpdatePostRequest;
import com.whitecat.blog.domain.dtos.CreatePostRequestDto;
import com.whitecat.blog.domain.dtos.PostDto;
import com.whitecat.blog.domain.dtos.UpdatePostRequestDto;
import com.whitecat.blog.domain.entities.Post;
import com.whitecat.blog.domain.entities.User;
import com.whitecat.blog.mappers.PostMapper;
import com.whitecat.blog.services.PostService;
import com.whitecat.blog.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostMapper postMapper;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts(
            @RequestParam(required = false)UUID categoryId,
            @RequestParam(required = false)UUID tagId
            ){
            List<Post> posts = postService.getAllPosts(categoryId,tagId);
           List<PostDto> postDtos =  posts.stream().map(postMapper::toDto).toList();
           return ResponseEntity.ok(postDtos);
    }

    @GetMapping(path = "/drafts")
    public ResponseEntity<List<PostDto>> getDrafts(@RequestAttribute UUID userId){
        User loggesInUser = userService.getUserById(userId);
        List<Post> draftPosts = postService.getDraftPosts(loggesInUser);
        List<PostDto> postDtos = draftPosts.stream().map(postMapper::toDto).toList();
        return ResponseEntity.ok(postDtos);
    }

    @PostMapping
    public ResponseEntity<PostDto> createPost(
            @RequestAttribute UUID userId,
            @Valid @RequestBody CreatePostRequestDto createPostRequestDto
    ){
        System.out.println("Request body:" +createPostRequestDto);
        User loggedInUser = userService.getUserById(userId);
        CreatePostRequest createPostRequest = postMapper.toCreatePostRequest(createPostRequestDto);
        Post createdPost = postService.createPost(loggedInUser,createPostRequest);
        PostDto createPostDto = postMapper.toDto(createdPost);
        return new ResponseEntity<>(createPostDto,HttpStatus.CREATED);
    }
    @PutMapping(path = "/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable UUID id,@Valid @RequestBody UpdatePostRequestDto updatePostRequestDto){
        UpdatePostRequest updatePostRequest = postMapper.toUpdatePostRequest(updatePostRequestDto);
        Post updatedPost = postService.updatePost(id,updatePostRequest);
        PostDto updatePostDto = postMapper.toDto(updatedPost);
        return ResponseEntity.ok(updatePostDto);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<PostDto> getPost(@PathVariable UUID id){
        Post post = postService.getPost(id);
        PostDto postDto = postMapper.toDto(post);
        return ResponseEntity.ok(postDto);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable UUID id){
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}
