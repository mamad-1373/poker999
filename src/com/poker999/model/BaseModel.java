package com.poker999.model;

import java.sql.Timestamp;

/**
 * @author Valloon Project
 * @version 1.0 @2019-07-03
 */
public abstract class BaseModel {

	public int uid;
	public long money;
	public int type;
	public Timestamp regdate;
	public String memo;

	public BaseModel() {
	}

	public long getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public long getMoney() {
		return money;
	}

	public void setMoney(long money) {
		this.money = money;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Timestamp getRegdate() {
		return regdate;
	}

	public void setRegdate(Timestamp regdate) {
		this.regdate = regdate;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}
