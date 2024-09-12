package com.chyoon.jwt.common.exception;

import com.chyoon.jwt.common.ResultCode;
import lombok.Getter;

@Getter
public class CustomErrorException extends RuntimeException {
    private ResultCode resultCode;

    public CustomErrorException(String message) {
        super(message);
    }
    public CustomErrorException(ResultCode resultCode) {
        super(resultCode.getDescription());
        this.resultCode = resultCode;
    }
}
