package studentCoursesBackup.utilImplementation;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import studentCoursesBackup.pojo.Course;
import studentCoursesBackup.pojo.Student;
import studentCoursesBackup.util.CourseAllocatorInterface;

public class CourseAllocatorImpl implements CourseAllocatorInterface {

	private List<Student> studentPreferencesList;
	private List<Course> courseInfoList;
	private Set<Integer> courseTimings;
	private List<String> allotCourses;
	private float totalSatisfactionRating;
	private float averageSatisfactionRating;
	private String result;
	private boolean flag = true;
	private FileWriter errorLog;
	private FileWriter conflicts;

	public CourseAllocatorImpl(FileWriter error, FileWriter conflict) {
		/*
		 * passed the file writer object right away from the driver class because every
		 * time I tried creating a new instance to the FileWriter in the methods
		 * "logErrorsInCourseInfoFileInput" and "logErrorsInCoursePreferencesFileInput"
		 * some how the file writer is being overridden and the data will never appear
		 * on the "errorLog" file "THIS WAS A NIGHTMARE ATLEAST FOR ME". So instead of
		 * creating the instances whenever we need, I passes the single instance for the
		 * entire class.
		 */
		errorLog = error;
		conflicts = conflict;
		// used set interface to identify the time clashes
		courseTimings = new HashSet<Integer>();
		allotCourses = new ArrayList<String>();
	}

