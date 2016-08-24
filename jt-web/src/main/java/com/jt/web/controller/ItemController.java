package com.jt.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jt.common.spring.exetend.PropertyConfig;
import com.jt.web.service.ItemService;

@Controller
@RequestMapping("/item")
public class ItemController {
	// http://www.jt.com/item/562379.html

	@Autowired
	ItemService itemService;

	// 通过自定义注解 注入属性文件的属性 (试验无法注入属性)
	@PropertyConfig
	private String MANAGE_URL;

	@RequestMapping("{itemId}")
	public String item(@PathVariable Long itemId, Model model) throws Exception {
		// 添加对象到域，带到页面
		model.addAttribute("item", itemService.getItem(itemId));
		
		return "item";// 转向页面
	}
}
