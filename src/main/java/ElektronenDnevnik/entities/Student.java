package ElektronenDnevnik.entities;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.util.List;

@Entity
@Table(name = "student", uniqueConstraints = @UniqueConstraint(columnNames = "egn"))
public class Student {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    @NotEmpty(message = "Field cannot be left empty!")
    @Pattern(regexp="[A-Za-z]+$", message = "Must contain only latin letters!")
    private String firstName;

    @Column(name = "last_name")
    @NotEmpty(message = "Field cannot be left empty!")
    @Pattern(regexp="[A-Za-z]+$", message = "Must contain only latin letters!")
    private String lastName;

    @Column(name = "absences")
    private int absences;

    @Column(name = "egn")
    @Pattern(regexp="[\\d]{10}", message = "Must contain 10 digits")   //String of digits with size 10
    private String egn;

    @Column(name = "year")
    private int year;




   // https://www.baeldung.com/jpa-one-to-one
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    private Parent parent;


    //https://www.baeldung.com/hibernate-one-to-many
    @OneToMany(mappedBy="student")
    private List<Grades> grades;





    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAbsences() {
        return absences;
    }

    public void setAbsences(int absences) {
        this.absences = absences;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }


    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }

    public List<Grades> getGrades() {
        return grades;
    }

    public void setGrades(List<Grades> grades) {
        this.grades = grades;
    }

    public String getEgn() {
        return egn;
    }

    public void setEgn(String egn) {
        this.egn = egn;
    }


}
