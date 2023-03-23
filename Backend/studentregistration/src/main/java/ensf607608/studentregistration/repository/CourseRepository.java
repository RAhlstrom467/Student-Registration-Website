package ensf607608.studentregistration.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import ensf607608.studentregistration.model.Course;
import ensf607608.studentregistration.model.Section;

@Repository
public class CourseRepository extends DataBaseConnection {

    public List<Course> getAllCourses() {
        Connection con = getDatabaseConnection();
        List<Course> courseCat = new ArrayList<>();
        ResultSet rs;
        try {
            PreparedStatement stmt = con.prepareStatement("select * from courses");
            rs = stmt.executeQuery();
            while (rs.next()) {
                courseCat.add(new Course(rs.getString("course_name"), rs.getDate("course_start_date"),
                        rs.getDate("course_end_date")));
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return courseCat;
    }

    public List<Course> getAllCoursesInOrder() {
        Connection con = getDatabaseConnection();
        List<Course> courseCat = new ArrayList<>();
        ResultSet rs;
        try {
            PreparedStatement stmt = con.prepareStatement("select * from courses order by course_name asc");
            rs = stmt.executeQuery();
            while (rs.next()) {
                courseCat.add(new Course(rs.getString("course_name"), rs.getDate("course_start_date"),
                        rs.getDate("course_end_date")));
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return courseCat;
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

    public List<Section> getCourseSections(Course course) {
        Connection con = this.getDatabaseConnection();
        List<Section> sections = new ArrayList<>();
        try {
            PreparedStatement stmt = con.prepareStatement(
                    """
                            select s.section_number, s.capacity
                            from sections s inner join courses c on s.course_name = c.course_name
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
                            select pc.course_name, pc.course_start_date, pc.course_end_date from courses c
                            inner join prereqs p on c.course_name=p.course_name
                            inner join Courses pc on p.prereq_name=pc.course_name
                            where c.course_name = (?)
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

    public Course addNewCourse(Course course) {
        Connection con = this.getDatabaseConnection();
        Course courseOut = null;
        try {
            PreparedStatement stmt = con.prepareStatement("select * from courses as c where c.course_name = (?)");
            stmt.setString(1, course.getName());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return courseOut;
            } else {
                courseOut = course;
            }
            stmt = con
                    .prepareStatement(
                            "insert into courses (course_name, course_start_date, course_end_date) values (?, ?, ?)");
            stmt.setString(1, course.getName());
            stmt.setDate(2, course.getStartDate());
            stmt.setDate(3, course.getEndDate());
            stmt.executeUpdate();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return courseOut;
    }

    public Course updateCourse(String newCourseName, String oldCourseName) {
        Connection con = this.getDatabaseConnection();
        Course courseOut = null;
        try {
            PreparedStatement stmt = con.prepareStatement("select * from courses as c where c.course_name = (?)");
            stmt.setString(1, oldCourseName);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                return courseOut;
            } else {
                courseOut = new Course(newCourseName, rs.getDate("course_start_date"), rs.getDate("course_end_date"));
            }
            stmt = con
                    .prepareStatement(
                            "UPDATE courses SET course_name = (?) WHERE course_name = (?)");
            stmt.setString(1, newCourseName);
            stmt.setString(2, oldCourseName);
            stmt.executeUpdate();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return courseOut;
    }

    public Course deleteCourse(Course course) {
        Connection con = this.getDatabaseConnection();
        Course courseOut = null;
        try {
            PreparedStatement stmt = con.prepareStatement("select * from courses as c where c.course_name = (?)");
            stmt.setString(1, course.getName());
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                return courseOut;
            } else {
                courseOut = course;
            }
            stmt = con
                    .prepareStatement(
                            "DELETE FROM courses WHERE course_name=(?)");
            stmt.setString(1, course.getName());
            stmt.executeUpdate();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return courseOut;
    }

}
