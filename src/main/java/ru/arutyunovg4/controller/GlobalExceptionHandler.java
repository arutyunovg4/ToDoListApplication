package ru.arutyunovg4.controller;

import org.springframework.boot.webmvc.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@ControllerAdvice
public class GlobalExceptionHandler implements ErrorController { // отключает обработчик по умолчанию от спринг бута

    @RequestMapping("/error-page")
    public String getErrorPage(){
        return "error-page";
    }

    @ExceptionHandler(Throwable.class)
    public String handleThrowable(Throwable throwable){
        return "redirect:/error";
    }
}
