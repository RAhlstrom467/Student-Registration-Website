package ensf607608.studentregistration.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ensf607608.studentregistration.model.Section;
import ensf607608.studentregistration.model.Student;
import ensf607608.studentregistration.repository.StudentRepository;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student addNewStudent(Student student) {
        Student studentToAdd = studentRepository.addNewStudent(student);
        if (studentToAdd == null) {
            return null;
        }
        return studentToAdd;
    }

    public List<Section> getStudentsEnrolledSection(String ucid) {
        Student student = this.studentRepository.getStudenByUcid(ucid);
        if (student == null) {
            return null;
        } else {
            return this.studentRepository.getEnrolledSectionList(ucid);
        }

    }

}
