package com.chyoon.jwt.common.util;

import java.math.BigInteger;
import java.security.MessageDigest;

public class CryptoUtil {
    public static String encryptWithSHA512(String text) {
        String encrytedText = null;

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            String salt = "chyoon";
            md.reset();
            md.update(text.getBytes());
            md.update(salt.getBytes());
            encrytedText =  String.format("%0128x", new BigInteger(1, md.digest()));
        } catch (Exception e) {
            // TODO: 에러처리
        }

        return encrytedText;
    }
}

