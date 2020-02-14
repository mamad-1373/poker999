package com.poker999.model;

/**
 * @author Valloon Project
 * @version 1.0 @2019-06-26
 */
public final class AdminModel extends BaseModel {

	public static final int TYPE_MASTER = 1;
	public static final int TYPE_EMPLOYEE = 2;
	public String loginID;
	public String loginPassword;
	public int permit;
	public String tel;

	public AdminModel() {
	}

	public String getLoginID() {
		return loginID;
	}

	public void setLoginID(String loginID) {
		this.loginID = loginID;
	}

	public String getLoginPassword() {
		return loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

	public int getPermit() {
		return permit;
	}

	public void setPermit(int permit) {
		this.permit = permit;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public boolean getIsMaster() {
		return this.type == TYPE_MASTER;
	}

	public boolean getIsEmployee() {
		return this.type == TYPE_EMPLOYEE;
	}

}
