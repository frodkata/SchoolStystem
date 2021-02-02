package ElektronenDnevnik.services;

import ElektronenDnevnik.entities.Course;
import ElektronenDnevnik.entities.Grades;
import ElektronenDnevnik.entities.Student;
import ElektronenDnevnik.repositories.GradesRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


//https://medium.com/backend-habit/integrate-junit-and-mockito-unit-testing-for-service-layer-a0a5a811c58a
@ExtendWith(MockitoExtension.class)
class GradesServiceTest {

    @Mock
    private GradesRepository gradesRepository;

    @InjectMocks
   private GradesServiceImpl gradesService;



    @Test
    public void getAllGradesTest(){
        //sample repository populated by Grades
        List<Grades> list = new ArrayList<Grades>();
        Grades g1 = new Grades();
        Grades g2 = new Grades();
        Grades g3 = new Grades();

        list.add(g1);
        list.add(g2);
        list.add(g3);

        //mock repository to return sample list
        given(gradesRepository.findAll()).willReturn(list);

        //test
        List<Grades> expected = gradesService.getAll();
        assertEquals(expected, list);
    }


    @Test
    public void saveGradeTest(){
        //https://howtodoinjava.com/spring-boot2/testing/spring-boot-mockito-junit-example/
        Grades grade = new Grades();
        gradesService.saveGrade(grade);
        verify(gradesRepository, times(1)).save(grade);

    }


    @Test
    public void getGradeByIdTest(){

        Grades grade = new Grades();
        grade.setId(1L);
        grade.setGrade(2);

        given(gradesRepository.findById(1L)).willReturn(java.util.Optional.of(grade));

        Grades g1 = gradesService.getGradeById(1L);
        assertEquals(2, g1.getGrade());

    }

    @Test
    public void deleteGradeByIdTest(){
        gradesService.deleteGradeById(1L);
        verify(gradesRepository, times(1)).deleteById(1L);
    }

    @Test
    public void deleteGradesForStudentTest(){
       Student s = new Student();
       s.setId(1L);

        List<Grades> list = new ArrayList<Grades>();

        Grades g1 = new Grades();
        g1.setStudent(s);
        g1.setId(1L);


        list.add(g1);

        //mock repository to return fake list
        given(gradesRepository.findAll()).willReturn(list);
        gradesService.deleteGradesForStudent(s);

        //Method should be invoked exactly once for single Grade
        verify(gradesRepository, times(1)).deleteById(1L);
    }

    @Test
    public void getGradesForStudentTest(){
        Student s = new Student();
        s.setId(1L);

        List<Grades> list = new ArrayList<Grades>();

        Grades g1 = new Grades();
        g1.setStudent(s);
        g1.setId(1L);
        g1.setCourse(Course.CHEMISTRY);

        list.add(g1);

        //Mock repository to return example list
        given(gradesRepository.findAll()).willReturn(list);

        //Should return same List as example one
        List<Grades> expected = gradesService.getGradesForStudent(s,Course.CHEMISTRY);


        assertEquals(expected, list);

    }
}

