package work.ubox.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import work.ubox.community.dto.AccessTokenDTO;
import work.ubox.community.dto.GithubUser;
import work.ubox.community.mapper.UserMapper;
import work.ubox.community.model.User;
import work.ubox.community.provider.GithubProvider;

import javax.servlet.http.HttpServletRequest;
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
    private UserMapper userMapper;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request
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
        if (githubUser != null) {

            User user = new User();
            user.setToken(UUID.randomUUID().toString());
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            //将user信息持久化到数据库
            userMapper.insert(user);
            
            //登录成功，写cookie和session
            request.getSession().setAttribute("githubUser", githubUser);
            return "redirect:/";
        }else{
            //登录失败，重新登陆
            return "redirect:/";
        }

    }
}
