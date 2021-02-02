package ElektronenDnevnik.services;

import ElektronenDnevnik.entities.Course;
import ElektronenDnevnik.entities.Parent;
import ElektronenDnevnik.entities.Student;
import ElektronenDnevnik.entities.Teacher;
import ElektronenDnevnik.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service

public class StudentServiceImpl implements StudentService {

    @Autowired
    StudentRepository studentRepository;


    @Override
    public List<Student> getAll() {
        return studentRepository.findAll();
    }

    @Override
    public void saveStudent(Student student) {
        studentRepository.save(student);
    }

    @Override
    public Student getStudentById(Long id) {
        Optional<Student> optional = studentRepository.findById(id);
        Student student = null;
        if (optional.isPresent()) {
            student = optional.get();
        } else {
            throw new RuntimeException(" Student not found for id :: " + id);
        }
        return student;
    }

    @Override
    public void deleteStudentById(Long id) {
        studentRepository.deleteById(id);
    }

    @Override
    public List<Student> getStudentsByYear(int year) {
        List<Student> students = new ArrayList<>();
        for (Student s : studentRepository.findAll()) {
            if (year == s.getYear()) {
                students.add(s);
            }
        }

        return students;
    }


}

