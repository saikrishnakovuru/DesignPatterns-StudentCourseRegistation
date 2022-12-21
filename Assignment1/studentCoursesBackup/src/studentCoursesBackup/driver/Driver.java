package studentCoursesBackup.driver;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import studentCoursesBackup.pojo.Course;
import studentCoursesBackup.pojo.Student;
import studentCoursesBackup.util.CourseAllocatorInterface;
import studentCoursesBackup.util.FileDisplayInterface;
import studentCoursesBackup.util.FileProcessorInterface;
import studentCoursesBackup.util.StdoutDisplayInterface;
import studentCoursesBackup.utilImplementation.CourseAllocatorImpl;
import studentCoursesBackup.utilImplementation.FileProcessor;
import studentCoursesBackup.utilImplementation.Results;

public class Driver {

	private String coursePrefs;
	private String coursesInfo;
	private List<Student> studentPreferencesList;
	private List<Course> courseList;
	private FileProcessorInterface fileProcessorI;
	private CourseAllocatorInterface courseAllocatorI;
	private FileDisplayInterface resultsToFileI;
	private StdoutDisplayInterface resultsToConsoleI;

	public Driver(FileProcessorInterface fileDataReaderI, CourseAllocatorInterface courseAllocator,
			FileDisplayInterface resultsToFile, StdoutDisplayInterface resultsToConsole, String coursePreferences,
			String courseInformation) {
		coursePrefs = coursePreferences;
		coursesInfo = courseInformation;
		fileProcessorI = fileDataReaderI;
		courseAllocatorI = courseAllocator;
		resultsToFileI = resultsToFile;
		resultsToConsoleI = resultsToConsole;

		studentPreferencesList = new ArrayList<Student>();
		courseList = new ArrayList<Course>();
		doStuff();
	}

	private void doStuff() {

		/*
		 * passing the file inputs and returning the parsed input data to
		 * studentPreferencesList and courseList.
		 */
		studentPreferencesList.addAll(fileProcessorI.readStudentCoursePreferences(coursePrefs));
		courseList.addAll(fileProcessorI.readCourseInfo(coursesInfo));
		courseAllocatorI.createCourseAllocater(studentPreferencesList, courseList);
		// writing results to output file.
		resultsToFileI.writeResultsToOutputFile(courseAllocatorI.getAllotedCourses(),
				courseAllocatorI.getAverageSatisfactionRating());
		// printing results in console.
		resultsToConsoleI.displayResultsInConsole(courseAllocatorI.getAllotedCourses(),
				courseAllocatorI.getAverageSatisfactionRating());

	}

	@Override
	public String toString() {
		return "Driver [coursePreferences=" + coursePrefs + ", courseInfo=" + coursesInfo
				+ ", studentPreferencesList=" + studentPreferencesList + ", courseList=" + courseList
				+ ", fileDataReaderI=" + fileProcessorI + ", courseAllocatorI=" + courseAllocatorI + ", resultsToFileI="
				+ resultsToFileI + ", resultsToConsoleI=" + resultsToConsoleI + "]";
	}

	public static void main(String[] args) {

		if (args.length != 5 || args[0].equals("${arg0}") || args[1].equals("${arg1}") || args[2].equals("${arg2}")
				|| args[3].equals("${arg3}") || args[4].equals("${arg4}")) {

			System.err.println("Error: Incorrect number of arguments. Program accepts 5 arguments.");
			System.exit(1);
		}
		// checking the order of input files so that the mismatch should not happen.
		// If any mismatch happens, printing that mismatch in the input occured.
		// order is conserned because the respective files are passed to respective
		// classes.
		if (args[0].equals("coursePrefs.txt") && args[1].equals("courseInfo.txt") && args[2].equals("regResults.txt")
				&& args[3].equals("regConflicts.txt") && args[4].equals("errorLog.txt")) {

			// if the input files are present but with no data then the application should
			// not work
			if (!args[0].isBlank() && !args[1].isBlank()) {

				try {
					FileWriter errorLog = new FileWriter(args[4]);
					FileWriter conflicts = new FileWriter(args[3]);
					FileProcessorInterface fileDataReaderI = new FileProcessor(errorLog);
					CourseAllocatorInterface courseAllocatorI = new CourseAllocatorImpl(errorLog, conflicts);
					FileDisplayInterface resultsToFileI = new Results(args[2]);
					StdoutDisplayInterface resultsToConsoleI = new Results(args[2]);
					new Driver(fileDataReaderI, courseAllocatorI, resultsToFileI, resultsToConsoleI, args[0], args[1]);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {

				}
			} else {
				// prints when coursePrefs or courseInfo file has no data
				System.err.println("Error: Either coursePrefs or courseInfo file has no data");
				System.exit(1);
			}
		} else {
			// prints while input files gets mismatched
			System.err.println("Error: input order mismatch");
			System.exit(1);
		}
	}
}
