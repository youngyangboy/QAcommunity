package work.ubox.community.advice;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import work.ubox.community.exception.CustomizeException;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Component
public class CustomizeExceptionHandler {

    @ExceptionHandler(Exception.class)
    ModelAndView handle(HttpServletRequest request, Throwable e, Model model) {
        if (e instanceof CustomizeException) {
            model.addAttribute("message", e.getMessage());
        }else{
            model.addAttribute("message", (Object)"服务冒烟了，稍后再试试？");
        }
        return new ModelAndView("error");
    }
}
