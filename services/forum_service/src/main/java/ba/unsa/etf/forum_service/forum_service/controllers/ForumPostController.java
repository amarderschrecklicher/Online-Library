package ba.unsa.etf.forum_service.forum_service.controllers;

import ba.unsa.etf.forum_service.forum_service.dtos.ForumPostDto;
import ba.unsa.etf.forum_service.forum_service.mappers.ForumPostMapper;
import ba.unsa.etf.forum_service.forum_service.models.ForumPost;
import ba.unsa.etf.forum_service.forum_service.services.ForumPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/forum/posts")
@CrossOrigin
public class ForumPostController {
    @Autowired
    private ForumPostService forumPostService;

    @GetMapping
    public List<ForumPostDto> getAllPosts() {
        return forumPostService.getAllPosts().stream()
                .map(ForumPostMapper::toDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ForumPost createPost(@RequestBody ForumPost post) {
        return forumPostService.createPost(post);
    }
}
