package ba.unsa.etf.forum_service.forum_service.services;

import ba.unsa.etf.forum_service.forum_service.dtos.ForumPostDto;
import ba.unsa.etf.forum_service.forum_service.mappers.ForumPostMapper;
import ba.unsa.etf.forum_service.forum_service.models.ForumPost;
import ba.unsa.etf.forum_service.forum_service.repositories.ForumPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ForumPostService {
    @Autowired
    private ForumPostRepository forumPostRepository;

    public List<ForumPost> getAllPosts() {
        return forumPostRepository.findAll();
    }

    public ForumPost createPost(ForumPostDto postDto) {
        ForumPost post = ForumPostMapper.toEntity(postDto);
        post.setCreatedAt(LocalDateTime.now());
        return forumPostRepository.save(post);
    }

}
