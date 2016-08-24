package com.jt.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.util.CookieUtils;
import com.jt.common.vo.SysResult;
import com.jt.web.pojo.User;
import com.jt.web.service.UserService;
import com.mysql.jdbc.StringUtils;

@Controller
@RequestMapping("user")
public class UserController {
	@Autowired
	private UserService userservice;

	public static String JT_TICKET = "JT_TICKET";

	// 转向注册页面
	// GET /user/register.html HTTP/1.1
	@RequestMapping("register")
	public String register() {
		return "register";
	}

	// 注册保存
	// http://web.jt.com/service/user/doRegister
	@RequestMapping("doRegister")
	@ResponseBody
	public SysResult doregister(User user) throws Exception {
		return userservice.doRegister(user);
	}

	// 转向登陆页面
	// http://web.jt.com/user/login.html
	@RequestMapping("login")
	public String login() {
		return "login";
	}

	// http://web.jt.com/service/user/doLogin?r=0.5380055117153707
	@RequestMapping("doLogin")
	@ResponseBody
	public SysResult doLogin(String username, String password, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//将ticket值保存用户cookie中，这样下次业务访问时就可以从cookie中获取值，最终查询当前用户信息，无需再次登陆
		String ticket = userservice.doLogin(username, password);
		if(ticket!=null&&!"null".equals(ticket)){
			CookieUtils.setCookie(request, response, JT_TICKET, ticket);
			return SysResult.ok();
		}else{
			return SysResult.build(201, "");
		}
		
	}
}
