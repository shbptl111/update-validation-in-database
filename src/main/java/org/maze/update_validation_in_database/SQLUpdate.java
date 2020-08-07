package org.maze.update_validation_in_database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;

public class SQLUpdate {

	@Autowired
	Connection connection;

	@Autowired
	PreparedStatement sql;

	public void prepareSQLStatement(String numberType, String phoneNumber) {
		
		try {
			sql.setString(1, numberType);
			sql.setString(2, phoneNumber);
			sql.addBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void executeSQLStatement(boolean doExecute) {
		if (doExecute) {
			try {
				System.out.println("AutoCommit is set to: " + connection.getAutoCommit());
				connection.setAutoCommit(false);
				Timestamp time = new Timestamp(System.currentTimeMillis());
				System.out.println("Executing batch: " + time);
				sql.executeBatch();
				System.out.println("Execute complete" + time);
				connection.commit();
				sql.clearBatch();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

		}
	}

}
