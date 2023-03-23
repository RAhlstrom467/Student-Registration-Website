package ensf607608.studentregistration.model;

import java.util.ArrayList;
import java.util.List;

public class Section {

    private int sectionNumber;
    private int capacity;
    private Course course;
    private List<Enrollment> enrollmentList;

    public Section() {
    }

    public Section(int sectionNumber, int capacity) {
        this.sectionNumber = sectionNumber;
        this.capacity = capacity;
        this.enrollmentList = new ArrayList<>();
    }

    public int getSectionNumber() {
        return sectionNumber;
    }

    public void setSectionNumber(int sectionNumber) {
        this.sectionNumber = sectionNumber;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public List<Enrollment> getEnrollmentList() {
        return enrollmentList;
    }

    public void setEnrollmentList(List<Enrollment> enrollmentList) {
        this.enrollmentList = enrollmentList;
    }

    public boolean sectionValid() {
        if (this.enrollmentList.size() >= 8) {
            return true;
        } else {
            return false;
        }
    }

    public void removeStudent(Enrollment e) {
        this.enrollmentList.remove(e);
    }

}
