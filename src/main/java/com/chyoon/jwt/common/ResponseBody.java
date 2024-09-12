package com.chyoon.jwt.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
@RequiredArgsConstructor
public class ResponseBody {

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS", timezone = "Asia/Seoul")
    private LocalDateTime timestamp;
    private int code;
    private String message;
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private Object result;

    @Builder
    private ResponseBody(ResultCode code, Object result){
        this.code = code.getCode();
        this.message = code.getDescription();
        this.result = result;
        this.timestamp = LocalDateTime.now();
    }
}
