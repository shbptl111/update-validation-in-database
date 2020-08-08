package org.maze.update_validation_in_database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Autowired;

public class SQLUpdate {

	@Autowired
	Connection connection;

	PreparedStatement sql;

	public void createPreparedStatement() {
		try {
			sql = connection.prepareStatement("INSERT INTO #RECORDS VALUES(?, ?)");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void closePreparedStatement() {
		try {
			sql.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void prepareSQLStatement(String numberType, String phoneNumber) {

		try {
			sql.setString(1, phoneNumber);
			sql.setString(2, numberType);
			sql.addBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void executeSQLStatement() {
		try {
			sql.executeBatch();
			sql.clearBatch();
			System.out.println("==================================");

			String updateTable = "UPDATE A \n" + 
			                     "SET A.ContactType = B.ContactType \n" + 
					             "FROM TestTable A \n" + 
			                     "INNER JOIN #RECORDS B \n" + 
					             "ON A.MobileNo = B.MobileNo";

			String deleteTempData = "DELETE FROM #RECORDS";
			Statement statement = connection.createStatement();
			System.out.println("Number of rows affected after batch update: " + statement.executeUpdate(updateTable));
			System.out.println("Number of rows deleted from temporary table: " + statement.executeUpdate(deleteTempData));
			statement.close();
			connection.commit();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

}
