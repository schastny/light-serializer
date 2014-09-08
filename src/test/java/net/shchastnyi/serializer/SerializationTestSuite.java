package net.shchastnyi.serializer;

import net.shchastnyi.serializer.messages.AllWrappersInOne;
import net.shchastnyi.serializer.messages.Group;
import net.shchastnyi.serializer.messages.Person;
import net.shchastnyi.serializer.messages.PersonCyclic;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import java.util.ArrayList;
import java.util.List;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        UtilsTest.class,
        NodesTest.class,
        BytesTest.class
})
public class SerializationTestSuite {

    public static Person getPerson(){
        return new Person("John", "Smith");
    }

    public static AllWrappersInOne getAllWrappersInOne() {
        return new AllWrappersInOne(
                (byte)1, (short)2, 3, 4l,
                12f, 13d, 'c', true);
    }

    public static Group getGroup() {
        List<Person> students = new ArrayList<Person>();
        students.add(new Person("John", "Smith"));
        students.add(new Person("Nick", "Long"));
        students.add(new Person("Dave", "Brown"));
        return new Group(1, students, new Person("Angela", "Queen"));
    }

    public static PersonCyclic getPersonCyclic() {
        PersonCyclic a = new PersonCyclic();
        PersonCyclic b = new PersonCyclic();
        PersonCyclic c = new PersonCyclic();

        c.children.add(a);
        a.children.add(b);
        a.children.add(c);
        return a;
    }

    public static Character[] getCharactersArray() {
        return new Character[]{'a','b','c'};
    }

}
