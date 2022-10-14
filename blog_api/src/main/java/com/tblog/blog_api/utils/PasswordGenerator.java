package com.tblog.blog_api.utils;

import org.apache.commons.codec.digest.DigestUtils;

public class PasswordGenerator {
    public static void main(String[] args) {
        String password_m = "admin";
        String slat = "mszlu!@#";
        String password = DigestUtils.md5Hex(password_m + slat);
        System.out.printf(password);

    }
}
