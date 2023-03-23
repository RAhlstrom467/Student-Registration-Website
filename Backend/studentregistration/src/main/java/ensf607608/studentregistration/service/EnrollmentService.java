package ensf607608.studentregistration.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ensf607608.studentregistration.model.Course;
import ensf607608.studentregistration.model.Enrollment;
import ensf607608.studentregistration.model.Section;
import ensf607608.studentregistration.model.Student;
import ensf607608.studentregistration.repository.EnrollmentRepository;

@Service
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;

    @Autowired
    public EnrollmentService(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    public Enrollment enrollStudentInCourse(String courseName, int sectionNumber, String ucid) {
        Student student = enrollmentRepository.getStudenByUcid(ucid);
        if (student == null) {
            return null;
        }

        Course courseToEnroll = enrollmentRepository.getCourseByName(courseName);
        if (courseToEnroll != null) {
            courseToEnroll.setPreReqs(enrollmentRepository.getCoursePreReqs(courseToEnroll));
            courseToEnroll.setSectionList(enrollmentRepository.getCourseSections(courseToEnroll));
        }
        if (courseToEnroll == null) {
            return null;
        }

        student.setCourseList(enrollmentRepository.getEnrolledCourseList(ucid));
        if (student.getEnrolledCourseList().size() >= 5) {
            return null;
        }

        List<Course> coursePreReqs = enrollmentRepository.getCoursePreReqs(courseToEnroll);
        List<Course> studentCompletedCourses = enrollmentRepository.getStudentCompletedCourses(ucid);

        boolean flag = false;
        if (coursePreReqs.isEmpty()) {
            flag = true;
        } else {
            for (Course c : studentCompletedCourses) {
                if (coursePreReqs.contains(c)) {
                    flag = true;
                }
            }
        }

        if (!flag) {
            return null;
        }

        Section sectionToEnroll = null;
        List<Section> courseSections = enrollmentRepository.getCourseSections(courseToEnroll);
        if (courseSections.isEmpty()) {
            return null;
        } else {
            flag = false;
            for (Section s : courseSections) {
                if (s.getSectionNumber() == sectionNumber) {
                    sectionToEnroll = s;
                    sectionToEnroll.setCourse(courseToEnroll);
                    flag = true;
                }
            }
        }

        if (!flag) {
            return null;
        }

        for (Enrollment enrollment : student.getEnrolledCourseList()) {
            if (enrollment.getSection().getCourse().equals(courseToEnroll)) {
                return null;
            }
        }

        boolean result = this.enrollmentRepository.enrollStudentInCourse(courseName, sectionNumber, ucid, "");
        if (result && flag && sectionToEnroll != null) {
            return new Enrollment(sectionToEnroll, student);
        } else {
            return null;
        }
    }

    public Enrollment unenrollStudentInCourse(String courseName, String ucid) {

        boolean registrationFlag = false;

        Student student = enrollmentRepository.getStudenByUcid(ucid);
        if (student == null) {
            return null;
        }

        Course courseToUnenroll = enrollmentRepository.getCourseByName(courseName);
        if (courseToUnenroll == null) {
            return null;
        }

        student.setCourseList(enrollmentRepository.getEnrolledCourseList(ucid));

        Enrollment enrollmentToRemove = null;
        for (Enrollment enrolledCourse : student.getEnrolledCourseList()) {
            if (enrolledCourse.getSection().getCourse().equals(courseToUnenroll)) {
                enrollmentToRemove = enrolledCourse;
                registrationFlag = true;
                break;
            }
        }

        if (!registrationFlag) {
            return null;
        }

        boolean result = this.enrollmentRepository.unenrollStudentInCourse(courseName, ucid);
        if (result && registrationFlag && enrollmentToRemove != null) {
            return enrollmentToRemove;
        } else {
            return null;
        }
    }
}
