package ElektronenDnevnik.services;

import ElektronenDnevnik.entities.Student;
import ElektronenDnevnik.repositories.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {
    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    @Test
    void getAll() {
        //sample repository populated by Students
        List<Student> list = new ArrayList<Student>();
        Student s1 = new Student();
        Student s2 = new Student();
        Student s3 = new Student();

        list.add(s1);
        list.add(s2);
        list.add(s3);

        //Given that Mock repository returns sample list
        given(studentRepository.findAll()).willReturn(list);

        //test
        List<Student> expected = studentService.getAll();
        assertEquals(expected, list);
    }

    @Test
    void saveStudent() {
        Student student = new Student();
        studentService.saveStudent(student);
        verify(studentRepository, times(1)).save(student);
    }

    @Test
    void getStudentById() {
        Student student = new Student();
        student.setId(1L);
        student.setFirstName("Test");

        //Given that mock repo will return student object
        given(studentRepository.findById(1L)).willReturn(java.util.Optional.of(student));

        //Should return same student
        Student expected = studentService.getStudentById(1L);
        assertEquals("Test",expected.getFirstName());
    }

    @Test
    void deleteStudentById() {
        studentService.deleteStudentById(1L);
        verify(studentRepository, times(1)).deleteById(1L);
    }

    @Test
    void getStudentsByYear() {
            Student s = new Student();
            s.setId(1L);
            s.setYear(9);

            List<Student> list = new ArrayList<>();
            list.add(s);

            //Mock repository to return example list
            given(studentRepository.findAll()).willReturn(list);

            //Should return same List as example one
            List<Student> expected = studentService.getStudentsByYear(9);


            assertEquals(expected, list);

    }
}