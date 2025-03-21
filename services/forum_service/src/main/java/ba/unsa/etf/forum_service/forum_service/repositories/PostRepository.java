package ba.unsa.etf.forum_service.forum_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ba.unsa.etf.forum_service.forum_service.ForumPost;

@Repository
public interface PostRepository extends JpaRepository<ForumPost, Long> {

   
}
