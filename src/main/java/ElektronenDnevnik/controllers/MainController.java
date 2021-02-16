package ElektronenDnevnik.controllers;

import ElektronenDnevnik.entities.PasswordDTO;
import ElektronenDnevnik.entities.UserProfile;
import ElektronenDnevnik.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class MainController {
    @Autowired
    UserService userService;

    //REDIRECT EACH ROLE TO APPROPRIATE MENU
    //https://stackoverflow.com/questions/45709333/page-redirecting-depending-on-role-using-spring-security-and-thymeleaf-spring
    @GetMapping("/")
    public void success(HttpServletRequest request, HttpServletResponse response, Authentication authResult) throws IOException, ServletException {

        String role =  authResult.getAuthorities().toString();

        //Redirect to ADMIN page
        if(role.contains("ROLE_ADMIN")){
            response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/index"));
        }
        //Redirect to PARENT page
        else if(role.contains("ROLE_PARENT")) {
            response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/parentMenu"));
        }
        //Redirect to TEACHER page
        else if(role.contains("ROLE_TEACHER")) {
            response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/teacherMenu"));
        }
        //Redirect to HEADMASTER page
        else if(role.contains("ROLE_HEADMASTER")) {
            response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/headmasterMenu"));
        }
    }


    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/showChangePasswordForm")
    public String showChangePasswordForm(Model model) {

        //Bind model to form
        PasswordDTO password = new PasswordDTO();
        model.addAttribute("password", password);

        return "changePassword";
    }

    @PostMapping("/changePassword")
    public String changePassword( @ModelAttribute("password") PasswordDTO password ) {
        UserProfile currentUser = userService.getCurrentUser();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


        if(passwordEncoder.matches( password.getOldPassword(),
                                     currentUser.getPassword())
        ){
               userService.changePassword(currentUser, password.getNewPassword());
                return "redirect:/showChangePasswordForm?success";
            }else {
                return "redirect:/showChangePasswordForm?error";
            }

    }

}
