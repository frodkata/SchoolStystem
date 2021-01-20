package ElektronenDnevnik.services;

import ElektronenDnevnik.entities.Headmaster;
import ElektronenDnevnik.repositories.HeadmasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HeadmasterServiceImpl implements HeadmasterService {

    @Autowired
    HeadmasterRepository headmasterRepository;


    @Override
    public Headmaster getHeadmaster() {
        for (Headmaster h: headmasterRepository.findAll()) {
            return h;
        }
        return null;
    }

    @Override
    public void saveHeadmaster(Headmaster headmaster) {
        headmasterRepository.save(headmaster);
    }

    @Override
    public void deleteHeadmaster() {
        headmasterRepository.deleteAll();
    }
}
