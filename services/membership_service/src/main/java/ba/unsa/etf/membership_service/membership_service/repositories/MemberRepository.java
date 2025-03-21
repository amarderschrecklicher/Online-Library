package ba.unsa.etf.membership_service.membership_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ba.unsa.etf.membership_service.membership_service.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

   
}
