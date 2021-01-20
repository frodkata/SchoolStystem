package ElektronenDnevnik.services;

import ElektronenDnevnik.entities.Course;
import ElektronenDnevnik.entities.Grades;
import ElektronenDnevnik.entities.Student;
import ElektronenDnevnik.repositories.GradesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GradesServiceImpl implements GradesService {

    @Autowired
    GradesRepository gradesRepository;

    @Override
    public List<Grades> getAll() {
        return gradesRepository.findAll();
    }

    @Override
    public void saveGrade(Grades grades) {
        gradesRepository.save(grades);
    }

    @Override
    public Grades getGradeById(Long id) {
        Optional<Grades> optional = gradesRepository.findById(id);
        Grades grades = null;
        if (optional.isPresent()) {
            grades = optional.get();
        } else {
            throw new RuntimeException(" Grade not found for id :: " + id);
        }
        return grades;
    }


    @Override
    public void deleteGradeById(Long id) {
        gradesRepository.deleteById(id);
    }

    @Override
    //Delete all grades for given Student
    public void deleteGradesForStudent(Student s) {
        for(Grades g : gradesRepository.findAll()){
            if(g.getStudent().getId().equals(s.getId())){ //Check if Grade belongs to Student
                gradesRepository.deleteById(g.getId());
            }
        }
    }

    @Override
    //Return grades made for given Course
    public List<Grades> getGradesForStudent(Student s, Course course) {
        List<Grades> gradesByCourse = new ArrayList<>();
        for (Grades g : gradesRepository.findAll()) {
            //Match Grades to Student by ID && Match grades to Course
            if (g.getStudent().getId().equals(s.getId()) && g.getCourse().equals(course)) {
                gradesByCourse.add(g);
            }
        }
        return gradesByCourse;
    }

}
