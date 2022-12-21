package studentCoursesBackup.utilImplementation;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import studentCoursesBackup.pojo.Course;
import studentCoursesBackup.pojo.Student;
import studentCoursesBackup.util.FileProcessorInterface;

public class FileProcessor implements FileProcessorInterface {

	private Student student;
	private Course course;
	private List<Student> studentList;
	private List<Course> courseList;
	private Scanner scanner;
	private FileWriter fileWriter;

	public FileProcessor(FileWriter writer) {
		fileWriter = writer;
		studentList = new ArrayList<Student>();
		courseList = new ArrayList<Course>();
	}

	@Override
	public List<Student> readStudentCoursePreferences(String coursePreferences) {
		try {
			scanner = new Scanner(new File(coursePreferences));

			while (scanner.hasNext()) {

				//checking if hte first value is an integer which is student id.
				if (scanner.hasNextInt()) {
					int studentId = scanner.nextInt();

					String studentPreferedCourses = scanner.nextLine();
					if (studentPreferedCourses.endsWith(";")) {
						studentPreferedCourses = studentPreferedCourses.substring(0,
								studentPreferedCourses.length() - 1);
						studentPreferedCourses = studentPreferedCourses.replaceAll("\\s", "");

						/*
						 * used the above line with regex because the courses are being counted as
						 * 18(counting spaces as well) but has to be 8 while writing the conditions in
						 * CourseAllocator class. so removed the empty spaces between values in the
						 * string
						 * 
						 */
					}

					/*
					 * new student object has to be created as while scanning the input each line
					 * has the details which belongs to a student.
					 */
					student = new Student();

					// setting studentId and preferedCourses.
					student.setStudentId(studentId);
					student.setCourseList(studentPreferedCourses);

					// adding student details into an arrayList one by one.
					studentList.add(student);

				} else {
					System.out.println(
							"There must be an error in the input files -> courseInfo or coursePrefs, look into errorLog file for the error");
					fileWriter.append("Student Id can never be a string always" + "\n");
					fileWriter.flush();

					/*
					 * If I don't set the scanner empty, scanning continues and become an infinite
					 * loop again as there is something in the input file to scan.
					 */
					scanner = new Scanner("");
				}
			}

		} catch (Exception ex) {

			ex.printStackTrace();

		} finally {
			// closing the scanner resource
			scanner.close();
		}
		// returning the whole studentList when "readStudentCoursePreferences" is
		// called.
		return studentList;

	}

	@Override
	public List<Course> readCourseInfo(String courseInfo) {
		try {
			scanner = new Scanner(new File(courseInfo));

			while (scanner.hasNext()) {
				// Splitting the array using ':' as the input is separated by ':'.
				String[] courseDetails = scanner.nextLine().split(":");
				String courseName = courseDetails[0];
				int courseCapacity = Integer.parseInt(courseDetails[1]);
				int courseTiming = Integer.parseInt(courseDetails[2]);

				/*
				 * new course object has to be created as while scanning the input each line has
				 * the details which belongs to a specific course
				 */
				course = new Course();
				// setting the course details.
				course.setCourseName(courseName);
				course.setCourseCapacity(courseCapacity);
				course.setCourseTiming(courseTiming);

				// adding the course details into an arrayList one by one
				courseList.add(course);

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			// closing the scanner resource.
			scanner.close();
		}
		// returning whole course list while "readCourseInfo" is called
		return courseList;

	}

	@Override
	public String toString() {
		return "FileProcessorImpl [student=" + student + ", course=" + course + ", studentList=" + studentList
				+ ", courseList=" + courseList + "]";
	}

}
