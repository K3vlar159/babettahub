package com.example.babettahub.security;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JwtServiceTest {

    private static final String SECRET = "umE2hOXClZz3XMZ7LP8AjSSa/J12cWpNgC4Llny2E/A=";
    private final JwtService jwtService = new JwtService(SECRET, 3600000);

    @Test
    void generatedToken_isValidAndContainsUsername(){

        String token = jwtService.generateToken("babettar");

        assertAll(
                () -> assertTrue(jwtService.isValid(token)),
                () -> assertEquals("babettar", jwtService.extractUsername(token))
        );
    }

    @Test
    void isValid_tokenSignedWithDifferentKey_returnsFalse(){
        JwtService fakeService = new JwtService("4v4sMohzw8O9Aq0GJ2DmLvrVkFIZtRSsn59wnJCZHB4=", 3600000);
        String fakeToken = fakeService.generateToken("babettar");

        assertFalse(jwtService.isValid(fakeToken));
    }

    @Test
    void isValid_expiredToken_returnsFalse(){
        JwtService expiredService = new JwtService("4v4sMohzw8O9Aq0GJ2DmLvrVkFIZtRSsn59wnJCZHB4=", -1000);
        String expiredToken = expiredService.generateToken("babettar");

        assertFalse(expiredService.isValid(expiredToken));
    }

}
