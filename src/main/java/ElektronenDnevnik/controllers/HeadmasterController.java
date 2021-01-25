package ElektronenDnevnik.controllers;

import ElektronenDnevnik.entities.Student;
import ElektronenDnevnik.entities.Teacher;
import ElektronenDnevnik.services.GradesService;
import ElektronenDnevnik.services.StudentService;
import ElektronenDnevnik.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@Controller
public class HeadmasterController {

    @Autowired
    TeacherService teacherService;

    @Autowired
    StudentService studentService;


    @GetMapping("/headmasterMenu")
    public String headmasterMenu(Model model){
        //Fetch all Teacher data from repository
        List<Teacher> teacherList = teacherService.getAll();

        //Fetch all Student data from repository
        List<Student> studentsList = studentService.getAll();

        model.addAttribute("teacherList", teacherList);
        model.addAttribute("studentList", studentsList);

        //Grade statistics

        return "headmaster/headmasterMenu";
    }




}
