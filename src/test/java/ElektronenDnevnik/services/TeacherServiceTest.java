package ElektronenDnevnik.services;

import ElektronenDnevnik.entities.Teacher;
import ElektronenDnevnik.entities.UserProfile;
import ElektronenDnevnik.repositories.TeacherRepository;
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
class TeacherServiceTest {
    @Mock
    private TeacherRepository teacherRepository;

    @InjectMocks
    private TeacherServiceImpl teacherService;

    @Test
    void getAll() {
        //sample repository populated by Students
        List<Teacher> list = new ArrayList<Teacher>();
        Teacher t1 = new Teacher();
        Teacher t2 = new Teacher();
        Teacher t3 = new Teacher();

        list.add(t1);
        list.add(t2);
        list.add(t3);

        //Given that Mock repository returns sample list
        given(teacherRepository.findAll()).willReturn(list);

        //test
        List<Teacher> expected = teacherService.getAll();
        assertEquals(expected, list);

    }

    @Test
    void saveTeacher() {
        Teacher teacher = new Teacher();
        teacherService.saveTeacher(teacher);
        verify(teacherRepository, times(1)).save(teacher);
    }

    @Test
    void getTeacherById() {
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setFirstName("Test");

        //Given that mock repo will return student object
        given(teacherRepository.findById(1L)).willReturn(java.util.Optional.of(teacher));

        //Should return same student
        Teacher expected = teacherService.getTeacherById(1L);
        assertEquals("Test",expected.getFirstName());
    }

    @Test
    void deleteTeacherById() {
        teacherService.deleteTeacherById(1L);
        verify(teacherRepository, times(1)).deleteById(1L);
    }

    @Test
    void getTeacherByUserId() {
        Teacher t = new Teacher();
        UserProfile user = new UserProfile();

        user.setId(1L);
        t.setUserProfile(user);
        t.setFirstName("Test");

        List<Teacher> list = new ArrayList<>();
        list.add(t);

        //Given that mock repo will return given list
        given(teacherRepository.findAll()).willReturn(list);

        //Should return same Parent object
        Teacher expectedT = teacherService.getTeacherByUserId(1L);
        assertEquals("Test", expectedT.getFirstName());
    }
}