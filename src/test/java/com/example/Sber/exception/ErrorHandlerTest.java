package com.example.Sber.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.ui.Model;

import static org.assertj.core.api.Assertions.assertThat;

public class ErrorHandlerTest {

    private ErrorHandler errorHandler;
    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
        this.errorHandler = new ErrorHandler();
    }

    @Test
    void notFoundTest() {
        var ex = new NotFound();
        var res = errorHandler.handleNotFoundException(ex, model);
        assertThat(res)
                .isNotNull()
                .isEqualTo("404error");
    }

    @Test
    void handleExceptionTest() {
        var ex = new Throwable();
        var res = errorHandler.handleException(ex, model);
        assertThat(res)
                .isNotNull()
                .isEqualTo("500error");
    }
}
