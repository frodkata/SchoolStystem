package ElektronenDnevnik.controllers;


import ElektronenDnevnik.entities.*;
import ElektronenDnevnik.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


import java.util.List;

@Controller
public class AdminController {

    @Autowired
    StudentService studentService;

    @Autowired
    TeacherService teacherService;

    @Autowired
    ParentService parentService;

    @Autowired
    UserService userService;

    @Autowired
    HeadmasterService headmasterService;

    @Autowired
    GradesService gradesService;






    @GetMapping("/")
    public String index() {

        return "admin/index";
    }



    //ADMIN STUDENT CONTROLLERS-----------------------------------------------------------

    @GetMapping("/showNewStudentForm")
    public String showNewStudentForm(Model model) {

        //Bind model to form
        Student student = new Student();
        model.addAttribute("student", student);

        return "admin/studentPanel/newStudent";
    }


    @PostMapping("/saveStudent")
    public String saveStudent(@ModelAttribute("student") Student student) {


        //Create new User with ROLE.PARENT
       User user = new User();
       user.setUsername("S_" +  userService.randomStringForUsername()); //Username with first 4 characters from EGN
       user.setPassword("1234"); //TEST PASSWORD
       user.setRole(Role.PARENT); //Set role
       student.getParent().setUser(user); //Add user profile to Student entity


         //Save new student to repository
        studentService.saveStudent(student);
        //Save new user to repository
        userService.save(user);

        return "redirect:/";
    }


    @GetMapping("/viewStudents")
    public String viewStudents(Model model){

        //Fetch all student entries from repository
        List<Student> studentList =  studentService.getAll();
        model.addAttribute("studentList", studentList);


        return "admin/studentPanel/viewStudents";
    }

    //delete Student by ID
    @GetMapping("/deleteStudent/{id}")
    public String deleteStudent(@PathVariable(value = "id") long id) {

        Student studentToDelete = studentService.getStudentById(id);


        //Delete Parent user profile from repository
        userService.deleteUserById(studentToDelete.getParent().getUser().getId());

        //Delete  Parent from repository
        parentService.deleteParentById(studentToDelete.getParent().getId());

        //Delete student grades from repository
        gradesService.deleteGradesForStudent(studentToDelete);

        //Delete student from repository
        studentService.deleteStudentById(id);



        return "redirect:/?success";
    }


    @GetMapping("/showFormForUpdateStudent/{id}")
    public String showFormForUpdateStudent(@PathVariable( value = "id") long id, Model model) {

        // get Teacher from repository
        Student s = studentService.getStudentById(id);

        // set Teacher as a model attribute to pre-populate the form
        model.addAttribute("student", s);
        return "admin/studentPanel/updateStudent";

    }

    //save updated Teacher without changing User
    @PostMapping("/updateStudent")
    public String updateStudent(@ModelAttribute("student") Student student) {
        studentService.saveStudent(student);

        return "redirect:/?success";
    }




    //ADMIN TEACHER CONTROLLERS-----------------------------------------------------------
    @GetMapping("/showNewTeacherForm")
    public String showNewTeacherForm(Model model) {

        //Bind model to form
        Teacher teacher = new Teacher();
        model.addAttribute("teacher", teacher);

        return "admin/teacherPanel/newTeacher";
    }


    @PostMapping("/saveTeacher")
    public String saveTeacher(@ModelAttribute("teacher") Teacher teacher) {



        //Create new User with ROLE.TEACHER
        User user = new User();
        user.setUsername("T_"+userService.randomStringForUsername()); //Create username
        user.setPassword("1234"); //TEST PASSWORD
        user.setRole(Role.TEACHER); //Add TEACHER role to User
        teacher.setUser(user); //Add user profile to Teacher entity


        //Save new Teacher to repository
        teacherService.saveTeacher(teacher);
        //Save new User to repository
        userService.save(user);


        return "redirect:/";
    }


