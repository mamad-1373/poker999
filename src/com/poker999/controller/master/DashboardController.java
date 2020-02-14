package com.poker999.controller.master;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.poker999.annotation.Role;
import com.poker999.controller.BaseController;

/**
 * @author Valloon Project
 * @version 1.0 @2019-06-26
 */
@Controller
@RequestMapping(value = "/master")
@Role(Role.ROLE_MASTER)
public class DashboardController extends BaseController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getDashboard(HttpServletRequest request, HttpServletResponse response) throws IOException {
		return "master/dashboard";
	}

}
