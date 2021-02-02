package ElektronenDnevnik.services;

import ElektronenDnevnik.entities.Parent;
import ElektronenDnevnik.entities.UserProfile;
import ElektronenDnevnik.repositories.ParentRepository;
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
class ParentServiceTest {
    @Mock
    private ParentRepository parentRepository;

    @InjectMocks
    private ParentServiceImpl parentService;

    @Test
    void getAll() {
        //sample repository populated by Parents
        List<Parent> list = new ArrayList<Parent>();
        Parent p1 = new Parent();
        Parent p2 = new Parent();
        Parent p3 = new Parent();

        list.add(p1);
        list.add(p2);
        list.add(p3);

        //mock repository to return sample list
        given(parentRepository.findAll()).willReturn(list);

        //test
        List<Parent> expected = parentService.getAll();
        assertEquals(expected, list);
    }

    @Test
    void saveParent() {
        Parent parent = new Parent();
        parentService.saveParent(parent);
        verify(parentRepository, times(1)).save(parent);

    }

    @Test
    void getParentById() {
        Parent parent = new Parent();
        parent.setId(1L);
        parent.setFirstName("Test");

        //Given that mock repo will return parent object
        given(parentRepository.findById(1L)).willReturn(java.util.Optional.of(parent));

        //Should return same parent
        Parent parent1 = parentService.getParentById(1L);
        assertEquals("Test",parent1.getFirstName());
    }

    @Test
    void deleteParentById() {
        parentService.deleteParentById(1L);
        verify(parentRepository, times(1)).deleteById(1L);
    }

    @Test
    void getParentByUserId() {
        Parent p = new Parent();
        UserProfile user = new UserProfile();

        user.setId(1L);
        p.setUserProfile(user);
        p.setFirstName("Test");

        List<Parent> list = new ArrayList<>();
        list.add(p);

        //Given that mock repo will return given list
        given(parentRepository.findAll()).willReturn(list);

        //Should return same Parent object
        Parent expectedP = parentService.getParentByUserId(1L);
        assertEquals("Test", expectedP.getFirstName());



    }
}