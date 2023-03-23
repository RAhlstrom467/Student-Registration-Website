package ensf607608.studentregistration.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import ensf607608.studentregistration.model.Course;
import ensf607608.studentregistration.model.Enrollment;
import ensf607608.studentregistration.model.Section;
import ensf607608.studentregistration.model.Student;

@Repository
public class EnrollmentRepository extends DataBaseConnection {

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

    public Course getCourseByName(String courseName) {
        Connection con = this.getDatabaseConnection();
        Course course = null;
        try {
            PreparedStatement stmt = con.prepareStatement(
                    "select * from courses as c where c.course_name = (?)");
            stmt.setString(1, courseName);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                course = new Course(rs.getString("course_name"), rs.getDate("course_start_date"),
                        rs.getDate("course_end_date"));
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return course;
    }

    public List<Enrollment> getEnrolledCourseList(String ucid) {
        Connection con = this.getDatabaseConnection();
        List<Enrollment> enrolledCourses = new ArrayList<>();
        Student student = null;
        Course course = null;
        Section section = null;
        try {
            PreparedStatement stmt = con.prepareStatement("select * from students as st where st.student_ucid = (?)");
            stmt.setString(1, ucid);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                throw new IllegalStateException("student doesn't exist!");
            }

            student = new Student(rs.getString("student_ucid"), rs.getString("student_first_name"),
                    rs.getString("student_last_name"));

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
                enrolledCourses.add(new Enrollment(section, student));
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return enrolledCourses;
    }

    public List<Course> getStudentCompletedCourses(String ucid) {
        Connection con = this.getDatabaseConnection();
        List<Course> courseList = new ArrayList<>();
        try {
            PreparedStatement stmt = con.prepareStatement("select * from students as st where st.student_ucid = (?)");
            stmt.setString(1, ucid);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                throw new IllegalStateException("student doesn't exist!");
            }
            stmt = con.prepareStatement(
                    """
                            SELECT st.student_first_name, st.student_last_name, c.course_name, c.course_start_date, c.course_end_date
                            FROM SECTIONs s
                            INNER JOIN ENROLLMENTs e
                            ON s.course_name = e.course_name
                            AND s.section_number = e.section_number
                            AND e.student_ucid = (?)
                            INNER JOIN COURSEs c
                            ON c.course_name = s.course_name
                            INNER JOIN STUDENTs st
                            ON st.student_ucid = e.student_ucid
                            where e.grade != (?)
                            """);
            stmt.setString(1, ucid);
            stmt.setString(2, "");
            rs = stmt.executeQuery();
            while (rs.next()) {
                courseList.add(new Course(rs.getString("course_name"), rs.getDate("course_start_date"),
                        rs.getDate("course_end_date")));
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return courseList;
    }

    public List<Section> getCourseSections(Course course) {
        Connection con = this.getDatabaseConnection();
        List<Section> sections = new ArrayList<>();
        try {
            PreparedStatement stmt = con.prepareStatement(
                    """
                            select s.section_number, s.capacity
                            from sections s
                            inner join courses c on s.course_name = c.course_name
                            and c.course_name = (?)
                            """);
            stmt.setString(1, course.getName());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                sections.add(new Section(rs.getInt("section_number"), rs.getInt("capacity")));
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return sections;
    }

    public List<Course> getCoursePreReqs(Course course) {
        Connection con = this.getDatabaseConnection();
        List<Course> preReqs = new ArrayList<>();
        try {
            PreparedStatement stmt = con.prepareStatement(
                    """
                            select pc.course_name, pc.course_start_date, pc.course_end_date
                            from courses c 
                            inner join prereqs p on c.course_name=p.course_name
                            inner join Courses pc on p.prereq_name=pc.course_name where c.course_name = (?)
                            """);
            stmt.setString(1, course.getName());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                preReqs.add(new Course(rs.getString("course_name"), rs.getDate("course_start_date"),
                        rs.getDate("course_end_date")));
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return preReqs;
    }

    public boolean enrollStudentInCourse(String courseName, int sectionNumber, String ucid, String grade) {
        Connection con = this.getDatabaseConnection();
        boolean flag = false;
        try {
            PreparedStatement stmt = con
                    .prepareStatement(
                            "insert into enrollments (course_name, section_number, student_ucid, grade) values (?, ?, ?, ?)");
            stmt.setString(1, courseName);
            stmt.setInt(2, sectionNumber);
            stmt.setString(3, ucid);
            stmt.setString(4, grade);
            stmt.executeUpdate();
            flag = true;
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return flag;
    }

    public boolean unenrollStudentInCourse(String courseName, String ucid) {
        Connection con = this.getDatabaseConnection();
        boolean flag = false;
        try {
            PreparedStatement stmt = con.prepareStatement(
                    "delete from enrollments e where e.course_name = (?) and e.student_ucid = (?)");
            stmt.setString(1, courseName);
            stmt.setString(2, ucid);
            stmt.executeUpdate();
            flag = true;
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return flag;
    }

}
