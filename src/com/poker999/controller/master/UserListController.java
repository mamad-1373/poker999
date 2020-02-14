package com.poker999.controller.master;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.dao.DataAccessException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.poker999.MESSAGE;
import com.poker999.annotation.Role;
import com.poker999.controller.BaseController;
import com.poker999.model.UserModel;
import com.poker999.service.LogService;
import com.poker999.service.UserService;

/**
 * @author Valloon Project
 * @version 1.0 @2019-06-26
 */
@Controller
@RequestMapping(value = "/master/user")
@Role(Role.ROLE_MASTER)
public class UserListController extends BaseController {

	UserService userService = new UserService();

	@GetMapping(path = { "/", "/list" })
	public String getList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		return "master/user/list";
	}

	@GetMapping(path = "/search")
	public String getSearch(HttpServletRequest request, HttpServletResponse response) throws IOException {
		return "master/user/search";
	}

	@GetMapping(path = "/blocked")
	public String getBlocked(HttpServletRequest request, HttpServletResponse response) throws IOException {
		return "master/user/blocked";
	}

	@GetMapping(path = "/list.json", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public JsonObject getListJson(HttpServletRequest request, HttpServletResponse response) throws IOException {
		boolean success = false;
		String msg = null;
		int count = 0;
		JsonArray result = null;
		int offset = getInt(request, "offset", 0);
		int limit = getInt(request, "limit", 10);
		String orderby = getString(request, "orderby", true, false);
		boolean desc = 1 == getInt(request, "desc", 0);
		String qLoginID = getString(request, "loginid", true);
		String qTel = getString(request, "tel", false);
		int qPermit = getInt(request, "permit", -1);
		try {
			String qOrderBy;
			count = userService.count(qLoginID, qTel, qPermit);
			if (offset < 0)
				offset = 0;
			if (limit < 0)
				limit = 10;
			if (orderby == null) {
				orderby = "id";
				qOrderBy = "uid";
			} else if (orderby.equals("id")) {
				qOrderBy = "uid";
			} else if (orderby.equals("loginid")) {
				qOrderBy = "loginid";
			} else if (orderby.equals("tel")) {
				qOrderBy = "tel";
			} else if (orderby.equals("money")) {
				qOrderBy = "money";
			} else {
				orderby = "id";
				qOrderBy = "uid";
			}
			List<UserModel> userList = userService.getUserList(offset, limit, qOrderBy, desc, true, qLoginID, qTel, qPermit);
			result = new JsonArray();
			int i = offset;
			for (UserModel user : userList) {
				JsonObject obj = new JsonObject();
				obj.addProperty("no", ++i);
				obj.addProperty("uid", user.uid);
				obj.addProperty("loginid", user.loginID);
				obj.addProperty("avatar", user.avatar);
				obj.addProperty("tel", user.tel);
				obj.addProperty("money", user.money);
				obj.addProperty("permit", user.permit);
				result.add(obj);
			}
			success = true;
		} catch (DataAccessException e) {
			LogService.log(request, e);
			msg = MESSAGE.DB_ERROR;
		} catch (Exception e) {
			LogService.log(request, e);
			msg = MESSAGE.UNKNOWN_ERROR;
		}
		JsonObject json = new JsonObject();
		json.addProperty("success", success);
		json.addProperty("msg", msg);
		json.addProperty("count", count);
		json.addProperty("offset", offset);
		json.addProperty("limit", limit);
		json.addProperty("loginid", qLoginID);
		json.addProperty("tel", qTel);
		if (qPermit >= 0)
			json.addProperty("permit", qPermit);
		json.addProperty("orderBy", orderby);
		json.addProperty("orderByDesc", desc);
		json.add("result", result);
		return json;
	}

	@GetMapping(path = "/permit", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public JsonObject getPermit(HttpServletRequest request, HttpServletResponse response, @RequestParam("id") int uid, @RequestParam("p") int permit) {
		boolean success = false;
		String msg = null;
		try {
			if (uid < 0 || permit < 0 || permit > 2) {
				msg = MESSAGE.INVALID_INPUT;
			} else if (userService.updatePermit(uid, permit) < 1) {
				msg = MESSAGE.USER_NOT_FOUND;
			} else {
				success = true;
			}
		} catch (DataAccessException e) {
			LogService.log(request, e);
			msg = MESSAGE.DB_ERROR;
		} catch (Exception e) {
			LogService.log(request, e);
			msg = MESSAGE.UNKNOWN_ERROR;
		}
		JsonObject json = new JsonObject();
		json.addProperty("success", success);
		json.addProperty("msg", msg);
		return json;
	}

	@GetMapping(path = "/edit")
	public String getEdit(HttpServletRequest request, HttpServletResponse response, @RequestParam("uid") int uid, Model model) throws IOException {
		try {
			UserModel user = userService.getUser(uid, true);
			if (user == null)
				response.sendError(404, MESSAGE.USER_NOT_FOUND);
			model.addAttribute("user", user);
		} catch (DataAccessException e) {
			LogService.log(request, e);
			response.sendError(500, MESSAGE.DB_ERROR);
		} catch (Exception e) {
			LogService.log(request, e);
			response.sendError(500, MESSAGE.UNKNOWN_ERROR);
		}
		return "master/user/edit";
	}

}
