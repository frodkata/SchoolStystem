package ElektronenDnevnik.entities;

public enum Role {
    ADMIN,PARENT,TEACHER,HEADMASTER;

    @Override
    public String toString() {
        return  "ROLE_"+this.name();
    }
}
