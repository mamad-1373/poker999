package com.poker999.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.poker999.model.AdminModel;

/**
 * @author Valloon Project
 * @version 1.0 @2019-07-03
 */
@Repository
public class AdminDao extends BaseDao {

	public AdminModel get(final String loginID) {
		String query = "SELECT * FROM tbl_adminlist WHERE loginid=?";
		return getJdbcTemplate().query(query, new Object[] { loginID }, new ResultSetExtractor<AdminModel>() {
			@Override
			public AdminModel extractData(ResultSet rs) throws SQLException {
				if (!rs.next())
					return null;
				AdminModel model = new AdminModel();
				model.uid = rs.getInt("uid");
				model.loginID = rs.getString("loginid");
				model.loginPassword = rs.getString("password");
				model.type = rs.getInt("type");
				model.permit = rs.getInt("permit");
				return model;
			}
		});
	}

}
