package com.chyoon.jwt.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@RequiredArgsConstructor
public class LoginDTO {

    @NotBlank(message = "userId is required parameter!")
    private String userId;
    @NotBlank(message = "userPw is required parameter!")
    private String userPw;
}
