package ba.unsa.etf.membership_service.membership_service.mappers;

import ba.unsa.etf.membership_service.membership_service.dtos.MemberDto;
import ba.unsa.etf.membership_service.membership_service.models.Member;

public class MemberMapper {
    public static MemberDto toDto(Member member) {
        MemberDto dto = new MemberDto();
        dto.setId(member.getId());
        dto.setUsername(member.getUsername());
        dto.setEmail(member.getEmail());
        dto.setStatus(member.getStatus());
        return dto;
    }
}
