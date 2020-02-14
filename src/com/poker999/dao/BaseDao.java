package com.poker999.dao;

import java.io.IOException;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Repository;

import com.poker999.Config;
import com.poker999.service.LogService;

/**
 * @author Valloon Project
 * @version 1.0 @2019-07-03
 */
@Repository
public abstract class BaseDao {

	private static DriverManagerDataSource dataSource = null;
	private static JdbcTemplate jdbcTemplate = null;

	private static final DriverManagerDataSource getDataSource() {
		if (dataSource == null) {
			try {
				Properties jdbcProperties = PropertiesLoaderUtils.loadProperties(new ClassPathResource(Config.JDBC_PROPERTIES));
				dataSource = new DriverManagerDataSource();
				dataSource.setDriverClassName(jdbcProperties.getProperty("driverClassName"));
				dataSource.setUrl(jdbcProperties.getProperty("url"));
				dataSource.setUsername(jdbcProperties.getProperty("username"));
				dataSource.setPassword(jdbcProperties.getProperty("password"));
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return dataSource;
	}

	public static final JdbcTemplate getJdbcTemplate() {
		if (jdbcTemplate == null) {
			jdbcTemplate = new JdbcTemplate(getDataSource());
			LogService.printConsole(LogService.getTimeString() + "<jdbcTemplate has created>", LogService.LEVEL_DEBUG);
		}
		return jdbcTemplate;
	}

	public static final SimpleJdbcCall getSimpleJdbcCall() {
		return new SimpleJdbcCall(getDataSource());
	}

}
