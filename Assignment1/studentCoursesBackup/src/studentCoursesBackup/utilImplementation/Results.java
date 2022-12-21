package studentCoursesBackup.utilImplementation;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import studentCoursesBackup.util.FileDisplayInterface;
import studentCoursesBackup.util.StdoutDisplayInterface;

public class Results implements FileDisplayInterface, StdoutDisplayInterface {

	private FileWriter fileWriter;
	private String regResults;

	public Results(String results) {
		regResults = results;
	}

	@Override
	public void displayResultsInConsole(List<String> results, float averageSatisfactionRating) {
		/*
		 * checking for the empty list, the list will be empty when the there is any
		 * error in the input there won't be any data in the list and the
		 * "averageSatisfactionRating" will be printed '0' on console which is weird.
		 */
		if (!results.isEmpty() && averageSatisfactionRating != 0) {
			for (String res : results) {
				System.out.println(res + "\n");
			}
			System.out.println("AverageSatisfactionRating = " + averageSatisfactionRating);
		}
	}

	@Override
	public void writeResultsToOutputFile(List<String> results, float averageSatisfactionRating) {
		if (!results.isEmpty() && averageSatisfactionRating != 0)
			try {
				fileWriter = new FileWriter(regResults);
				for (String res : results) {
					fileWriter.write(res + "\n");
				}
				fileWriter.write("AverageSatisfactionRating= " + (double) averageSatisfactionRating);
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {

			}
	}
}
