package com.poker999.model;

import java.util.Date;

/**
 * @author Valloon Project
 * @version 1.0 @2019-06-26
 */
public final class UserModel {

	public int uid;
	public String loginID;
	public String password;
	public int avatar;
	public String tel;
	public int permit;
	public String memo;
	public Date regdate;
	public int win;
	public int lose;
	public int isDeport;
	public long money;
	public long gameMoney;
	public long enterMoney;

	public UserModel() {
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getLoginID() {
		return loginID;
	}

	public void setLoginID(String loginID) {
		this.loginID = loginID;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getAvatar() {
		return avatar;
	}

	public void setAvatar(int avatar) {
		this.avatar = avatar;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public int getPermit() {
		return permit;
	}

	public void setPermit(int permit) {
		this.permit = permit;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Date getRegdate() {
		return regdate;
	}

	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}

	public int getWin() {
		return win;
	}

	public void setWin(int win) {
		this.win = win;
	}

	public int getLose() {
		return lose;
	}

	public void setLose(int lose) {
		this.lose = lose;
	}

	public int getIsDeport() {
		return isDeport;
	}

	public void setIsDeport(int isDeport) {
		this.isDeport = isDeport;
	}

	public long getMoney() {
		return money;
	}

	public void setMoney(long money) {
		this.money = money;
	}

	public long getGameMoney() {
		return gameMoney;
	}

	public void setGameMoney(long gameMoney) {
		this.gameMoney = gameMoney;
	}

	public long getEnterMoney() {
		return enterMoney;
	}

	public void setEnterMoney(long enterMoney) {
		this.enterMoney = enterMoney;
	}

}
