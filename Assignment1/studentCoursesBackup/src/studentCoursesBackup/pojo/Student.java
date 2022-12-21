package studentCoursesBackup.pojo;

public class Student {
	// pojo class of Student with instance variables and getters setters
	private int studentId;
	private String courseList;

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public String getCourseList() {
		return courseList;
	}

	public void setCourseList(String courseList) {
		this.courseList = courseList;
	}

	@Override
	public String toString() {
		return "Student [studentId=" + studentId + ", courseList=" + courseList + "]";
	}

}
