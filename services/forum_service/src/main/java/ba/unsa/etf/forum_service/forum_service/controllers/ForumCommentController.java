package ba.unsa.etf.forum_service.forum_service.controllers;

import ba.unsa.etf.forum_service.forum_service.dtos.ForumCommentDto;
import ba.unsa.etf.forum_service.forum_service.mappers.ForumCommentMapper;
import ba.unsa.etf.forum_service.forum_service.models.ForumComment;
import ba.unsa.etf.forum_service.forum_service.services.ForumCommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> addComment(@PathVariable Long postId, @Valid @RequestBody ForumCommentDto commentDto) {
        try {
            ForumComment saved = forumCommentService.addComment(postId, commentDto);
            return ResponseEntity.ok(ForumCommentMapper.toDto(saved));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error saving comment");
        }
    }

    @GetMapping("/post/{postId}")
    public List<ForumCommentDto> getComments(@PathVariable Long postId) {
        return forumCommentService.getCommentsByPostId(postId).stream()
                .map(ForumCommentMapper::toDto)
                .collect(Collectors.toList());
    }
}

