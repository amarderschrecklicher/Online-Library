package ba.unsa.etf.forum_service.forum_service.mappers;

import ba.unsa.etf.forum_service.forum_service.dtos.ForumCommentDto;
import ba.unsa.etf.forum_service.forum_service.models.ForumComment;

public class ForumCommentMapper {
    public static ForumCommentDto toDto(ForumComment comment) {
        ForumCommentDto dto = new ForumCommentDto();
        dto.setId(comment.getId());
        dto.setForumPostId(comment.getForumPost().getId());
        dto.setMemberId(comment.getMemberId());
        dto.setContent(comment.getContent());
        dto.setCreatedAt(comment.getCreatedAt());
        return dto;
    }

    public static ForumComment toEntity(ForumCommentDto dto) {
        ForumComment comment = new ForumComment();
        comment.setMemberId(dto.getMemberId());
        comment.setContent(dto.getContent());
        // forumPost se setuje posebno u servisu
        return comment;
    }

}
