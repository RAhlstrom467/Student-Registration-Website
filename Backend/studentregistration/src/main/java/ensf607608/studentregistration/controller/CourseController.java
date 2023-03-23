package ensf607608.studentregistration.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ensf607608.studentregistration.model.Course;
import ensf607608.studentregistration.service.CourseService;

@RestController
@RequestMapping("/courses")
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
public class CourseController {

    @Autowired
    CourseService courseService;

    @GetMapping("/allcourses")
    public List<Course> getAllCourses() {
        return this.courseService.getAllCourses();
    }

    @GetMapping("/allcoursesinorder")
    public List<Course> getAllCourseInOrder() {
        return this.courseService.getAllCoursesInOrder();
    }

    @GetMapping("/find/{courseName}")
    public Course getCourseByName(@PathVariable("courseName") String courseName) {
        return this.courseService.getCourseByName(courseName);
    }

    @PostMapping("/addcourse")
    public Course addNewCourse(@RequestBody Course course) {
        return this.courseService.addNewCourse(course);
    }

    @PutMapping("/updatecourse/{newCourseName}/{oldCourseName}")
    public Course updateCurrentCourse(@PathVariable("newCourseName") String newCourseName,
            @PathVariable("oldCourseName") String oldCourseName) {
        Course updateCourse = this.courseService.updateCourse(newCourseName, oldCourseName);
        return updateCourse;
    }

    @DeleteMapping("deletecourse")
    public Course deleteCourse(@RequestBody Course course) {
        return this.courseService.deleteCourse(course);
    }
}
