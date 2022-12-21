package studentCoursesBackup.util;

import java.util.List;

import studentCoursesBackup.pojo.Course;
import studentCoursesBackup.pojo.Student;

public interface FileProcessorInterface {
	public List<Student> readStudentCoursePreferences(String coursePreferences);

	public List<Course> readCourseInfo(String courseInfo);
}
