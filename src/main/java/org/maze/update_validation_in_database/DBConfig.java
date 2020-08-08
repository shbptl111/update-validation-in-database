package org.maze.update_validation_in_database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DBConfig {

	@Autowired
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

	@PostConstruct
	public void createTempTable() {
		try {
			Statement statement = connection.createStatement();
			String sql = "CREATE TABLE #RECORDS(MobileNo nvarchar(500), \n" + "ContactType nvarchar(500))";
			statement.execute(sql);
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@PreDestroy
	public void closeConnection() {
		try {
			System.out.println("Closing connection with database");
			if (connection != null)
				connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
