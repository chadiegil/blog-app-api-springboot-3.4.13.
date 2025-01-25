package com.whitecat.blog.mappers;

import com.whitecat.blog.domain.CreatePostRequest;
import com.whitecat.blog.domain.UpdatePostRequest;
import com.whitecat.blog.domain.dtos.CreatePostRequestDto;
import com.whitecat.blog.domain.dtos.PostDto;
import com.whitecat.blog.domain.dtos.UpdatePostRequestDto;
import com.whitecat.blog.domain.entities.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper {
    @Mapping(target = "author", source = "author")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "tags", source = "tags")
    @Mapping(target = "status", source = "status")
    PostDto toDto(Post post);
    CreatePostRequest toCreatePostRequest(CreatePostRequestDto dto);
    UpdatePostRequest toUpdatePostRequest(UpdatePostRequestDto dto);
}
