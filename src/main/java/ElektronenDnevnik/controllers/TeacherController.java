package ElektronenDnevnik.controllers;

import ElektronenDnevnik.entities.Course;
import ElektronenDnevnik.entities.Grades;
import ElektronenDnevnik.entities.Student;
import ElektronenDnevnik.entities.Teacher;
import ElektronenDnevnik.services.GradesService;
import ElektronenDnevnik.services.StudentService;
import ElektronenDnevnik.services.TeacherService;
import ElektronenDnevnik.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class TeacherController {


    @Autowired
    TeacherService teacherService;

    @Autowired
    StudentService studentService;

    @Autowired
    GradesService gradesService;

    @Autowired
    UserService userService;


    @GetMapping("/teacherMenu")
    public String teacherMenu(Model model){

        //get current logged teacher year
        int year = teacherService.getTeacherByUserId(userService.getCurrentUser().getId()).getYear();
        List<Student> studentList =  studentService.getStudentsByYear(year);


        model.addAttribute("studentList", studentList);




        return "teacher/teacherMenu";
    }

    //mark student absent by ID
    @GetMapping("/showMarkAbsentForm/{id}")
    public String showMarkAbsentForm(@PathVariable( value = "id") long id, Model model) {

        Student student = studentService.getStudentById(id);


        //bind form data
        model.addAttribute("student", student);
        model.addAttribute("gradesList", student.getGrades() );

        return "teacher/markAbsent";
    }


    @PostMapping("/markAbsent")
    public String markAbsent(@ModelAttribute("student") Student student) {

        //Save marked absent student to repository
        studentService.saveStudent(student);

        return "redirect:/teacherMenu";
    }



    //add new grade to student by ID
    @GetMapping("/showNewGradeForm/{id}")
    public String showNewGradeForm(@PathVariable( value = "id") long id, Model model) {
        //Get student by ID for new grade
        Student student = studentService.getStudentById(id);
        Grades grades = new Grades();
        grades.setStudent(student);
        student.getGrades().add(grades);

        //bind form data
        model.addAttribute("student", student);
        model.addAttribute("grades",grades);

        return "teacher/newGrade";
    }


    @PostMapping("/saveGrade")
    public String saveGrade(@ModelAttribute("grades") Grades grades) {

        //Save grade to repository
        grades.setCourse(teacherService.getTeacherByUserId(userService.getCurrentUser().getId()).getCourse());
        gradesService.saveGrade(grades);

        return "redirect:/teacherMenu";
    }


    //display grades for student by ID
    @GetMapping("/viewGrades/{id}")
    public String viewGrade(@PathVariable (value = "id") long id, Model model) {

        //Fetch student by ID
        Student student = studentService.getStudentById(id);

        //Get current logged in Teacher by his user profile
        Teacher teacher = teacherService.getTeacherByUserId(userService.getCurrentUser().getId());

        //Only fetch grades for Student by Teacher's course
        List<Grades> gradesList = gradesService.getGradesForStudent(student, teacher.getCourse());

        model.addAttribute("gradesList", gradesList);
        return "teacher/viewGrades";
    }



    //delete grade for student by ID
    @GetMapping("/deleteGrade/{id}")
    public String deleteGrade(@PathVariable (value = "id") long id) {

        //Delete grade from repository
        this.gradesService.deleteGradeById(id);
        return "redirect:/teacherMenu";
    }





}


