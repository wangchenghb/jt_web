package com.jt.web.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.HttpClientService;
import com.jt.common.util.CookieUtils;
import com.jt.common.vo.SysResult;
import com.jt.web.pojo.User;

@Service
public class UserService {
	@Autowired
	private HttpClientService httpClientService;

	private static final ObjectMapper MAPPER = new ObjectMapper();

	public SysResult doRegister(User user) throws Exception {
		String url = "http://sso.jt.com/user/register";

		Map<String, String> params = new HashMap<String, String>();
		params.put("username", user.getUsername());
		params.put("password", user.getPassword());
		params.put("phone", user.getPhone());
		params.put("email", user.getPhone());// 不是最终，因为用不到email 但是数据库有唯一索引 校验

		// 发送请求
		String jedisData = httpClientService.doPost(url, params, "UTF-8");

		if (StringUtils.isNotEmpty(jedisData)) {
			return SysResult.ok();
		}

		return null;
	}

	public String doLogin(String username, String password) throws Exception {
		String url = "http://sso.jt.com/user/login";

		Map<String, String> params = new HashMap<String, String>();
		params.put("u", username);
		params.put("p", password);

		String jedisData = httpClientService.doPost(url, params, "UTF-8");
		// 获取
		JsonNode jsonNode = MAPPER.readTree(jedisData);
		String ticket = jsonNode.get("data").asText();

		// CookieUtils.setCookie(request, response, JT_TICKET, ticket);

		return ticket;
	}
}
