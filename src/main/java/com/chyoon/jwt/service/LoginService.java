package com.chyoon.jwt.service;

import com.chyoon.jwt.common.exception.CustomErrorException;
import com.chyoon.jwt.common.ResultCode;
import com.chyoon.jwt.common.util.CryptoUtil;
import com.chyoon.jwt.common.config.JwtProvider;
import com.chyoon.jwt.dto.LoginDTO;
import com.chyoon.jwt.dto.UserInfoDTO;
import com.chyoon.jwt.entity.Users;
import com.chyoon.jwt.repository.UserJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class LoginService {

    private final UserJPARepository userJPARepository;
    private final JwtProvider jwtProvider;

    @Transactional
    public UserInfoDTO login(LoginDTO loginDTO) {
        Users user = userJPARepository.findByUserId(loginDTO.getUserId()).orElse(null);
        if (user == null || !CryptoUtil.encryptWithSHA512(loginDTO.getUserPw()).equals(user.getUserPw())) {
            throw new CustomErrorException(ResultCode.NOT_USER);
        }

        String accessToken = jwtProvider.createAccessToken(user.getUserId());

        return UserInfoDTO.builder()
                .userId(user.getUserId())
                .accessToken(accessToken)
                .build();
    }

}
