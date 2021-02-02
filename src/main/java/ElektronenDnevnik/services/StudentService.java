package ElektronenDnevnik.services;

import ElektronenDnevnik.entities.Course;
import ElektronenDnevnik.entities.Grades;
import ElektronenDnevnik.entities.Parent;
import ElektronenDnevnik.entities.Student;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StudentService {
     List<Student> getAll();
    void saveStudent(Student student);
    Student getStudentById(Long id);
    void deleteStudentById(Long id);
    List<Student> getStudentsByYear(int year);

}
