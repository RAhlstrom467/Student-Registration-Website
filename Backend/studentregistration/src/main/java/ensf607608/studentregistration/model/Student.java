package ensf607608.studentregistration.model;

import java.util.ArrayList;
import java.util.List;

public class Student {

    private String studentUcid;
    private String studentFirstName;
    private String studentLastName;
    private List<Enrollment> enrolledCourseList;

    public Student() {
    }

    public Student(String studentUcid, String studentFirstName, String studentLastName) {
        this.studentUcid = studentUcid;
        this.studentFirstName = studentFirstName;
        this.studentLastName = studentLastName;
        this.enrolledCourseList = new ArrayList<>();
    }

    public String getStudentUcid() {
        return studentUcid;
    }

    public void setStudentUcid(String studentUcid) {
        this.studentUcid = studentUcid;
    }

    public String getStudentFirstName() {
        return studentFirstName;
    }

    public void setStudentFirstName(String studentFirstName) {
        this.studentFirstName = studentFirstName;
    }

    public String getStudentLastName() {
        return studentLastName;
    }

    public void setStudentLastName(String studentLastName) {
        this.studentLastName = studentLastName;
    }

    public List<Enrollment> getEnrolledCourseList() {
        return enrolledCourseList;
    }

    public void setCourseList(List<Enrollment> enrolledCourseList) {
        this.enrolledCourseList = enrolledCourseList;
    }

    public void addCourse(Enrollment e) {
        enrolledCourseList.add(e);
    }

    public void removeCourse(Enrollment e) {
        enrolledCourseList.remove(e);
    }

}
