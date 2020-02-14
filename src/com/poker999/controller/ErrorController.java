package com.poker999.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.poker999.MESSAGE;
import com.poker999.annotation.Role;
import com.poker999.service.LogService;

/**
 * @author Valloon Project
 * @version 1.0 @2019-07-03
 */
@Controller
public class ErrorController {

	@RequestMapping(path = "/error")
	@Role
	public String error(HttpServletRequest request, Model model) {
		String uri = (String) request.getAttribute("javax.servlet.error.request_uri");
		Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
		String msg = (String) request.getAttribute("javax.servlet.error.message");
		Object exceptionType = request.getAttribute("javax.servlet.error.exception_type");
		Object exception = request.getAttribute("javax.servlet.error.exception");
		LogService.printConsole(request, uri + "\t" + statusCode + "\t" + msg + "\t" + exceptionType + "\t" + exception, LogService.LEVEL_WARN);
		model.addAttribute("uri", uri);
		model.addAttribute("statusCode", statusCode);
		model.addAttribute("msg", msg);
		model.addAttribute("exceptionType", exceptionType);
		model.addAttribute("exception", exception);
		return "error";
	}

	@RequestMapping(path = "/require-login.json", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public Map<String, Object> requireLoginJson(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", false);
		map.put("logout", true);
		map.put("msg", MESSAGE.REQUIRE_RE_LOGIN);
		return map;
	}

}
