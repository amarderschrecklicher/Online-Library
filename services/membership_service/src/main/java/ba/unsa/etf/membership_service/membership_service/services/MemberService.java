package ba.unsa.etf.membership_service.membership_service.services;

import ba.unsa.etf.membership_service.membership_service.dtos.MemberDto;
import ba.unsa.etf.membership_service.membership_service.models.Member;
import ba.unsa.etf.membership_service.membership_service.models.Reservation;
import ba.unsa.etf.membership_service.membership_service.repositories.MemberRepository;
import jakarta.transaction.Transactional;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public List<Member> getAllMembers() {return memberRepository.findAll();}

    public boolean existsByEmail(String email) {
        return memberRepository.existsByEmail(email);
    }
    public Member getMemberById(Long id) {return memberRepository.getById(id);}

    public Optional<Member> getMemberByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    public Member addNewMember(Member member) {
        memberRepository.save(member);
        return member;
    }

    public Member createMember(String username, String email, String password, LocalDateTime createdAt, String status) {
        Member member = Member.builder().username(username).
                email(email)
                .password(password)
                .status(status)
                .build();
        Member savedMember = memberRepository.save(member);
        return savedMember;
    }

    public Member updateMember(Long id, MemberDto updateMember) {
        Member existingMember = memberRepository.getById(id);

        existingMember.setUsername(updateMember.getUsername());
        existingMember.setEmail(updateMember.getEmail());
        return memberRepository.save(existingMember);

    }

    public void deleteMember(Long id) {
        memberRepository.deleteById(id);
    }

    public Optional<Member> findByUsername(String memberName) {
        return memberRepository.findByUsername(memberName);
    }

    public boolean existsById(Long memberId) {
        return memberRepository.existsById(memberId);
    }

    public Member updateMemberRole(Long memberId, MemberDto memberDto) {
        Member existingMember = memberRepository.getById( memberId);
        existingMember.setStatus(memberDto.getStatus());
        return memberRepository.save(existingMember);}
}

