package com.chyoon.jwt;

import com.chyoon.jwt.common.util.CryptoUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class loginTest {

    @Test
    void test1() {
        String pass = "1234";
        String abc = CryptoUtil.encryptWithSHA512(pass);
        System.out.println(abc);
    }
}
