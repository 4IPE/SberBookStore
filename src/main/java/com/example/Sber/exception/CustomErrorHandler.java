package com.example.Sber.exception;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


import java.net.http.HttpRequest;

@Controller
public class CustomErrorHandler implements ErrorController {
    @RequestMapping("/error")
    public String handlerError(HttpServletRequest request, Model model){
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if(status!=null){
            int statusCode = Integer.parseInt(status.toString());
            if(statusCode == 403){
                return "403error";
            }
            if(statusCode==500){
                return "500error";
            }
            if(statusCode==404){
                return "404error";
            }
        }
        model.addAttribute("status",status);
        return "error";
    }
}
