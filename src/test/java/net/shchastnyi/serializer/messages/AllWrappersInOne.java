package net.shchastnyi.serializer.messages;

public class AllWrappersInOne {

    public String firstName;
    public String lastName;
    public int age;
    public Integer weight;

    public AllWrappersInOne() {}

    public AllWrappersInOne(String firstName, String lastName, int age, int weight) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.weight = weight;
    }

    @Override
    public boolean equals(Object obj) {
        if ( !(obj instanceof AllWrappersInOne) ) {
            return false;
        }
        AllWrappersInOne that = (AllWrappersInOne) obj;
        boolean res =
                this.firstName.equals(that.firstName)
                && this.lastName.equals(that.lastName)
                && this.age == (that.age)
                && this.weight == (that.weight);
        return res;
    }
}
