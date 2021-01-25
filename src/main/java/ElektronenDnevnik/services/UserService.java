package ElektronenDnevnik.services;

import ElektronenDnevnik.entities.UserProfile;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public interface UserService extends UserDetailsService{
	UserProfile save(UserProfile userProfile);
	UserProfile getCurrentUser();
	void deleteUserById(Long id);
	String randomStringForUsername();


}
