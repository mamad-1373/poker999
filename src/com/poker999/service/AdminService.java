package com.poker999.service;

import org.springframework.transaction.annotation.Transactional;

import com.poker999.dao.AdminDao;
import com.poker999.model.AdminModel;

/**
 * @author Valloon Project
 * @version 1.0 @2019-06-26
 */
@Transactional
public class AdminService {

	private static AdminDao adminDao = new AdminDao();

	public AdminModel getAdmin(String loginID) {
		return adminDao.get(loginID);
	}

}
