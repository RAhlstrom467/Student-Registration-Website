package ensf607608.studentregistration.model;

public class Enrollment {

    private Section section;
    private Student student;
    private char grade;

    public Enrollment() {
    }

    public Enrollment(Section section, Student student) {
        this.section = section;
        this.student = student;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public char getGrade() {
        return grade;
    }

    public void setGrade(char grade) {
        this.grade = grade;
    }

}
