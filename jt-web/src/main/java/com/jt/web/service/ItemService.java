package com.jt.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.HttpClientService;
import com.jt.common.spring.exetend.PropertyConfig;
import com.jt.web.pojo.Item;

@Service
public class ItemService {

	@Autowired
	private HttpClientService httpservice;

	// 通过自定义注解 注入属性文件的属性
	@PropertyConfig
	private String MANAGE_URL;

	// 引用jackson对象ObjectMapper
	private static final ObjectMapper MAPPER = new ObjectMapper();

	/*
	 * 使用httpClient进行后台系统访问并获取到它的返回值 两个系统之间使用json传递数据，现在不同系统间传递数据的主流方式
	 */
	public Item getItem(Long itemId) throws Exception {

		// 后台系统请求链接：/web/item/{item}
		String url = MANAGE_URL + "/web/item/" + itemId;

		// 异常应该抛出，这个异常业务无法继续进行
		String jsonData = httpservice.doGet(url);

		// json转换java对象，利用jackson对象ObjectMapper提供readValue方法将json转换item对象
		Item item = MAPPER.readValue(jsonData, Item.class);
		
		
		return item;
	}
}
