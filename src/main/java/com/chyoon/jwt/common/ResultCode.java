package com.chyoon.jwt.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResultCode {

    // 200 번대 성공
    SUCCESS(2000, "SUCCESS"),

    // 300 클라이언트 에러
    INVALID_PARAMETER(3000, "Invalid Parameter"),
    NOT_FOUND_URI(3001, "Not Found"),

    // 400 권한 에러
    UNAUTHORIZED(4000, "Unauthorized"),
    INVALID_TOKEN(4001, "Invalid Token"),
    EXPIRED_TOKEN(4002, "Expired Token"),
    NOT_USER(4003, "Not User"),

    // 600 서버 에러
    SERVER_ERROR(6000, "Server Error");

    private final int code;
    private final String description;
}
