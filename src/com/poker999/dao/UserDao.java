package com.poker999.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.poker999.model.UserModel;

/**
 * @author Valloon Project
 * @version 1.0 @2019-06-26
 */
@Repository
public class UserDao extends BaseDao {

	public UserModel get(final int uid, final boolean withMoney) {
		String query = "SELECT tbl_userlist.*";
		query += withMoney ? ",ISNULL(tbl_usermoney.gamemoney,0) money FROM tbl_userlist LEFT JOIN tbl_usermoney ON tbl_userlist.uid=tbl_usermoney.uid" : " FROM tbl_userlist";
		query += " WHERE tbl_userlist.uid=?";
		return getJdbcTemplate().query(query, new Object[] { uid }, new ResultSetExtractor<UserModel>() {
			@Override
			public UserModel extractData(ResultSet rs) throws SQLException {
				if (!rs.next())
					return null;
				UserModel model = new UserModel();
				model.uid = rs.getInt("uid");
				model.loginID = rs.getString("loginid");
				model.password = rs.getString("password");
				model.avatar = rs.getInt("avatar");
				model.tel = rs.getString("tel");
				model.permit = rs.getInt("permit");
				model.memo = rs.getString("memo");
				model.regdate = rs.getTimestamp("regdate");
				model.win = rs.getInt("win");
				model.lose = rs.getInt("lose");
				model.isDeport = rs.getInt("isDeport");
				if (withMoney)
					model.money = rs.getLong("money");
				return model;
			}
		});
	}

	public int count(final String loginID, final String tel, final int permit) {
		String query = "SELECT count(*) FROM tbl_userlist";
		String queryWhere;
		if (permit == 1)
			queryWhere = " WHERE permit=1";
		else if (permit == 2)
			queryWhere = " WHERE permit=2";
		else if (permit == 3)
			queryWhere = " WHERE permit!=1";
		else
			queryWhere = "";
		if (loginID != null)
			queryWhere += " AND loginid LIKE ?";
		if (tel != null)
			queryWhere += " AND tel LIKE ?";
		if (queryWhere.startsWith(" AND"))
			queryWhere = " WHERE" + queryWhere.substring(4);
		query += queryWhere;
		return getJdbcTemplate().query(query, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				int i = 1;
				if (loginID != null)
					ps.setString(i++, '%' + loginID + '%');
				if (tel != null)
					ps.setString(i++, '%' + tel + '%');
			}
		}, new ResultSetExtractor<Integer>() {
			@Override
			public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
				if (!rs.next())
					return null;
				return rs.getInt(1);
			}
		});
	}

	public List<UserModel> getList(int offset, final int limit, String orderby, final boolean desc, final boolean withMoney, final String loginID, final String tel, final int permit) {
		String query = "SELECT tbl_userlist.*";
		query += withMoney ? ",ISNULL(tbl_usermoney.gamemoney,0) money FROM tbl_userlist LEFT JOIN tbl_usermoney ON tbl_userlist.uid=tbl_usermoney.uid" : " FROM tbl_userlist";
		String queryWhere;
		if (permit == 1)
			queryWhere = " WHERE permit=1";
		else if (permit == 2)
			queryWhere = " WHERE permit=2";
		else if (permit == 3)
			queryWhere = " WHERE permit!=1";
		else
			queryWhere = "";
		if (loginID != null)
			queryWhere += " AND loginid LIKE ?";
		if (tel != null)
			queryWhere += " AND tel LIKE ?";
		if (queryWhere.startsWith(" AND"))
			queryWhere = " WHERE" + queryWhere.substring(4);
		query += queryWhere;
		if (orderby == null)
			orderby = "uid";
		query += " ORDER BY " + orderby;
		if (desc)
			query += " DESC";
		query += " OFFSET ? ROWS";
		if (limit > 0)
			query += " FETCH NEXT ? ROWS ONLY";
		return getJdbcTemplate().query(query, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				int i = 1;
				if (loginID != null)
					ps.setString(i++, '%' + loginID + '%');
				if (tel != null)
					ps.setString(i++, '%' + tel + '%');
				ps.setInt(i++, offset);
				if (limit > 0)
					ps.setInt(i++, limit);
			}
		}, new RowMapper<UserModel>() {
			@Override
			public UserModel mapRow(ResultSet rs, int rownumber) throws SQLException {
				UserModel model = new UserModel();
				model.uid = rs.getInt("uid");
				model.loginID = rs.getString("loginid");
				model.password = rs.getString("password");
				model.avatar = rs.getInt("avatar");
				model.tel = rs.getString("tel");
				model.permit = rs.getInt("permit");
				model.memo = rs.getString("memo");
				model.regdate = rs.getTimestamp("regdate");
				model.win = rs.getInt("win");
				model.lose = rs.getInt("lose");
				model.isDeport = rs.getInt("isDeport");
				if (withMoney)
					model.money = rs.getLong("money");
				return model;
			}
		});
	}

	public int updatePermit(final int uid, final int permit) {
		String query = "UPDATE tbl_userlist SET permit=? WHERE uid=?";
		return getJdbcTemplate().update(query, new Object[] { permit, uid });
	}

//	 public void insert(final UserModel user) {
//		String query = "insert into user values(?,?,?,?,?,?,?);";
//		jdbcTemplate.execute(query, new PreparedStatementCallback<Boolean>() {
//			@Override
//			public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
//				ps.setString(1, user.id);
//				ps.setString(2, user.password);
//				ps.setTimestamp(3, new Timestamp(user.time.getTime()));
//				ps.setString(4, user.ip);
//				ps.setString(5, user.header);
//				ps.setString(6, user.text);
//				ps.setInt(7, user.allowed);
//				return ps.execute();
//			}
//		});
//	}

}
