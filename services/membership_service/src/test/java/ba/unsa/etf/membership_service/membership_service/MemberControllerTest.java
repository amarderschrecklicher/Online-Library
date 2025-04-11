package ba.unsa.etf.membership_service.membership_service;

import ba.unsa.etf.membership_service.membership_service.controllers.MemberController;
import ba.unsa.etf.membership_service.membership_service.dtos.MemberDto;
import ba.unsa.etf.membership_service.membership_service.models.Member;
import ba.unsa.etf.membership_service.membership_service.repositories.MemberRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MemberControllerTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberController memberController;

    @Test
    void shouldReturnAllMembers() {
        Member member = new Member(1L, "john", "john@example.com", "pass", LocalDateTime.now(), "ACTIVE");
        when(memberRepository.findAll()).thenReturn(List.of(member));

        List<Member> members = memberController.getAllMembers();

        assertThat(members).hasSize(1);
        assertThat(members.get(0).getEmail()).isEqualTo("john@example.com");
    }

    @Test
    void shouldCreateNewMember() {
        MemberDto dto = new MemberDto("john", "john@example.com", "ACTIVE");
        Member saved = new Member(1L, "john", "john@example.com", "pass", LocalDateTime.now(), "ACTIVE");

        when(memberRepository.existsByEmail(dto.getEmail())).thenReturn(false);
        when(memberRepository.save(any(Member.class))).thenReturn(saved);

        var response = memberController.createMember(dto);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        verify(memberRepository).save(any(Member.class));
    }

    @Test
    void shouldRejectDuplicateMember() {
        MemberDto dto = new MemberDto("john", "john@example.com", "ACTIVE");

        when(memberRepository.existsByEmail(dto.getEmail())).thenReturn(true);

        var response = memberController.createMember(dto);

        assertThat(response.getStatusCodeValue()).isEqualTo(400);
        assertThat(response.getBody()).isEqualTo("Member already exists");
        verify(memberRepository, never()).save(any());
    }

    @Test
    void shouldDeleteMember() {
        doNothing().when(memberRepository).deleteById(1L);

        memberController.deleteMember(1L);

        verify(memberRepository, times(1)).deleteById(1L);
    }
}
