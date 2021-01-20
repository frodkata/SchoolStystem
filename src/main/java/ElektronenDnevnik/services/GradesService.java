package ElektronenDnevnik.services;

import ElektronenDnevnik.entities.Course;
import ElektronenDnevnik.entities.Grades;
import ElektronenDnevnik.entities.Student;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GradesService {
    List<Grades> getAll();
    void saveGrade(Grades grades);
    Grades getGradeById(Long id);
    void deleteGradeById(Long id);
    void deleteGradesForStudent(Student s);
    List<Grades> getGradesForStudent(Student s, Course course);


}
