package ensf607608.studentregistration.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import ensf607608.studentregistration.model.Course;
import ensf607608.studentregistration.model.Section;
import ensf607608.studentregistration.model.Student;

@Repository
public class StudentRepository extends DataBaseConnection {

    public Student addNewStudent(Student student) {
        Connection con = this.getDatabaseConnection();
        Student studentOut = null;
        try {
            PreparedStatement stmt = con.prepareStatement("select * from students as s where s.student_ucid = (?)");
            stmt.setString(1, student.getStudentUcid());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return studentOut;
            } else {
                studentOut = student;
            }
            stmt = con
                    .prepareStatement(
                            "insert into students (student_ucid, student_first_name, student_last_name) values (?, ?, ?)");
            stmt.setString(1, student.getStudentUcid());
            stmt.setString(2, student.getStudentFirstName());
            stmt.setString(3, student.getStudentLastName());
            stmt.executeUpdate();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return studentOut;
    }

    public List<Section> getEnrolledSectionList(String ucid) {
        Connection con = this.getDatabaseConnection();
        List<Section> enrolledSections = new ArrayList<>();
        Course course = null;
        Section section = null;
        try {
            PreparedStatement stmt = con.prepareStatement("select * from students as st where st.student_ucid = (?)");
            stmt.setString(1, ucid);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                throw new IllegalStateException("student doesn't exist!");
            }
            stmt = con.prepareStatement(
                    """
                            select c.course_name, c.course_start_date, c.course_end_date, s.section_number, s.capacity, e.grade
                            from sections s
                            inner join enrollments e on s.course_name = e.course_name
                            and s.section_number = e.section_number
                            and e.student_ucid = (?)
                            and e.grade = (?)
                            inner join courses c on c.course_name = s.course_name
                            inner join students st on st.student_ucid = e.student_ucid
                            """);
            stmt.setString(1, ucid);
            stmt.setString(2, "");
            rs = stmt.executeQuery();
            while (rs.next()) {
                course = new Course(rs.getString("course_name"), rs.getDate("course_start_date"),
                        rs.getDate("course_end_date"));
                section = new Section(rs.getInt("section_number"), rs.getInt("capacity"));
                section.setCourse(course);
                enrolledSections.add(section);
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return enrolledSections;
    }

    public Student getStudenByUcid(String ucid) {
        Connection con = this.getDatabaseConnection();
        Student student = null;
        try {
            PreparedStatement stmt1 = con.prepareStatement("select * from students as s where s.student_ucid = (?)");
            stmt1.setString(1, ucid);
            ResultSet rs = stmt1.executeQuery();
            if (rs.next()) {
                student = new Student(rs.getString("student_ucid"), rs.getString("student_first_name"),
                        rs.getString("student_last_name"));
            } else {
                throw new IllegalStateException("student doesn't exist!");
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return student;
    }

}
