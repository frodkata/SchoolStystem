package ElektronenDnevnik.repositories;

import ElektronenDnevnik.entities.Headmaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeadmasterRepository extends JpaRepository<Headmaster,Long> {
}
