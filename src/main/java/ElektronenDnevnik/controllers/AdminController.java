package ElektronenDnevnik.controllers;


import ElektronenDnevnik.entities.*;
import ElektronenDnevnik.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


import javax.validation.Valid;
import java.util.List;
import java.util.regex.Pattern;

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

    @Autowired
    private JavaMailSender emailSender;





    @GetMapping("/index")
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
    public String saveStudent(@Valid @ModelAttribute("student") Student student,BindingResult bindingResult) {


        for ( Student s: studentService.getAll()) {
            if(s.getEgn().equals(student.getEgn())){
                return "redirect:/showNewStudentForm?egnError";
            }
        }


        //VALIDATE FORM
        if (bindingResult.hasErrors() ) {  //check if entity constraints are satisfied
            return "admin/studentPanel/newStudent";
        }
        //As Parent is created by creating Student, i couldn't place @Valid to validate parent model, as there is no parent model that gets passed,
        //so validation on parent fields is done  here
        else if(   !Pattern.matches("[a-zA-Z]+",student.getParent().getFirstName())
                || !Pattern.matches("[a-zA-Z]+",student.getParent().getLastName()) )
        {
            return "redirect:/showNewStudentForm?parentError";
        }
        else{


            //Both Student and Parent are created at the same time, based on the idea that the Parent has access to the account
            //Create new UserProfile with ROLE.PARENT
            UserProfile userProfile = new UserProfile();
            userProfile.setUsername("S_" +  userService.randomStringForUsername()); //Username with first 4 characters from EGN
            userProfile.setPassword(student.getEgn());
            userProfile.setRole(Role.PARENT); //Set role
            student.getParent().setUserProfile(userProfile); //Add userProfile profile to Student entity

            //Send password and username to Parent email
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(student.getParent().getEmail());
            message.setSubject("School System Account");
            message.setText("Username: "+ userProfile.getUsername() + "\n Password: " + userProfile.getPassword());
            emailSender.send(message);


            //Save new student to repository
            studentService.saveStudent(student);
            //Save new userProfile to repository
            userService.save(userProfile);

            return "redirect:/index?email";
        }



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
        userService.deleteUserById(studentToDelete.getParent().getUserProfile().getId());

        //Delete  Parent from repository
        parentService.deleteParentById(studentToDelete.getParent().getId());

        //Delete student grades from repository
        gradesService.deleteGradesForStudent(studentToDelete);

        //Delete student from repository
        studentService.deleteStudentById(id);



        return "redirect:/index?success";
    }


    @GetMapping("/showFormForUpdateStudent/{id}")
    public String showFormForUpdateStudent(@PathVariable( value = "id") long id, Model model) {

        // get Teacher from repository
        Student s = studentService.getStudentById(id);

        // set Teacher as a model attribute to pre-populate the form
        model.addAttribute("student", s);
        return "admin/studentPanel/updateStudent";

    }

    //save updated Teacher without changing UserProfile
    @PostMapping("/updateStudent")
    public String updateStudent(@Valid @ModelAttribute("student") Student student, BindingResult bindingResult) {
        //VALIDATE
        if (bindingResult.hasErrors() ) {  //check if entity constraints are satisfied
            return "admin/studentPanel/updateStudent";
        }else {
            studentService.saveStudent(student);
            return "redirect:/index?success";
        }
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
    public String saveTeacher(@Valid @ModelAttribute("teacher") Teacher teacher,BindingResult bindingResult) {
        if (bindingResult.hasErrors() ) {  //check if entity constraints are satisfied
            return "admin/teacherPanel/newTeacher";
        }else{


            //Create new UserProfile with ROLE.TEACHER
            UserProfile userProfile = new UserProfile();
            userProfile.setUsername("T_"+userService.randomStringForUsername()); //Create username
            userProfile.setPassword("1234"); //TEST PASSWORD
            userProfile.setRole(Role.TEACHER); //Add TEACHER role to UserProfile
            teacher.setUserProfile(userProfile); //Add userProfile profile to Teacher entity

            //Send password and username to email
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(teacher.getEmail());
            message.setSubject("School System Account");
            message.setText("Username: "+ userProfile.getUsername() + "\n Password: " + userProfile.getPassword());
            emailSender.send(message);

            //Save new Teacher to repository
            teacherService.saveTeacher(teacher);
            //Save new UserProfile to repository
            userService.save(userProfile);


            return "redirect:/index?email";
        }

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
        userService.deleteUserById(teacherService.getTeacherById(id).getUserProfile().getId());

        //Delete Teacher from repository
        teacherService.deleteTeacherById(id);

        return "redirect:/index?success";
    }

    @GetMapping("/showFormForUpdateTeacher/{id}")
    public String showFormForUpdateTeacher(@PathVariable( value = "id") long id, Model model) {

        // get Teacher from repository
        Teacher t = teacherService.getTeacherById(id);

        // set Teacher as a model attribute to pre-populate the form
        model.addAttribute("teacher", t);
        return "admin/teacherPanel/updateTeacher";


    }

    //save updated Teacher without changing UserProfile
    @PostMapping("/updateTeacher")
    public String updateTeacher(@Valid @ModelAttribute("teacher") Teacher teacher, BindingResult bindingResult) {
    if(bindingResult.hasErrors()){
        return "admin/teacherPanel/updateTeacher";
    }else{
        teacherService.saveTeacher(teacher);


        return "redirect:/index?success";
    }

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
    public String saveHeadmaster(@Valid @ModelAttribute("headmaster") Headmaster headmaster, BindingResult bindingResult) {

        if (bindingResult.hasErrors() ) {  //check if entity constraints are satisfied
            return "admin/headmasterPanel/newHeadmaster";
        }else {
            //Only one headmaster can exist
            if(headmasterService.getHeadmaster() != null){ //Check if there is an existing headmaster
                return "redirect:/index?error";

            }else{
                //Create new UserProfile with ROLE.HEADMASTER
                UserProfile userProfile = new UserProfile();
                userProfile.setUsername("H_"+headmaster.getFirstName()); //Create username
                userProfile.setPassword("1234"); //TEST PASSWORD
                userProfile.setRole(Role.HEADMASTER); //Add HEADMASTER role to UserProfile
                headmaster.setUserProfile(userProfile); //Add userProfile profile to Headmaster entity

                //Send password and username to email
                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(headmaster.getEmail());
                message.setSubject("School System Account");
                message.setText("Username: "+ userProfile.getUsername() + "\n Password: " + userProfile.getPassword());
                emailSender.send(message);

                //Save new Headmaster to repository
                headmasterService.saveHeadmaster(headmaster);
                //Save new UserProfile to repository
                userService.save(userProfile);


                return "redirect:/index?email";
            }

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

        Long userId = headmasterService.getHeadmaster().getUserProfile().getId(); //for deleting UserProfile

        //Delete Headmaster profile from repository
        userService.deleteUserById(userId);

        //Delete Headmaster from repository
        headmasterService.deleteHeadmaster();

        return "redirect:/index?success";
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

    //save updated Headmaster without changing UserProfile
    @PostMapping("/updateHeadmaster")
    public String updateHeadmaster(@Valid @ModelAttribute("headmaster") Headmaster headmaster, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "admin/headmasterPanel/updateHeadmaster";
        }   else {
            headmasterService.saveHeadmaster(headmaster);
            return "redirect:/index?success";
        }

    }


}
