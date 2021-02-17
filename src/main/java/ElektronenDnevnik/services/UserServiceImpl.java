package ElektronenDnevnik.services;

import ElektronenDnevnik.entities.UserProfile;
import ElektronenDnevnik.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
	public UserProfile save(UserProfile userProfile) {
		String password = passwordEncoder.encode(userProfile.getPassword());
		userProfile.setPassword(password);
		return userRepository.save(userProfile);
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	
		UserProfile userProfile = userRepository.findByUsername(username);
		if(userProfile == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}


		//https://stackoverflow.com/questions/37615034/spring-security-spring-boot-how-to-set-roles-for-users
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
			grantedAuthorities.add(new SimpleGrantedAuthority(userProfile.getRole().toString()));


		return new org.springframework.security.core.userdetails.User(userProfile.getUsername(), userProfile.getPassword(), grantedAuthorities);
	}
	



	/*
	 * https://docs.spring.io/spring-security/site/docs/4.0.2.RELEASE/reference/htmlsingle/#obtaining-information-about-the-current-user
	 * Get current logged user object
	 * */
	@Override
	public UserProfile getCurrentUser() {
		String username;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			username = ((UserDetails)principal).getUsername();
		} else {
			username = principal.toString();
		}

		UserProfile currentUserProfile = userRepository.findByUsername(username);

		return currentUserProfile;
	}

	@Override
	public void deleteUserById(Long id) {
		userRepository.deleteById(id);
	}

	@Override
	public String randomString(){
		//Generate random string for unique username
		String randomString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();
		while (salt.length() < 6) { // length of the random string.
			int index = (int) (rnd.nextFloat() * randomString.length());
			salt.append(randomString.charAt(index));
		}
		return salt.toString();
	}

	@Override
	public void changePassword(UserProfile userProfile, String password) {
		UserProfile user = userRepository.findByUsername(userProfile.getUsername());
		user.setPassword(password);
		save(user);
	}

}
