package net.shchastnyi.serializer.messages;

import java.util.List;

public class Group {

    public int number;
    public List<Person> students;
    public Person mentor;

    public Group(){}

    public Group(Integer number, List<Person> students, Person mentor) {
        this.number = number;
        this.students = students;
        this.mentor = mentor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Group group = (Group) o;

        if (number != group.number) return false;
        if (mentor != null ? !mentor.equals(group.mentor) : group.mentor != null) return false;
        if (students != null ? !students.equals(group.students) : group.students != null) return false;

        return true;
    }

    //Geters and setters
    public List<Person> getStudents() {
        return students;
    }

    public void setStudents(List<Person> students) {
        this.students = students;
    }

    public Person getMentor() {
        return mentor;
    }

    public void setMentor(Person mentor) {
        this.mentor = mentor;
    }
    //!Geters and setters

}
