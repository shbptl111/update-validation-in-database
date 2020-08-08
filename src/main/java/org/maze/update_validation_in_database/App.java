package org.maze.update_validation_in_database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
	
	static AnnotationConfigApplicationContext context;

	public static void main(String[] args) {

		long start = System.currentTimeMillis();
		
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
		long end = System.currentTimeMillis();
		System.out.println("Time required to complete program: " + 
				+ (TimeUnit.MILLISECONDS.toMinutes(end - start)) + " minutes "
				+ (TimeUnit.MILLISECONDS.toSeconds(end - start) % 60) + " seconds.");		
	}

	public static void scanFolderForCSVFiles(File file) {
		FileReader fileReader = null;
		File[] files = file.listFiles();

		for (File f : files) {
			if (f.isDirectory()) {
				scanFolderForCSVFiles(f);
			} else if (f.getName().toLowerCase().endsWith(".csv")) {
				try {
					System.out.println("Reading " + f.getPath());
					fileReader = new FileReader(f.getPath());
					MyCSVReader myCSVReader = (MyCSVReader) context.getBean("myCSVReader");
					myCSVReader.readCSVFile(fileReader);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		}

	}

}
