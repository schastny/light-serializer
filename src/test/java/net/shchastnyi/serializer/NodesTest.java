package net.shchastnyi.serializer;

import net.shchastnyi.serializer.messages.AllWrappersInOne;
import net.shchastnyi.serializer.messages.Group;
import net.shchastnyi.serializer.messages.Person;
import net.shchastnyi.serializer.messages.PersonCyclic;
import net.shchastnyi.serializer.node.Node;
import net.shchastnyi.serializer.node.NodeConstructor;
import net.shchastnyi.serializer.node.NodeDecoder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static net.shchastnyi.serializer.SerializationTestSuite.*;

public class NodesTest {

    private NodeConstructor nodeConstructor;
    private NodeDecoder nodeDecoder;

    @Before
    public void beforeTest() {
        nodeConstructor = new NodeConstructor();
        nodeDecoder = new NodeDecoder();
    }

    //TODO Get it workable
    //@Test
    public void testArray() throws Exception {
        //Writing message
        Character[] messageSent = getCharactersArray();
        Node nodeSent = nodeConstructor.getNode(messageSent);

        //Reading message
        Character[] messageReceived = (Character[]) nodeDecoder.constructFromNode(nodeSent);
        Assert.assertArrayEquals("Failure - objects are not equal", messageSent, messageReceived);
    }

    @Test
    public void testPerson() throws Exception {
        //Writing message
        Person messageSent = getPerson();
        Node nodeSent = nodeConstructor.getNode(messageSent);

        //Reading message
        Person messageReceived = (Person) nodeDecoder.constructFromNode(nodeSent);
        Assert.assertEquals(messageSent, messageReceived);
    }

    @Test
    public void testAllWrappersInOne() throws Exception {
        //Writing message
        AllWrappersInOne messageSent = getAllWrappersInOne();
        Node nodeSent = nodeConstructor.getNode(messageSent);

        //Reading message
        AllWrappersInOne messageReceived = (AllWrappersInOne) nodeDecoder.constructFromNode(nodeSent);
        Assert.assertEquals(messageSent, messageReceived);
    }

    @Test
    public void testGroup() throws Exception {
        //Writing message
        Group messageSent = getGroup();
        Node node = nodeConstructor.getNode(messageSent);

        //Reading message
        Group messageReceived = (Group) nodeDecoder.constructFromNode(node);
        Assert.assertEquals(messageSent, messageReceived);
    }

    /**
     * An exception should be thrown
     * @throws Exception
     */
    @Test(expected=RuntimeException.class)
    public void testPersonCyclic() throws Exception {
        PersonCyclic a = getPersonCyclic();
        nodeConstructor.getNode(a);
    }

    /**
     * Just to show the idea<br/>
     * An exception should be thrown
     * @throws Exception
     */
    @Test(expected=RuntimeException.class)
    public void testCyclicAlgorithm() throws Exception {
        PersonCyclic a = getPersonCyclic();
        checkRecAlgorithm(a, new HashSet<PersonCyclic>());
    }

    private void checkRecAlgorithm(PersonCyclic node, Set<PersonCyclic> stack) {
        if ( !stack.add(node) )
            throw new RuntimeException("Error! Cyclic referencing detected!");

        for (PersonCyclic child: node.children) {
            checkRecAlgorithm(child, stack);
        }

        stack.remove(node);
    }

}