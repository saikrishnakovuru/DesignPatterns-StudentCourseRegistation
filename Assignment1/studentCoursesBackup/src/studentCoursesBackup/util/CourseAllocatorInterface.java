package studentCoursesBackup.util;

import java.util.List;

import studentCoursesBackup.pojo.Course;
import studentCoursesBackup.pojo.Student;

public interface CourseAllocatorInterface {

	public void createCourseAllocater(List<Student> studentPreferencesList, List<Course> courseInfoList);

	public float getAverageSatisfactionRating();

	List<String> getAllotedCourses();

}
