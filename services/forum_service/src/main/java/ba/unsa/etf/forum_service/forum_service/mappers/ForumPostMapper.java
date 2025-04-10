package ba.unsa.etf.forum_service.forum_service.mappers;

import ba.unsa.etf.forum_service.forum_service.dtos.ForumPostDto;
import ba.unsa.etf.forum_service.forum_service.models.ForumPost;

public class ForumPostMapper {
    public static ForumPostDto toDto(ForumPost post) {
        ForumPostDto dto = new ForumPostDto();
        dto.setId(post.getId());
        dto.setMemberId(post.getMemberId());
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        dto.setCreatedAt(post.getCreatedAt());
        return dto;
    }

    public static ForumPost toEntity(ForumPostDto dto) {
        ForumPost post = new ForumPost();
        post.setMemberId(dto.getMemberId());
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        // createdAt se postavlja u servisu prilikom snimanja
        return post;
    }
}
