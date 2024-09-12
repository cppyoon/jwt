package com.chyoon.jwt.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class DefaultController {

    @GetMapping("healthcheck")
    public ResponseEntity healthCheck(){
        return new ResponseEntity<>( "healthcheck!", HttpStatus.OK);
    }
}


