package com.poker999.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.poker999.dao.UserDao;
import com.poker999.model.UserModel;

/**
 * @author Valloon Project
 * @version 1.0 @2019-06-26
 */
@Transactional
public class UserService {

	private static final UserDao userDao = new UserDao();

	public UserModel getUser(final int uid, final boolean withMoney) {
		return userDao.get(uid, withMoney);
	}

	public int count(final String loginID, final String tel, final int permit) {
		return userDao.count(loginID, tel, permit);
	}

	public List<UserModel> getUserList(final int offset, final int limit, final String orderby, final boolean desc, final boolean withMoney, final String loginID, final String tel, final int permit) {
		return userDao.getList(offset, limit, orderby, desc, withMoney, loginID, tel, permit);
	}

	public int updatePermit(final int uid, final int permit) {
		return userDao.updatePermit(uid, permit);
	}

}
