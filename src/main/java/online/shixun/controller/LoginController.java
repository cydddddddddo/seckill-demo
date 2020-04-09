package online.shixun.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import online.shixun.pojo.SeckillUser;
import online.shixun.pojo.vo.LoginVo;
import online.shixun.service.SeckillUserService;

@Controller
@RequestMapping("/login")
public class LoginController {

	private static Logger log = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	SeckillUserService userService;

    /**
     * 跳转登陆页面
     * @return
     */
    @RequestMapping("/to_login")
    public String toLogin() {
        return "login";
    }

    /**
     * 验证登陆信息
     * @param loginVo
     * @param session
     * @return
     */
    @RequestMapping("/do_login")
    public String doLogin(@Valid LoginVo loginVo,HttpSession session) {
    	log.info(loginVo.toString());
    	//登录
    	SeckillUser user = userService.login(loginVo);
    	session.setAttribute("user", user);
    	return "forward:/goods/to_list";
    }
}
