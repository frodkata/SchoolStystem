package ElektronenDnevnik.entities;

import javax.persistence.*;

@Entity
@Table(name = "grades")
public class Grades {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "grade")
    private double grade;

    @Column(name = "course")
    private Course course;


    //https://www.baeldung.com/hibernate-one-to-many
    @ManyToOne
    @JoinColumn(name="student_id")
    private Student student;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
