package ElektronenDnevnik.services;

import ElektronenDnevnik.entities.Parent;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ParentService {
    List<Parent> getAll();
    void saveParent(Parent parent);
    Parent getParentById(Long id);
    void deleteParentById(Long id);
    Parent getParentByUserId(Long id);
}
