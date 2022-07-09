package com.example.security_jwt.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JWTUtilTest {
    private JWTUtil jwtUtil;

    @BeforeEach
    public void testBefore() {
        System.out.println("testBefore-----------------");
        jwtUtil = new JWTUtil();
    }

    @Test
    void generateToken() throws Exception {
        String email = "jordan@naver.com";
        String jwt = jwtUtil.generateToken(email);

        System.out.println(jwt);
        String[] values = jwt.split("[.]");
        System.out.println(Arrays.toString(values));
        assertThat(values.length)
            .isEqualTo(3);
    }

    @Test
    void validateAndExtract() throws Exception {
        String email = "jordan@naver.com1";
        String jwt = jwtUtil.generateToken(email);

        String decodedEmail = jwtUtil.validateAndExtract(jwt);
        assertThat(decodedEmail)
            .isEqualTo(email);
        System.out.println(jwt);
        System.out.println(decodedEmail);
    }
}