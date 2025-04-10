package ba.unsa.etf.forum_service.forum_service;

import ba.unsa.etf.forum_service.forum_service.dtos.ForumCommentDto;
import ba.unsa.etf.forum_service.forum_service.models.ForumComment;
import ba.unsa.etf.forum_service.forum_service.models.ForumPost;
import ba.unsa.etf.forum_service.forum_service.repositories.ForumCommentRepository;
import ba.unsa.etf.forum_service.forum_service.repositories.ForumPostRepository;
import ba.unsa.etf.forum_service.forum_service.services.ForumCommentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ForumCommentServiceTest {

    @Mock
    private ForumCommentRepository forumCommentRepository;

    @Mock
    private ForumPostRepository forumPostRepository;

    @InjectMocks
    private ForumCommentService forumCommentService;

    @Test
    void shouldReturnCommentsByPostId() {
        ForumComment comment = new ForumComment(1L, 101L, "Komentar", LocalDateTime.now(), null);

        when(forumCommentRepository.findByForumPostId(1L)).thenReturn(List.of(comment));

        List<ForumComment> result = forumCommentService.getCommentsByPostId(1L);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getContent()).isEqualTo("Komentar");
    }

    @Test
    void shouldAddCommentToPost() {
        ForumPost post = new ForumPost(1L, 100L, "Naslov", "Sadržaj", LocalDateTime.now(), new ArrayList<>());

        ForumCommentDto dto = new ForumCommentDto();
        dto.setMemberId(105L);
        dto.setContent("Novi komentar");

        when(forumPostRepository.findById(1L)).thenReturn(Optional.of(post));

        ArgumentCaptor<ForumComment> captor = ArgumentCaptor.forClass(ForumComment.class);
        when(forumCommentRepository.save(any(ForumComment.class))).thenAnswer(i -> {
            ForumComment c = i.getArgument(0);
            c.setId(10L);
            return c;
        });

        ForumComment saved = forumCommentService.addComment(1L, dto);

        assertThat(saved.getId()).isEqualTo(10L);
        assertThat(saved.getContent()).isEqualTo("Novi komentar");
        assertThat(saved.getForumPost().getId()).isEqualTo(1L);
        verify(forumCommentRepository).save(captor.capture());
    }
}
