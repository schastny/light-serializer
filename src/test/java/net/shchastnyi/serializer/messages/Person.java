package net.shchastnyi.serializer.messages;

public class Person {

    public String firstName;
    public String lastName;

    public Person() {}

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object obj) {
        if ( !(obj instanceof Person) ) {
            return false;
        }
        Person that = (Person) obj;
        boolean res =
                this.firstName.equals(that.firstName)
                && this.lastName.equals(that.lastName);
        return res;
    }
}
