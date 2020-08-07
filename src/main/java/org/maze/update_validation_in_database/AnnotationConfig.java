package org.maze.update_validation_in_database;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AnnotationConfig {

	@Bean(name = "myCSVReader")
	public MyCSVReader getMyCSVReader() {
		MyCSVReader myCSVReader = new MyCSVReader();
		return myCSVReader;
	}
	
	@Bean(name = "sqlUpdate")
	public SQLUpdate getsqlUpdate() {
		SQLUpdate sqlUpdate = new SQLUpdate();
		return sqlUpdate;
	}
	
}
