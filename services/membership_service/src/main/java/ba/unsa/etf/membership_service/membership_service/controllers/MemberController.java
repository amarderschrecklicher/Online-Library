package ba.unsa.etf.membership_service.membership_service.controllers;

import ba.unsa.etf.membership_service.membership_service.dtos.MemberDto;
import ba.unsa.etf.membership_service.membership_service.models.Member;
import ba.unsa.etf.membership_service.membership_service.repositories.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/member")
@CrossOrigin
public class MemberController {

    @Autowired
    private MemberRepository memberRepository;

    @GetMapping
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<?> createMember(@RequestBody MemberDto memberDto) {
        if (memberRepository.existsByEmail(memberDto.getEmail())) {
            return ResponseEntity.badRequest().body("Member already exists");
        }

        Member member = new Member();
        member.setUsername(memberDto.getUsername());
        member.setEmail(memberDto.getEmail());
        member.setStatus(memberDto.getStatus());
        member.setCreatedAt(LocalDateTime.now());

        return ResponseEntity.ok(memberRepository.save(member));
    }

    @DeleteMapping("/{id}")
    public void deleteMember(@PathVariable Long id) {
        memberRepository.deleteById(id);
    }
}
