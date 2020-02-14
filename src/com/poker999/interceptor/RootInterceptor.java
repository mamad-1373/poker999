package com.poker999.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import com.poker999.Config;
import com.poker999.annotation.Role;
import com.poker999.controller.BaseController;
import com.poker999.model.AdminModel;
import com.poker999.service.LogService;

/**
 * @author Valloon Project
 * @version 1.0 @2019-07-04
 */
public class RootInterceptor extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (handler instanceof ResourceHttpRequestHandler) {
			if (Config.DEV_MODE) {
				String servletPath = request.getServletPath();
				if (servletPath.startsWith("/assets/")) {
					response.setHeader("Cache-Control", "no-cache"); // HTTP 1.1
					response.setHeader("Pragma", "no-cache"); // HTTP 1.0
					response.setDateHeader("Expires", 0); // prevents caching at the proxy server
				}
			}
			return true;
		}
		boolean isResponseBody = false;
		if (handler instanceof HandlerMethod) {
			HandlerMethod method = (HandlerMethod) handler;
			Object controller = method.getBean();
			isResponseBody = controller.getClass().getAnnotation(RestController.class) != null || method.getMethodAnnotation(ResponseBody.class) != null;
			String contextPath = request.getContextPath();
//			ServletContext context = request.getServletContext();
			HttpSession session = request.getSession();
			AdminModel me = (AdminModel) session.getAttribute(BaseController.SESSION_ME);
			block_success: {
				int role = 0;
				Role permitForMethod = method.getMethodAnnotation(Role.class);
				if (permitForMethod == null) {
					Role permitForClass = controller.getClass().getAnnotation(Role.class);
					if (permitForClass == null)
						break block_success;
					role = permitForClass.value();
				} else {
					role = permitForMethod.value();
				}
				if (me != null) {
					if (role == 0 || role == Role.ROLE_MASTER && me.type == AdminModel.TYPE_MASTER || role == Role.ROLE_EMPLOYEE && me.type == AdminModel.TYPE_EMPLOYEE)
						break block_success;
				}
				if (isResponseBody) {
					request.getRequestDispatcher("/require-login.json").forward(request, response);
				} else {
					response.sendRedirect(contextPath + "/logout");
				}
				return false;
			}
			if (!isResponseBody)
				LogService.printConsole(request, handler.toString(), LogService.LEVEL_DEBUG);
		}
		return true;
	}
}
