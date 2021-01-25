package ElektronenDnevnik.controllers;

import ElektronenDnevnik.entities.Role;
import ElektronenDnevnik.entities.UserProfile;
import ElektronenDnevnik.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/registration")
public class UserRegistrationController {

	@Autowired
	private UserService userService;

	
	@ModelAttribute("user")
    public UserProfile user() {
        return new UserProfile();
    }
	
	@GetMapping
	public String showRegistrationForm() {
		return "registration";
	}


	
	@PostMapping
	public String registerUserAccount(@ModelAttribute("userProfile") UserProfile userProfile) {
		userProfile.setRole(Role.ADMIN);
		userService.save(userProfile);
		return "redirect:/registration?success";
	}
}
