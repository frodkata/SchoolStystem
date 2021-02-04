package ElektronenDnevnik.controllers;

import ElektronenDnevnik.entities.Student;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//https://www.petrikainulainen.net/programming/spring-framework/unit-testing-of-spring-mvc-controllers-normal-controllers/
@SpringBootTest
@AutoConfigureMockMvc
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "admin", roles = { "ADMIN" })
    void index() throws Exception {
        this.mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/index"));
    }

    @Test
    @WithMockUser(username = "admin", roles = { "ADMIN" })
    void showNewStudentForm() throws Exception{
        this.mockMvc.perform(get("/showNewStudentForm"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/studentPanel/newStudent"))
                .andExpect(model().attributeExists("student"));
    }
/*
    @Test
    @WithMockUser(username = "admin", roles = { "ADMIN" })
    void saveStudent() throws Exception{
        mockMvc.perform(post("/saveStudent").with(csrf())
            //    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
              //  .andExpect(status().isOk())
                .andExpect(forwardedUrl("/"))
                .andReturn();
    }

 */

    @Test
    void viewStudents() throws Exception{
    }

    @Test
    void deleteStudent() throws Exception{
    }

    @Test
    void showFormForUpdateStudent() throws Exception{
    }

    @Test
    void updateStudent() throws Exception{
    }

    @Test
    void showNewTeacherForm() throws Exception{
    }

    @Test
    void saveTeacher() throws Exception{
    }

    @Test
    void teacher() throws Exception{
    }

    @Test
    void deleteTeacher() throws Exception{
    }

    @Test
    void showFormForUpdateTeacher() throws Exception{
    }

    @Test
    void updateTeacher() throws Exception{
    }

    @Test
    void showNewHeadmasterForm() throws Exception{
    }

    @Test
    void saveHeadmaster() throws Exception{
    }

    @Test
    void viewHeadmaster() throws Exception{
    }

    @Test
    void deleteHeadmaster() throws Exception{
    }

    @Test
    void showFormForUpdateHeadmaster() throws Exception{
    }

    @Test
    void updateHeadmaster() throws Exception{
    }
}