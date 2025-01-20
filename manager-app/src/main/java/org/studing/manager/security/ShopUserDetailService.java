package org.studing.manager.security;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.studing.manager.entity.Authority;
import org.studing.manager.repository.ShopUserRepository;

import static org.springframework.security.core.userdetails.User.builder;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public class ShopUserDetailService implements UserDetailsService {
    ShopUserRepository shopUserRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return shopUserRepository.findByUsername(username)
            .map(user -> builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getAuthorities().stream()
                    .map(Authority::getAuthority)
                    .map(SimpleGrantedAuthority::new)
                    .toList())
                .build())
            .orElseThrow(() -> new UsernameNotFoundException("User %s not found".formatted(username)));
    }
}
