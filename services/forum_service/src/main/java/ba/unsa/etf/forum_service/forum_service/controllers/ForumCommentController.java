package ba.unsa.etf.forum_service.forum_service.controllers;

import ba.unsa.etf.forum_service.forum_service.dtos.ForumCommentDto;
import ba.unsa.etf.forum_service.forum_service.mappers.ForumCommentMapper;
import ba.unsa.etf.forum_service.forum_service.models.ForumComment;
import ba.unsa.etf.forum_service.forum_service.services.ForumCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/forum/comments")
@CrossOrigin
public class ForumCommentController {
    @Autowired
    private ForumCommentService forumCommentService;

    @PostMapping("/{postId}")
    public ForumComment addComment(@PathVariable Long postId, @RequestBody ForumComment comment) {
        return forumCommentService.addComment(postId, comment);
    }

    @GetMapping("/post/{postId}")
    public List<ForumCommentDto> getComments(@PathVariable Long postId) {
        return forumCommentService.getCommentsByPostId(postId).stream()
                .map(ForumCommentMapper::toDto)
                .collect(Collectors.toList());
    }
}
