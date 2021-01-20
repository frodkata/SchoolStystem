package ElektronenDnevnik.services;

import ElektronenDnevnik.entities.Role;
import ElektronenDnevnik.entities.User;
import ElektronenDnevnik.entities.UserRegistrationDto;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public interface UserService extends UserDetailsService{
	User save(User user);
	User getCurrentUser();
	void deleteUserById(Long id);
	String randomStringForUsername();


}
