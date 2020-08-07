package org.maze.update_validation_in_database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DBConfig {
	
	private Connection connection;

	@Bean(name = "databaseConnection")
	public Connection getConnection() {
		connection = null;
		try {
			connection = DriverManager
					.getConnection("jdbc:sqlserver://localhost:1433;databaseName=students;integratedSecurity=true;");
			connection.setAutoCommit(false);
			System.out.println("Connection with database established");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	@Bean(name = "preparedStatement")
	public PreparedStatement getPreparedStatement() {
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement("UPDATE TestTable SET ContactType = ? WHERE MobileNo = ?");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return preparedStatement;
	}
}
