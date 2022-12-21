package studentCoursesBackup.pojo;

public class Course {
	// pojo class of course with instance variables and getters setters
	private String courseName;
	private int courseCapacity;
	private int courseTiming;

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public int getCourseCapacity() {
		return courseCapacity;
	}

	public void setCourseCapacity(int courseCapacity) {
		this.courseCapacity = courseCapacity;
	}

	public int getCourseTiming() {
		return courseTiming;
	}

	public void setCourseTiming(int courseTiming) {
		this.courseTiming = courseTiming;
	}

	@Override
	public String toString() {
		return "Course [courseName=" + courseName + ", courseCapacity=" + courseCapacity + ", courseTiming="
				+ courseTiming + "]";
	}

}
