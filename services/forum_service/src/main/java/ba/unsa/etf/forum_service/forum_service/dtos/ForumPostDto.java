package ba.unsa.etf.forum_service.forum_service.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ForumPostDto {
    private Long id;
    private Long memberId;
    private String title;
    private String content;
    private LocalDateTime createdAt;
}