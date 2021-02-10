package ElektronenDnevnik.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class MainController {

    //REDIRECT EACH ROLE TO APPROPRIATE MENU
    //https://stackoverflow.com/questions/45709333/page-redirecting-depending-on-role-using-spring-security-and-thymeleaf-spring
    @GetMapping("/success")
    public void success(HttpServletRequest request, HttpServletResponse response, Authentication authResult) throws IOException, ServletException {

        String role =  authResult.getAuthorities().toString();

        //Redirect to ADMIN page
        if(role.contains("ROLE_ADMIN")){
            response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/"));
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



}
