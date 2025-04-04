package ba.unsa.etf.forum_service.forum_service.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ForumCommentDto {
    private Long id;
    private Long forumPostId;
    private Long memberId;
    private String content;
    private LocalDateTime createdAt;
}
