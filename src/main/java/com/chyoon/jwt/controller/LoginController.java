package com.chyoon.jwt.controller;

import com.chyoon.jwt.common.ResponseBody;
import com.chyoon.jwt.common.ResultCode;
import com.chyoon.jwt.dto.LoginDTO;
import com.chyoon.jwt.dto.UserInfoDTO;
import com.chyoon.jwt.service.LoginService;
import com.chyoon.jwt.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity login(@Valid  @RequestBody LoginDTO loginRequestDTO) {

        UserInfoDTO userInfo = loginService.login(loginRequestDTO);

        return new ResponseEntity<>(ResponseBody.builder()
                .code(ResultCode.SUCCESS)
                .result(userInfo)
                .build(), HttpStatus.OK);
    }
}
