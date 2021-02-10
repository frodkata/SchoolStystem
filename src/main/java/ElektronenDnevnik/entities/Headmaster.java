package ElektronenDnevnik.entities;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "headmaster")
public class Headmaster {
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

    @Column(name = "schoolName")
    @NotEmpty(message = "Field cannot be left empty!")
    private String schoolName;

    @Column(name = "schoolAddress")
    @NotEmpty(message = "Field cannot be left empty!")
    private String schoolAddress;

    // https://www.baeldung.com/jpa-one-to-one
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserProfile userProfile;



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

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getSchoolAddress() {
        return schoolAddress;
    }

    public void setSchoolAddress(String schoolAddress) {
        this.schoolAddress = schoolAddress;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }
}
