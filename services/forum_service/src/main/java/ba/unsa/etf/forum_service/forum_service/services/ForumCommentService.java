package ba.unsa.etf.forum_service.forum_service.services;

import ba.unsa.etf.forum_service.forum_service.models.ForumComment;
import ba.unsa.etf.forum_service.forum_service.models.ForumPost;
import ba.unsa.etf.forum_service.forum_service.repositories.ForumCommentRepository;
import ba.unsa.etf.forum_service.forum_service.repositories.ForumPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ForumCommentService {
    @Autowired
    private ForumCommentRepository forumCommentRepository;

    @Autowired
    private ForumPostRepository forumPostRepository;

    public ForumComment addComment(Long postId, ForumComment comment) {
        ForumPost post = forumPostRepository.findById(postId).orElseThrow();
        comment.setForumPost(post);
        comment.setCreatedAt(LocalDateTime.now());
        return forumCommentRepository.save(comment);
    }

    public List<ForumComment> getCommentsByPostId(Long postId) {
        return forumCommentRepository.findByForumPostId(postId);
    }
}
