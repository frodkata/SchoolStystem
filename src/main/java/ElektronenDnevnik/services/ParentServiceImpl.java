package ElektronenDnevnik.services;

import ElektronenDnevnik.entities.Parent;
import ElektronenDnevnik.repositories.ParentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParentServiceImpl implements ParentService {

    @Autowired
    ParentRepository parentRepository;

    @Override
    public List<Parent> getAll() {
        return parentRepository.findAll();
    }

    @Override
    public void saveParent(Parent parent) {
        parentRepository.save(parent);
    }

    @Override
    public Parent getParentById(Long id) {
        Optional<Parent> optional = parentRepository.findById(id);
        Parent parent = null;
        if (optional.isPresent()) {
            parent = optional.get();
        } else {
            throw new RuntimeException(" Parent not found for id :: " + id);
        }
        return parent;

    }

    @Override
    public void deleteParentById(Long id) {
        parentRepository.deleteById(id);
    }

    @Override
    public  Parent getParentByUserId(Long id){
        for (Parent p : parentRepository.findAll()) {
            if(p.getUser().getId().equals(id)){
                return p;
            }
        }

        throw new RuntimeException(" Parent  not found for user id :: " + id);

    }
}


