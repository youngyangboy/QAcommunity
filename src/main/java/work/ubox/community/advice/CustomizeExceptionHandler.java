package work.ubox.community.advice;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import work.ubox.community.dto.ResultDTO;
import work.ubox.community.exception.CustomizeErrorCode;
import work.ubox.community.exception.CustomizeException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice
@Component
public class CustomizeExceptionHandler {

    @ExceptionHandler(Exception.class)
    Object handle(HttpServletRequest request,
                  HttpServletResponse response,
                  Throwable e,
                  Model model) {
        String contentType = request.getContentType();
        if ("application/json".equals(contentType)) {
            //返回json
            ResultDTO resultDTO;
            if (e instanceof CustomizeException) {
                resultDTO = ResultDTO.errorOf((CustomizeException)e);
            }else{
                resultDTO = ResultDTO.errorOf(CustomizeErrorCode.SYSTEM_ERROR);
            }
            try {
                response.setContentType("application/json");
                response.setStatus(200);
                response.setCharacterEncoding("UTF-8");
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDTO));
                writer.close();
            } catch (IOException ioe) {
            }
            return null;

        }else{
            //返回错误页面跳转
            if (e instanceof CustomizeException) {
                model.addAttribute("message", e.getMessage());
            }else{
                model.addAttribute("message", (Object)CustomizeErrorCode.SYSTEM_ERROR.getMessage());
            }
            return new ModelAndView("error");
        }
    }
}
