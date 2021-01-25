package ElektronenDnevnik.services;

import ElektronenDnevnik.entities.Course;
import ElektronenDnevnik.entities.Teacher;
import ElektronenDnevnik.repositories.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    TeacherRepository teacherRepository;

    @Override
    public List<Teacher> getAll() {
        return teacherRepository.findAll();
    }

    @Override
    public void saveTeacher(Teacher teacher) {
        teacherRepository.save(teacher);
    }

    @Override
    public Teacher getTeacherById(long id) {
        Optional<Teacher> optional = teacherRepository.findById(id);
        Teacher teacher = null;
        if (optional.isPresent()) {
            teacher = optional.get();
        } else {
            throw new RuntimeException(" Teacher not found for id :: " + id);
        }
        return teacher;
    }

    @Override
    public void deleteTeacherById(long id) {
        teacherRepository.deleteById(id);
    }


    @Override
    public Teacher getTeacherByCourse(Course course) {
        for (Teacher t : teacherRepository.findAll()) {
            if(t.getCourse().equals(course)){
                return t;
            }
        }

        throw new RuntimeException("Student course not found for name ::" + course);
    }

    @Override
    public Teacher getTeacherByUserId(Long id) {
        for (Teacher t : teacherRepository.findAll()) {
            if(t.getUserProfile().getId().equals(id)){
                return t;
            }
        }

        throw new RuntimeException(" Teacher  not found for user id :: " + id);

    }
}
