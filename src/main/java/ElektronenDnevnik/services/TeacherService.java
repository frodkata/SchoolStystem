package ElektronenDnevnik.services;

import ElektronenDnevnik.entities.Course;
import ElektronenDnevnik.entities.Teacher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TeacherService {
    List<Teacher> getAll();
    void saveTeacher(Teacher teacher);
    Teacher getTeacherById(long id);
    void deleteTeacherById(long id);
    Teacher getTeacherByCourse(Course course);
    Teacher getTeacherByUserId(Long id);
}
