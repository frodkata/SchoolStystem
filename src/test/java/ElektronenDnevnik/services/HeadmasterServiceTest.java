package ElektronenDnevnik.services;

import ElektronenDnevnik.entities.Headmaster;
import ElektronenDnevnik.repositories.HeadmasterRepository;
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
class HeadmasterServiceTest {
    @Mock
    private HeadmasterRepository headmasterRepository;

    @InjectMocks
    private HeadmasterServiceImpl headmasterService;


    @Test
    void getHeadmasterTest() {
        Headmaster h = new Headmaster();
        List<Headmaster> list =  new ArrayList<>();
        list.add(h);

        //Given mock repo returns List of exactly one Headmaster
        given(headmasterRepository.findAll()).willReturn(list);

        assertEquals(h, headmasterRepository.findAll().get(0));
    }

    @Test
    void saveHeadmasterTest() {
        Headmaster h = new Headmaster();
        headmasterService.saveHeadmaster(h);
        verify(headmasterRepository, times(1)).save(h);
    }

    @Test
    void deleteHeadmasterTest() {
        headmasterService.deleteHeadmaster();
        verify(headmasterRepository, times(1)).deleteAll();
    }
}