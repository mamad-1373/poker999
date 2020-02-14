package com.poker999.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.poker999.model.AdminModel;
import com.poker999.service.AdminService;
import com.poker999.service.LogService;
import com.poker999.MESSAGE;
import com.poker999.controller.BaseController;

/**
 * @author Valloon Project
 * @version 1.0 @2019-05-14
 */
@Controller
public class LoginController extends BaseController {

	public static AdminService adminService = new AdminService();

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String getLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
		return "login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String postLogin(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
		String id = getString(request, "i", true, false);
		String password = getString(request, "p", true, false);
		String msg = null;
		AdminModel user;
		try {
			if (id == null || (user = adminService.getAdmin(id)) == null) {
				msg = MESSAGE.LOGIN_INVALID_ID;
			} else if (!user.loginPassword.equals(password)) {
				msg = MESSAGE.LOGIN_INVALID_PASSWORD;
			} else {
				HttpSession session = request.getSession();
				session.setAttribute(SESSION_ME, user);
				LogService.printConsole(request, id + "\t" + session.getId(), LogService.LEVEL_INFO);
				return "redirect:/";
			}
		} catch (Exception e) {
			msg = MESSAGE.DB_ERROR;
			LogService.log(request, e);
		}
		model.addAttribute("msg", msg);
		model.addAttribute("id", id);
		model.addAttribute("password", password);
		return "login";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String getLogout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		AdminModel me = (AdminModel) session.getAttribute(SESSION_ME);
		String id = null;
		if (me != null) {
			id = me.loginID;
		}
		session.invalidate();
		LogService.printConsole(request, id + "\t" + session.getId(), LogService.LEVEL_INFO);
		return "redirect:/login";
	}

}
