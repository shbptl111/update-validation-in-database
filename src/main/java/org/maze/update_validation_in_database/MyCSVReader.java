package org.maze.update_validation_in_database;

import java.io.FileReader;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

public class MyCSVReader {
	
	@Autowired
	SQLUpdate sqlUpdate;
	
	public void readCSVFile(FileReader fileReader) {

		CSVReader csvReader = new CSVReaderBuilder(fileReader).withSkipLines(1).build();
		String[] records = null;
		int count = 0;
		String numberType;
		String phoneNumber;

		try {

			while ((records = csvReader.readNext()) != null) {

				if (count < 100) {

					numberType = records[11];
					phoneNumber = records[0];
					sqlUpdate.prepareSQLStatement(numberType, phoneNumber);
					count++;

				} else {
					numberType = records[11];
					phoneNumber = records[0];
					sqlUpdate.prepareSQLStatement(numberType, phoneNumber);
					sqlUpdate.executeSQLStatement(true);
					count = 0;

				}

			}
			
			if(count != 0) {
				sqlUpdate.executeSQLStatement(true);
			}

			csvReader.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
