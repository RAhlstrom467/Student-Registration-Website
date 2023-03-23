package ensf607608.studentregistration.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ensf607608.studentregistration.model.Course;
import ensf607608.studentregistration.repository.CourseRepository;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> getAllCourses() {
        List<Course> courseCat = this.courseRepository.getAllCourses();
        for (Course c : courseCat) {
            c.setPreReqs(this.courseRepository.getCoursePreReqs(c));
            c.setSectionList(this.courseRepository.getCourseSections(c));
        }
        return courseCat;
    }

    public List<Course> getAllCoursesInOrder() {
        List<Course> courseCat = this.courseRepository.getAllCoursesInOrder();
        for (Course c : courseCat) {
            c.setPreReqs(this.courseRepository.getCoursePreReqs(c));
            c.setSectionList(this.courseRepository.getCourseSections(c));
        }
        return courseCat;
    }

    public Course getCourseByName(String courseName) {
        Course course = this.courseRepository.getCourseByName(courseName);
        if (course != null) {
            course.setPreReqs(courseRepository.getCoursePreReqs(course));
            course.setSectionList(courseRepository.getCourseSections(course));
        } else {
            return null;
        }
        return course;
    }

    public Course addNewCourse(Course course) {
        Course courseToAdd = this.courseRepository.addNewCourse(course);
        if (courseToAdd == null) {
            return null;
        }
        return courseToAdd;
    }

    public Course updateCourse(String newCourseName, String oldCourseName) {
        Course courseToUpdate = this.courseRepository.updateCourse(newCourseName, oldCourseName);
        if (courseToUpdate == null) {
            return null;
        }
        return courseToUpdate;
    }

    public Course deleteCourse(Course course) {
        Course courseToDelete = this.courseRepository.deleteCourse(course);
        if (courseToDelete == null) {
            return null;
        }
        return courseToDelete;
    }
}
