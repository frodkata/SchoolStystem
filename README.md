<img src="/src/main/resources/static/images/logo.png"/>
<h1>Online school management system written in Spring Boot </h1>
<h2>Project is no longer live due to Heroku terminating its free tier accounts!</h2>
<a href="https://school-system-heroku.herokuapp.com/">Project is live here.</a>  <p></p>

A full-stack application that provides management tools for a given school with functionality based on different roles. <br>
4 roles exist: <b>[ADMIN]</b> <b>[TEACHER]</b> <b>[PARENT]</b> <b>[HEADMASTER]</b> ,  and each one of them has their own unique toolset and privileges. <br>
They are explained further below. <b><i>TLDR at the bottom </i></b>
<h3>Credentials to test the project: </h3>
<b>Admin role:</b> User: admin ; Pass: admin <br>
<b>Teacher role:</b> User: T_VL86G0 ; Pass: 1234 <br>
<b>Student role:</b> User: S_6YKV6K ; Pass: 9941251234 <br>
<b>Headmaster role:</b> User: H_Direktor ; Pass: 1234 <br>

<h3>Technologies used: </h3>
- Spring Boot(Spring Data/Hibernate, Spring Security) <br>
- Mockito service layer tests <br>
- Java Mail and Apache Commons email validator <br>
- Thymeleaf + CSS <br>
- JQuery + Ajax  <br>
- PostgreSQL serverside, hosted on Heroku 
<h3>Full description:</h3>
Greeted by a simple login screen, each user can log into their profile panel based on their role. <br>
<h4>[ADMIN]</h4>
<img src="/src/main/resources/static/images/adminPanel.gif"/>

Users can't register themselves! Each user is registered by the mercy of the [ADMIN]. <br> By registering either a [TEACHER], [STUDENT] or [HEADMASTER] 
users are sent an EMAIL with their respective username and primary password. <br> The primary password is a random character string of length 6 which is used to log in for the first time and can be changed 
from <br> the Change Password menu. <br>
<b>NOTE: <i>By registering Students, the Parent profile is created and serves as the main profile, as the Parent is expected to have access to child's grades and absences </i></b> <p></p>
<b>Creating and then deleting a student: </b>
<img src="/src/main/resources/static/images/saveStudent.gif"/>
Admins have access to every registered user infromation except their passwords. Other than creating, they can Update information and Delete information for users. <br>
Admins also have the ability to add other Admins too.
<h4>[TEACHER]</h4>
On creation, each teacher is assigned a class by the subject he is proficient in and the year he is tutoring. <br>
Therefore, teachers only have access to information for students that are taking the class they are teaching! <br>
Upon login, teachers are greeted with a list of each of ther students and the ability to either <b><i>Mark Absent</i></b> , <b><i>View</i></b>, <b><i>Add</i></b> 
and <b><i>Delete</i></b> grades, as seen here:
<img src="/src/main/resources/static/images/teacherMenu.gif"/>
<h4>[PARENT] ([student])</h4>
The Parent profile can be both shared with the student, or be exclusive to the Parent! <br>
The information about the student's absences and grades might be more interesting to the Parent. The user is therefore greeted with a cute little profile card of the Student and
the ability to either switch between number of absences and table of grades as shown here:
<img src="/src/main/resources/static/images/parentMenu.gif"/>
<h4>[HEADMASTER]</h4>
The Headmaster can only read information and has no other particular functionality.  <br>
The Headmaster user is presented with a full list of every Student and Teacher currently registered
in this school:
<img src="/src/main/resources/static/images/headmasterMenu.gif"/>
<p></p>

<h4>TLDR:</h4>
Users are managed and created by the Admin and then sent an Email with their username and password key. Once logged in, they can change their password. <br>
Teachers manage grades and absences of the students they teach. <br>
Parents can check grades and absences of their kid. <br>
Headmaster sees a full list of every registered Teacher and Student. <br>
