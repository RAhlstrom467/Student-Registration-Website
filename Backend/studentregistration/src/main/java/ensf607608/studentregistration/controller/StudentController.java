package ensf607608.studentregistration.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ensf607608.studentregistration.model.Section;
import ensf607608.studentregistration.model.Student;
import ensf607608.studentregistration.service.StudentService;

@RestController
@RequestMapping("/students")
@CrossOrigin
public class StudentController {

    @Autowired
    StudentService studentService;

    @PostMapping("/add")
    public Student registerNewStudent(@RequestBody Student student) {
        return this.studentService.addNewStudent(student);
    }

    @GetMapping("/findcourses/{ucid}")
    public List<Section> getStudentsEnrolledCourses(@PathVariable("ucid") String ucid) {
        return this.studentService.getStudentsEnrolledSection(ucid);
    }

}
