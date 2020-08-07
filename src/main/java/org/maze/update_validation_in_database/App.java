package org.maze.update_validation_in_database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
	
	static AnnotationConfigApplicationContext context;

	public static void main(String[] args) {

		String filePath = null;

		context = new AnnotationConfigApplicationContext();
		context.scan("org.maze");
		context.refresh();

		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter the file path: ");
		filePath = scanner.nextLine();

		scanner.close();

		File file = new File(filePath);
		scanFolderForCSVFiles(file);
		
		context.close();
	}

	public static void scanFolderForCSVFiles(File file) {
		FileReader fileReader = null;
		File[] files = file.listFiles();

		for (File f : files) {
			if (f.isFile() && f.getAbsolutePath().toLowerCase().endsWith(".csv")) {
				try {
					System.out.println("Reading " + f.getName());
					fileReader = new FileReader(f.getAbsolutePath());
					MyCSVReader myCSVReader = (MyCSVReader) context.getBean("myCSVReader");
					myCSVReader.readCSVFile(fileReader);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			} else if (f.isDirectory()) {
				scanFolderForCSVFiles(file);
			}
		}

	}

}
