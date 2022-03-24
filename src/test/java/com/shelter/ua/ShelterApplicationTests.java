package com.shelter.ua;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
public class ShelterApplicationTests {

    @Test
    public void shouldRunWithoutException() {
        assertDoesNotThrow(() -> ShelterUaApplication.main(new String[]{}));
    }

}