	@Override
	public void createCourseAllocater(List<Student> studentPreferencesList, List<Course> courseInfoList) {
		/*
		 * making the "studentPreferencesList" and "courseInfoList" as global variables
		 * so that we can use those through out the class, used setter way of injection.
		 */
		setStudentPreferencesList(studentPreferencesList);
		setCourseList(courseInfoList);

		try {

			for (int courseInfoCount = 0; courseInfoCount < courseInfoList.size(); courseInfoCount++) {
				logErrorsInCourseInfoFileInput(courseInfoCount);
			}
			/*
			 * tried doing the check with while loop and it has become infinite loop as the
			 * data is not null so the further steps in the while loop keeps on executing,
			 * which made me to use the for loop again as it has the count which helps me in
			 * getting id of a student.
			 */
			for (int preferencesListCount = 0; preferencesListCount < studentPreferencesList
					.size(); preferencesListCount++) {
				int lengthOfPreferedCourses = studentPreferencesList.get(preferencesListCount).getCourseList().length();
				result = null;
				int studentId = 0;
				String[] coursesAssigned = new String[3];
				int counterToCourseAssigned = 0;

				logErrorsInCoursePreferencesFileInput(preferencesListCount, lengthOfPreferedCourses);

				if (flag)
					/*
					 * flag will be set to false when there is any error in the input and if exists
					 * the courseAllocator should not work.
					 */
					allocateCourses(lengthOfPreferedCourses, courseInfoList, preferencesListCount, result, studentId,
							coursesAssigned, counterToCourseAssigned);
				else {
					System.out.println(
							"There must be an error in the input files -> courseInfo or coursePrefs, look into errorLog file for the error");
					break;
				}

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
		}

	}

	private void allocateCourses(int lengthOfPreferedCourses, List<Course> courseInfoList, int preferencesListCount,
			String result, int studentId, String[] coursesAssigned, int counterToCourseAssigned) {
		float satisfaction = 0;

		// checking with each student prefered courses with all the courses in
		// courseInfo list
		for (int preferencesCount = 0; preferencesCount < lengthOfPreferedCourses; preferencesCount++) {

			// incremetns and move to the next courseinfo in every iteration
			for (int courseInfoCount = 0; courseInfoCount < courseInfoList.size(); courseInfoCount++) {

				studentId = studentPreferencesList.get(preferencesListCount).getStudentId();
				result = studentId + "";
				String studentPreferedCourse = String.valueOf(
						studentPreferencesList.get(preferencesListCount).getCourseList().charAt(preferencesCount));
				String courseNameInCourseInfoList = courseInfoList.get(courseInfoCount).getCourseName();
				int courseCapasity = courseInfoList.get(courseInfoCount).getCourseCapacity();

				if (studentPreferedCourse.equals(courseNameInCourseInfoList) && courseCapasity > 0
						&& counterToCourseAssigned < 3) {

					// if condition to check weather any course, time is overlapping.
					if (!courseTimings.contains(courseInfoList.get(courseInfoCount).getCourseTiming())) {

						coursesAssigned[counterToCourseAssigned] = courseInfoList.get(courseInfoCount).getCourseName();

						courseTimings.add(courseInfoList.get(courseInfoCount).getCourseTiming());

						int capacity = courseInfoList.get(courseInfoCount).getCourseCapacity();
						// dicrementing the course capacity
						courseInfoList.get(courseInfoCount).setCourseCapacity(--capacity);
						/*
						 * while preferences count goes up, that means these may times the loop has been
						 * executed, we will be only allowed into this if loop only when we don't have
						 * time clash, so the satisfaction rating goes down based on no. of times the
						 * same course has been checked with other courses with out time clash
						 */
						satisfaction += 9 - preferencesCount;

						for (String a : coursesAssigned) {
							if (a != null)
								counterToCourseAssigned++;
							break;
						}
					} else {
						/*
						 * else will be only called when conflict exist between 2 courses and logging
						 * the conflicts in the conflicts file.
						 */
						try {
							String conflictedCourseName = courseInfoList.get(courseInfoCount).getCourseName();
							int conflictedCourseTiming = courseInfoList.get(courseInfoCount).getCourseTiming();
							conflicts.write("conflicted course is " + conflictedCourseName + ", and the time "
									+ conflictedCourseTiming + " is overlapping to another course" + "\n");
							conflicts.flush();
							System.out.println("conflicted course is" + conflictedCourseName + ", and the time "
									+ conflictedCourseTiming + " is overlaping to another course");
						} catch (IOException e) {
							e.printStackTrace();
						} finally {

						}

					}
				}
			}

		}

		// using the courseAssigned would return the Object value
		result = result + ": " + String.join(",", coursesAssigned) + "::" + satisfaction / coursesAssigned.length;
		totalSatisfactionRating = totalSatisfactionRating + satisfaction / coursesAssigned.length;

		// in every iteration "counterToCourseAssigned" should be 0 because none of the
		// courses were been alloted to new student
		counterToCourseAssigned = 0;

		/*
		 * I got stuck here for almost a day with out clearing the courseTimings for the
		 * further iterations.
		 */
		courseTimings.clear();

		allotCourses.add(result);

	}

	private void logErrorsInCourseInfoFileInput(int courseInfoCount) {
		try {
			String courseName = courseInfoList.get(courseInfoCount).getCourseName();
			int courseCapacity = courseInfoList.get(courseInfoCount).getCourseCapacity();
			int courseTimings = courseInfoList.get(courseInfoCount).getCourseTiming();

			// capacity can never be 0
			if (courseCapacity == 0) {
				errorLog.write(
						"The course " + courseName + " has the in compatible capacity where it can never be 0" + "\n");
				flag = false;
				errorLog.flush();
			}
			// course timings should be between 1 to 9
			if (courseTimings > 9 || courseTimings == 0) {
				errorLog.write(
						"The course timing of " + courseName + " should be in between 1-9 also can't be 0" + "\n");
				flag = false;
				errorLog.flush();
			}
			// course name can never be an integer, used rejex to identify if course name
			// given is an integer
			if (courseName.matches("-?(0|[1-9]\\d*)")) {
				errorLog.write("Course name:" + courseName + " should always be of string type" + "\n");
				flag = false;
				errorLog.flush();
			}

			/*
			 * flag will be set to false if there is any single error in the input either in
			 * studentPrefs or coursePrefs, the course allocator should not work. so, before
			 * entering into course allocator flag will be checked and later the main logic
			 * runs.
			 */

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}

	}

	private void logErrorsInCoursePreferencesFileInput(int preferencesListCount, int lengthOfPreferedCourses) {
		int studentId = studentPreferencesList.get(preferencesListCount).getStudentId();

		try {
			// preferred course should never be less than or greater than 9
			if (lengthOfPreferedCourses < 9 || lengthOfPreferedCourses > 9) {

				errorLog.write("Error at student Id:" + studentId
						+ "Student course preferences must not be lesser than or greater than 9" + "\n");
				flag = false;
				errorLog.flush();

			}
			// student Id should be between 100 - 999 and should be an integer
			else if (studentId < 100 || studentId > 999 || studentId != (int) studentId) {
				errorLog.write("Student Id given is:" + studentId
						+ " must be a three digit number between 100-999 and should be of type integer");
				flag = false;
				errorLog.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}

	}

	// setter injection of preferences list
	private void setStudentPreferencesList(List<Student> preferencesList) {
		studentPreferencesList = preferencesList;
	}

	// setter injection of courseInfoLists
	private void setCourseList(List<Course> courses) {
		courseInfoList = courses;
	}

	@Override
	public float getAverageSatisfactionRating() {
		averageSatisfactionRating = (totalSatisfactionRating / studentPreferencesList.size());
		return averageSatisfactionRating;
	}

	@Override
	public List<String> getAllotedCourses() {
		return allotCourses;
	}

	@Override
	public String toString() {
		return "CourseAllocatorImpl [studentPreferencesList=" + studentPreferencesList + ", courseInfoList="
				+ courseInfoList + ", courseTimings=" + courseTimings + ", allotCourses=" + allotCourses
				+ ", totalSatisfactionRating=" + totalSatisfactionRating + ", averageSatisfactionRating="
				+ averageSatisfactionRating + ", result=" + result + "]";
	}

}
