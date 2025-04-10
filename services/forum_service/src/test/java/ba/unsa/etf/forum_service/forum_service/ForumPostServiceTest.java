package ba.unsa.etf.forum_service.forum_service;

import ba.unsa.etf.forum_service.forum_service.dtos.ForumPostDto;
import ba.unsa.etf.forum_service.forum_service.mappers.ForumPostMapper;
import ba.unsa.etf.forum_service.forum_service.models.ForumPost;
import ba.unsa.etf.forum_service.forum_service.repositories.ForumPostRepository;
import ba.unsa.etf.forum_service.forum_service.services.ForumPostService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ForumPostServiceTest {

    @Mock
    private ForumPostRepository forumPostRepository;

    @InjectMocks
    private ForumPostService forumPostService;

    @Test
    void shouldReturnAllPosts() {
        List<ForumPost> posts = List.of(
                new ForumPost(1L, 101L, "Naslov 1", "Sadržaj 1", LocalDateTime.now(), new ArrayList<>()),
                new ForumPost(2L, 102L, "Naslov 2", "Sadržaj 2", LocalDateTime.now(), new ArrayList<>())
        );

        when(forumPostRepository.findAll()).thenReturn(posts);

        List<ForumPost> result = forumPostService.getAllPosts();

        assertThat(result).hasSize(2);
        verify(forumPostRepository, times(1)).findAll();
    }

    @Test
    void shouldCreateNewPost() {
        ForumPostDto dto = new ForumPostDto();
        dto.setMemberId(101L);
        dto.setTitle("Test naslov");
        dto.setContent("Test sadržaj");

        ForumPost post = ForumPostMapper.toEntity(dto);
        post.setId(1L);
        post.setCreatedAt(LocalDateTime.now());

        when(forumPostRepository.save(any(ForumPost.class))).thenReturn(post);

        ForumPost created = forumPostService.createPost(dto);

        assertThat(created.getId()).isEqualTo(1L);
        assertThat(created.getTitle()).isEqualTo("Test naslov");
        verify(forumPostRepository, times(1)).save(any(ForumPost.class));
    }
}
