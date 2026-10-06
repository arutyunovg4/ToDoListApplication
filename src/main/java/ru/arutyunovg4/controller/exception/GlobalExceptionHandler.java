package ru.arutyunovg4.controller.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.webmvc.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@ControllerAdvice
public class GlobalExceptionHandler implements ErrorController { // отключает обработчик по умолчанию от спринг бута

    @RequestMapping("/error")
    public String redirectToSpecificErrorPage(HttpServletResponse response){
        switch (HttpStatus.valueOf(response.getStatus())){
            case  FORBIDDEN :
                return "redirect:/error/403";
            case NOT_FOUND :
                return "redirect:/error/404";
            default:
                return "redirect:/error/500";
        }
    }

    @RequestMapping("/error/500")
    public String getCommonErrorPage(){
        return "public/error/common-error-page";
    }

    @RequestMapping("/error/403")
    public String getForbiddenErrorPage(){
        return "public/error/common-error-page";
    }
    @RequestMapping("/error/304")
    public String getNotFoundErrorPage(){
        return "public/error/common-error-page";
    }

    @ExceptionHandler(Throwable.class)
    public String handleThrowable(Throwable throwable){
        return "redirect:/error/500";
    }
}
