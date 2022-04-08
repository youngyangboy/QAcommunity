package work.ubox.community.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import work.ubox.community.dto.PaginationDTO;
import work.ubox.community.dto.QuestionDTO;
import work.ubox.community.mapper.UserMapper;
import work.ubox.community.model.User;
import work.ubox.community.service.QuestionService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/***
 * 允许这个类去接收前端的
 */
@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model,
                        // @RequestParam：从前端接收参数
                        @RequestParam(name="page",defaultValue = "1") Integer page,
                        @RequestParam(name="size",defaultValue = "8") Integer size
                        ) {

//            Cookie[] cookies = request.getCookies();
//            if (cookies != null && cookies.length != 0){
//                for (Cookie cookie : cookies) {
//                    if (cookie.getName().equals("token")){
//                        String token = cookie.getValue();
//                        User user = userMapper.findByToken(token);
//                        if (user != null) {
//                            request.getSession().setAttribute("user", user);
//                        }
//                        break;
//                    }
//                }
//            }
            PaginationDTO pagination = questionService.list(page,size);
            model.addAttribute("pagination", pagination);
            return "index";
    }
}
