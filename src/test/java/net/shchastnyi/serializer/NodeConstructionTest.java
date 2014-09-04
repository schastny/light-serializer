package net.shchastnyi.serializer;

import net.shchastnyi.serializer.messages.AllWrappersInOne;
import net.shchastnyi.serializer.messages.Group;
import net.shchastnyi.serializer.messages.Person;
import net.shchastnyi.serializer.node.Node;
import net.shchastnyi.serializer.node.NodeConstructor;
import net.shchastnyi.serializer.node.NodeDecoder;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class NodeConstructionTest {

    @Test
    public void testSimpleBeanWithPublicFields() throws Exception {
        //Writing message
        Person messageSent = new Person("John", "Smith");
        Node nodeSent = NodeConstructor.getNode(messageSent);

        //Reading message
        Person messageReceived = (Person) NodeDecoder.constructFromNode(nodeSent);

        Assert.assertEquals("Failure - objects are not equal", messageSent, messageReceived);
    }

    @Test
    public void testWrappers() throws Exception {
        //Writing message
        AllWrappersInOne messageSent = new AllWrappersInOne(
                (byte)1, (short)2, 3, 4,
                12f, 13d, 'c', true);
        Node nodeSent = NodeConstructor.getNode(messageSent);

        //Reading message
        AllWrappersInOne messageReceived = (AllWrappersInOne) NodeDecoder.constructFromNode(nodeSent);

        Assert.assertEquals("Failure - objects are not equal", messageSent, messageReceived);
    }

    @Test
    public void testComplexNode() throws Exception {
        List<Person> students = new ArrayList<Person>();
        students.add(new Person("John", "Smith"));
        students.add(new Person("Nick", "Long"));
        students.add(new Person("Dave", "Brown"));
        Group messageSent = new Group(1, students, new Person("Angela", "Queen"));

        Node node = NodeConstructor.getNode(messageSent);

        Group messageReceived = (Group) NodeDecoder.constructFromNode(node);

        Assert.assertEquals(messageSent, messageReceived);
    }

}