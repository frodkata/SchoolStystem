package ElektronenDnevnik.services;

import ElektronenDnevnik.entities.Headmaster;
import org.springframework.stereotype.Service;

@Service
public interface HeadmasterService {
    Headmaster getHeadmaster();
    void saveHeadmaster(Headmaster headmaster);
    void deleteHeadmaster();
}
