package ba.unsa.etf.forum_service.forum_service.repositories;

import ba.unsa.etf.forum_service.forum_service.models.ForumPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForumPostRepository extends JpaRepository<ForumPost, Long> {}