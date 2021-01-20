package ElektronenDnevnik.controllers;

import ElektronenDnevnik.entities.Parent;
import ElektronenDnevnik.entities.Student;
import ElektronenDnevnik.services.ParentService;
import ElektronenDnevnik.services.StudentService;
import ElektronenDnevnik.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ParentController {

    @Autowired
    ParentService parentService;

    @Autowired
    StudentService studentService;

    @Autowired
    UserService userService;

    @GetMapping("/parentMenu")
    public String parentMenu(Model model){

        //Get data for logged in Parent profile
        Parent parent = parentService.getParentByUserId(userService.getCurrentUser().getId());


        model.addAttribute("parent",  parent);

        return "parent/parentMenu";
    }


}