    //view teachers
    @GetMapping("/viewTeachers")
    public String teacher(Model model){

        //Fetch all Teacher entries form repository
       List<Teacher> teacherList =  teacherService.getAll();
        model.addAttribute("teacherList", teacherList);


        return "admin/teacherPanel/viewTeachers";
    }

    //delete Teacher by ID
    @GetMapping("/deleteTeacher/{id}")
    public String deleteTeacher(@PathVariable(value = "id") long id) {

        //Delete Teacher profile from repository
        userService.deleteUserById(teacherService.getTeacherById(id).getUser().getId());

        //Delete Teacher from repository
        teacherService.deleteTeacherById(id);

        return "redirect:/?success";
    }

    @GetMapping("/showFormForUpdateTeacher/{id}")
    public String showFormForUpdateTeacher(@PathVariable( value = "id") long id, Model model) {

        // get Teacher from repository
        Teacher t = teacherService.getTeacherById(id);

        // set Teacher as a model attribute to pre-populate the form
        model.addAttribute("teacher", t);
        return "admin/teacherPanel/updateTeacher";


    }

    //save updated Teacher without changing User
    @PostMapping("/updateTeacher")
    public String updateTeacher(@ModelAttribute("teacher") Teacher teacher) {

        teacherService.saveTeacher(teacher);


        return "redirect:/?success";
    }


    //ADMIN HEADMASTER CONTROLLERS-----------------------------------------------------------
    @GetMapping("/showNewHeadmasterForm")
    public String showNewHeadmasterForm(Model model) {

        //Bind model to form
        Headmaster headmaster = new Headmaster();
        model.addAttribute("headmaster", headmaster);

        return "admin/headmasterPanel/newHeadmaster";
    }


    @PostMapping("/saveHeadmaster")
    public String saveHeadmaster(@ModelAttribute("headmaster") Headmaster headmaster) {

        //Only one headmaster can exist
        if(headmasterService.getHeadmaster() != null){ //Check if there is an existing headmaster
            return "redirect:/?error";

        }else{
            //Create new User with ROLE.HEADMASTER
            User user = new User();
            user.setUsername("H_"+headmaster.getFirstName()); //Create username
            user.setPassword("1234"); //TEST PASSWORD
            user.setRole(Role.HEADMASTER); //Add HEADMASTER role to User
            headmaster.setUser(user); //Add user profile to Headmaster entity


            //Save new Headmaster to repository
            headmasterService.saveHeadmaster(headmaster);
            //Save new User to repository
            userService.save(user);


            return "redirect:/";
        }



    }


    //view teachers
    @GetMapping("/viewHeadmaster")
    public String viewHeadmaster(Model model){

        //Fetch all Teacher entries form repository
         Headmaster headmaster = headmasterService.getHeadmaster();
        model.addAttribute("headmaster", headmaster);


        return "admin/headmasterPanel/viewHeadmaster";
    }

    //delete Headmaster
    @GetMapping("/deleteHeadmaster")
    public String deleteHeadmaster() {

        Long userId = headmasterService.getHeadmaster().getUser().getId(); //for deleting User

        //Delete Headmaster profile from repository
        userService.deleteUserById(userId);

        //Delete Headmaster from repository
        headmasterService.deleteHeadmaster();

        return "redirect:/?success";
    }

    //update Headmaster
    @GetMapping("/showFormForUpdateHeadmaster")
    public String showFormForUpdateHeadmaster(Model model) {

        // get Teacher from repository
        Headmaster h = headmasterService.getHeadmaster();

        // set Teacher as a model attribute to pre-populate the form
        model.addAttribute("headmaster", h);
        return "admin/headmasterPanel/updateHeadmaster";

    }

    //save updated Headmaster without changing User
    @PostMapping("/updateHeadmaster")
    public String updateHeadmaster(@ModelAttribute("headmaster") Headmaster headmaster) {
        headmasterService.saveHeadmaster(headmaster);

        return "redirect:/?success";
    }


}
