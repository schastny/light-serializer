package net.shchastnyi.serializer.messages;

public class PersonWithAge {

    public String firstName;
    public String lastName;
    public Integer age;

    public PersonWithAge() {}

    public PersonWithAge(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    @Override
    public boolean equals(Object obj) {
        if ( !(obj instanceof PersonWithAge) ) {
            return false;
        }
        PersonWithAge that = (PersonWithAge) obj;
        boolean res =
                this.firstName.equals(that.firstName)
                && this.lastName.equals(that.lastName)
                && this.age == (that.age);
        return res;
    }
}
