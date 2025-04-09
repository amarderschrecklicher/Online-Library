package ba.unsa.etf.forum_service.forum_service.repositories;

import ba.unsa.etf.forum_service.forum_service.models.ForumComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ForumCommentRepository extends JpaRepository<ForumComment, Long> {
    List<ForumComment> findByForumPostId(Long postId);
}
