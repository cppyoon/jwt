package com.chyoon.jwt.common;

import ch.qos.logback.classic.Logger;
import com.chyoon.jwt.common.exception.CustomErrorException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = (Logger) LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({MethodArgumentTypeMismatchException.class, ConstraintViolationException.class})
    public ResponseEntity invalidParamException(HttpServletRequest request, Exception e) {
        request.setAttribute("exceptionMessage", e.getMessage());

        return new ResponseEntity<>(ResponseBody.builder()
                .code(ResultCode.INVALID_PARAMETER)
                .build(), HttpStatus.OK);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity invalidParamException(HttpServletRequest request, MethodArgumentNotValidException e) {
        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        StringBuilder sb = new StringBuilder();
        for (ObjectError error : errors) {
            sb.append(error.getDefaultMessage()).append(", ");
        }
        String errorMessage = sb.toString().replaceAll(", $", "");

        request.setAttribute("exceptionMessage", errorMessage);
        return new ResponseEntity<>(ResponseBody.builder()
                .code(ResultCode.INVALID_PARAMETER)
                .build(), HttpStatus.OK);
    }


    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity notFoundErrorExceptions(HttpServletRequest request, NoResourceFoundException e) {
        request.setAttribute("exceptionMessage", e.getMessage());

        return new ResponseEntity<>(ResponseBody.builder()
                .code(ResultCode.NOT_FOUND_URI)
                .build(), HttpStatus.OK);
    }

    @ExceptionHandler(CustomErrorException.class)
    public ResponseEntity customErrorExceptions(HttpServletRequest request, Exception e, CustomErrorException ce) {
        request.setAttribute("exceptionMessage", e.getLocalizedMessage());

        return new ResponseEntity<>(ResponseBody.builder()
                .code(ce.getResultCode())
                .build(), HttpStatus.OK);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity serverErrorExceptions(HttpServletRequest request, Exception e) {
        request.setAttribute("exceptionMessage", e.getLocalizedMessage());

        return new ResponseEntity<>(ResponseBody.builder()
                .code(ResultCode.SERVER_ERROR)
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
