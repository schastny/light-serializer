package net.shchastnyi.serializer;

import net.shchastnyi.serializer.messages.AllWrappersInOne;
import net.shchastnyi.serializer.messages.Group;
import net.shchastnyi.serializer.messages.Person;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class NodeTest {

    @Test
    public void testSimpleBeanWithPublicFields() throws Exception {
        //Writing message
        Person messageSent = new Person("John", "Smith");
        Node nodeSent = LightSerializerWriter.getNode(messageSent);

        //Reading message
        Person messageReceived = (Person) LightSerializerReader.constructFromNode(nodeSent);

        Assert.assertEquals("Failure - objects are not equal", messageSent, messageReceived);
    }

    @Test
    public void testWrappers() throws Exception {
        //Writing message
        AllWrappersInOne messageSent = new AllWrappersInOne(
                (byte)1, (short)2, 3, 4,
                12f, 13d, 'c', true);
        Node nodeSent = LightSerializerWriter.getNode(messageSent);

        //Reading message
        AllWrappersInOne messageReceived = (AllWrappersInOne) LightSerializerReader.constructFromNode(nodeSent);

        Assert.assertEquals("Failure - objects are not equal", messageSent, messageReceived);
    }

    @Test
    public void testComplexNode() throws Exception {
        List<Person> students = new ArrayList<Person>();
        students.add(new Person("John", "Smith"));
        students.add(new Person("Nick", "Long"));
        students.add(new Person("Dave", "Brown"));
        Group messageSent = new Group(1, students, new Person("Angela", "Queen"));

        Node node = LightSerializerWriter.getNode(messageSent);

        Group messageReceived = (Group) LightSerializerReader.constructFromNode(node);

        Assert.assertEquals(messageSent, messageReceived);
    }

}