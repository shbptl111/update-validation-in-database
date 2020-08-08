package org.maze.update_validation_in_database;

import java.io.FileReader;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import com.opencsv.CSVReader;

public class MyCSVReader {

	@Autowired
	SQLUpdate sqlUpdate;

	public void readCSVFile(FileReader fileReader) {

		sqlUpdate.createPreparedStatement();

		// CSVReader csvReader = new
		// CSVReaderBuilder(fileReader).withSkipLines(1).build();
		CSVReader csvReader = new CSVReader(fileReader);
		String[] records = null;
		int count = 0;
		String numberType;
		String phoneNumber;
		int numberTypeColumn = 0;
		int phoneNumberColumn = 0;

		try {

			records = csvReader.readNext();

			for (int i = 0; i <= (records.length - 1); i++) {

				if (records[i].equalsIgnoreCase("Phone")) {
					phoneNumberColumn = i;
				}

				if (records[i].equalsIgnoreCase("NumberType")) {
					numberTypeColumn = i;
				}
			}

			while ((records = csvReader.readNext()) != null) {
				
				numberType = records[numberTypeColumn];
				phoneNumber = records[phoneNumberColumn];
				sqlUpdate.prepareSQLStatement(numberType, phoneNumber);
				count++;
				
				if (count == 10000) {
					sqlUpdate.executeSQLStatement();
					count = 0;
				}

			}

			if (count != 0) {
				sqlUpdate.executeSQLStatement();
			}

			csvReader.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		sqlUpdate.closePreparedStatement();

	}

}
