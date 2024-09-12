package com.chyoon.jwt.service;

import com.chyoon.jwt.entity.Users;
import com.chyoon.jwt.repository.UserJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final UserJPARepository userJPARepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        return (UserDetails) userJPARepository.findByUserId(userId).orElse(null);
    }

    public Users getUserInfo(String userId) {
        return userJPARepository.findByUserId(userId).orElse(null);
    }
}
