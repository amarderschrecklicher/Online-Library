package ba.unsa.etf.membership_service.membership_service.controllers;

import ba.unsa.etf.membership_service.membership_service.dtos.MemberDto;
import ba.unsa.etf.membership_service.membership_service.models.Member;
import ba.unsa.etf.membership_service.membership_service.repositories.MemberRepository;
import ba.unsa.etf.membership_service.membership_service.services.MemberService;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/member")
@CrossOrigin
public class MemberController {

    @Autowired
    private MemberService memberService;

    @GetMapping
    public List<Member> getAllMembers() {
        return memberService.getAllMembers();
    }

    @PostMapping
    public ResponseEntity<?> createMember(@RequestBody MemberDto memberDto) {

        if (memberService.existsByEmail(memberDto.getEmail())) {
            return ResponseEntity.badRequest().body("Member already exists");
        }

        Member newMember = memberService.createMember(
                memberDto.getUsername(),
                memberDto.getPassword(),
                memberDto.getEmail(),
                memberDto.getCreatedAt(),
                memberDto.getStatus()
        );

        return ResponseEntity.ok().body(newMember);
    }

    @PutMapping(path = "/{memberId}")
    public ResponseEntity<?> updateMember(@PathVariable("memberId") Long memberId, @RequestBody MemberDto memberDto) {
        if(memberService.existsByEmail(memberDto.getEmail())) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Member already exists");
            return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST).body(error);
        }
        try{
            Member updatedMember =memberService.updateMember(memberId, memberDto);

            if(updatedMember == null){
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(updatedMember);
        }catch (Exception e){
            Map<String, String> error = new HashMap<>();
            error.put("error", "Internal server error");
            return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMember(@PathVariable Long id) {
        System.out.println("Delete called!");
        try {
            memberService.deleteMember(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Member deleted successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to delete member.");
            return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).body(error);
        }
    }
}
