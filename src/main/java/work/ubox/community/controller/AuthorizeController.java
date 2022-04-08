package work.ubox.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import work.ubox.community.dto.AccessTokenDTO;
import work.ubox.community.dto.GithubUser;
import work.ubox.community.model.User;
import work.ubox.community.provider.GithubProvider;
import work.ubox.community.service.UserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.uri}")
    private String redirectUri;


    @Autowired
    private UserService userService;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletResponse response
                           ) {
        //对AccessToken进行成员赋值
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        //获取AccessTokenDTO
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        //通过AccessToken获取github用户信息
        GithubUser githubUser = githubProvider.getUser(accessToken);
        //获取到githubUser之后存入数据库，并且写入session
        if (githubUser != null && githubUser.getId()!=null) {

            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setAvatarUrl(githubUser.getAvatar_url());
            //将user信息持久化到数据库
            userService.createOrUpdate(user);

            response.addCookie(new Cookie("token",token));
            
            //登录成功，写cookie和session
//            request.getSession().setAttribute("githubUser", githubUser);
            return "redirect:/";
        }else{
            //登录失败，重新登陆
            System.out.println("登录失败！");
            return "redirect:/";
        }

    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response) {

        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }
}
