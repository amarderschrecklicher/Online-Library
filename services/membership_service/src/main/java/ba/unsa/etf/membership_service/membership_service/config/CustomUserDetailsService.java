package ba.unsa.etf.membership_service.membership_service.config;

import ba.unsa.etf.membership_service.membership_service.repositories.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberRepository.findByUsername(username)
                .map(member -> User.withUsername(member.getUsername())
                        .password(member.getPassword()) // već je hashed
                        .roles("USER") // možeš prilagoditi ako ima više uloga
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}