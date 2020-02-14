package com.poker999.controller;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.poker999.annotation.Role;
import com.poker999.controller.BaseController;
import com.poker999.model.AdminModel;

/**
 * @author Valloon Project
 * @version 1.0 @2019-06-26
 */
@Controller
public class RootController extends BaseController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	@Role
	public String root(HttpServletRequest request, HttpServletResponse response, @SessionAttribute(SESSION_ME) AdminModel me) throws IOException {
		if (me.type == AdminModel.TYPE_MASTER)
			return "redirect:/master/user/list";
		if (me.type == AdminModel.TYPE_EMPLOYEE)
			return "redirect:/employee/user/list";
		return null;
	}

}
