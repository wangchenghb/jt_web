package com.jt.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
	//杞悜鍒伴椤� index.jsp http://www.jt.com/index.html
	
	@RequestMapping("index.html")
	public String index(){
		return "index"; 
	
	
	}
}
