package ensf607608.studentregistration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ensf607608.studentregistration.model.Enrollment;
import ensf607608.studentregistration.service.EnrollmentService;

@RestController
@RequestMapping("/enrollments")
@CrossOrigin
public class EnrollmentController {

    @Autowired
    EnrollmentService enrollmentService;

    @PostMapping("/register/{courseName}/{sectionNumber}/{ucid}")
    public Enrollment studentEnrollInCourse(@PathVariable("courseName") String courseName,
            @PathVariable("sectionNumber") int sectionNumber,
            @PathVariable("ucid") String ucid) {
        return this.enrollmentService.enrollStudentInCourse(courseName, sectionNumber, ucid);
    }

    @DeleteMapping("/delete/{courseName}/{ucid}")
    public Enrollment deleteEmployeeById(@PathVariable("courseName") String courseName, @PathVariable("ucid") String ucid) {
        return this.enrollmentService.unenrollStudentInCourse(courseName, ucid);

    }
}
