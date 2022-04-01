package work.ubox.community.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/***
 * 允许这个类去接收前端的
 */
@Controller
public class HelloController {

//    ctrl + p 会提示要穿什么参数
    @GetMapping("/hello")
    public String hello(@RequestParam(name="name") String name, Model model) {
        //将浏览器传入的值传入model里面
        model.addAttribute("name", name);
        // return之后会去templates目录找hello.html
        return "hello";
    }
}
