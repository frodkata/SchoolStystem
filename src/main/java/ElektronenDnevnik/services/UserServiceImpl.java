package ElektronenDnevnik.services;

import ElektronenDnevnik.entities.Role;
import ElektronenDnevnik.entities.User;
import ElektronenDnevnik.entities.UserRegistrationDto;
import ElektronenDnevnik.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;


@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;


	@Override
	public User save(User user) {
		String password = passwordEncoder.encode(user.getPassword());
		user.setPassword(password);
		return userRepository.save(user);
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	
		User user = userRepository.findByUsername(username);
		if(user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}


		//https://stackoverflow.com/questions/37615034/spring-security-spring-boot-how-to-set-roles-for-users
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
			grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole().toString()));


		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
	}
	



	/*
	 * https://docs.spring.io/spring-security/site/docs/4.0.2.RELEASE/reference/htmlsingle/#obtaining-information-about-the-current-user
	 * Get current logged user object
	 * */
	@Override
	public User getCurrentUser() {
		String username;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			username = ((UserDetails)principal).getUsername();
		} else {
			username = principal.toString();
		}

		User currentUser = userRepository.findByUsername(username);

		return currentUser;
	}

	@Override
	public void deleteUserById(Long id) {
		userRepository.deleteById(id);
	}

	@Override
	public String randomStringForUsername(){
		String randomString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();
		while (salt.length() < 6) { // length of the random string.
			int index = (int) (rnd.nextFloat() * randomString.length());
			salt.append(randomString.charAt(index));
		}
		String saltStr = salt.toString();
		return saltStr;
	}

}
