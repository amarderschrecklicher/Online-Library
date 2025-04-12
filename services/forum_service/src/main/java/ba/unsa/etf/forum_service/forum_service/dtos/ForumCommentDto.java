package ba.unsa.etf.forum_service.forum_service.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ForumCommentDto {
    private Long id;

    @NotNull(message = "Forum post ID is required")
    private Long forumPostId;

    @NotNull(message = "Member ID is required")
    private Long memberId;

    @NotBlank(message = "Content is required")
    private String content;

    private LocalDateTime createdAt;
}
