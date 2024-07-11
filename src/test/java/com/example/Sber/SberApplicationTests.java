package com.example.Sber;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class SberApplicationTests {

    @Test
    void contextLoads() {
        assertDoesNotThrow(SberApplication::new);
        assertDoesNotThrow(() -> SberApplication.main(new String[]{}));
    }


}
