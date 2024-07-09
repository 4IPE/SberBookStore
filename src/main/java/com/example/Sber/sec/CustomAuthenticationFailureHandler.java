package com.example.Sber.sec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String errorMessage = "Invalid username or password";

//        if (exception.getMessage().equalsIgnoreCase("User is disabled")) {
//            errorMessage = "User is disabled";
//        } else if (exception.getMessage().equalsIgnoreCase("User account has expired")) {
//            errorMessage = "User account has expired";
//        }
        request.setAttribute("errorMessage", errorMessage);
        response.sendRedirect(request.getContextPath()+"/login?error="+errorMessage);
    }
}
