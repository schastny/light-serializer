package net.shchastnyi.serializer.messages;

public class Employee {

    public String firstName;
    public String lastName;
    private long age = 42;
    private long fakeAge = 42;

    public Employee(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = System.currentTimeMillis();
        this.fakeAge = System.currentTimeMillis()+1;
    }

    public long getAge() {
        return age;
    }
}
