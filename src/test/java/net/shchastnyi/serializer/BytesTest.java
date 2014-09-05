package net.shchastnyi.serializer;

import net.shchastnyi.serializer.messages.AllWrappersInOne;
import net.shchastnyi.serializer.messages.Group;
import net.shchastnyi.serializer.messages.Person;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class BytesTest {

    private static final String TMP_DIR = "d:/tmp";
    private static final String fileName = TMP_DIR+"/simple.ser";
    private LightSerializerWriter lightSerializerWriter;
    private LightSerializerReader lightSerializerReader;

    @Test
    public void testSimpleBeanWithPublicFields() throws Exception {
        //Writing message
        Person messageSent = new Person("John", "Smith");
        byte[] bytesSent = lightSerializerWriter.serialize(messageSent);

        //Reading message
        Person messageReceived = lightSerializerReader.deSerialize(bytesSent);

//        Assert.assertEquals("Failure - objects are not equal", messageSent, messageReceived);
    }

//    @Test
    public void testWrappers() throws Exception {
        //Writing message
        AllWrappersInOne messageSent = new AllWrappersInOne(
                (byte)1, (short)2, 3, 4,
                12f, 13d, 'c', true);
//        byte[] bytesSent = LightSerializerWriter.serialize(messageSent);

        //Reading message
//        AllWrappersInOne messageReceived = LightSerializerReader.deSerialize(bytesSent);

//        Assert.assertEquals("Failure - objects are not equal", messageSent, messageReceived);
    }

//    @Test
    public void testReferences() throws Exception {
        //Writing message
        List<Person> students = new ArrayList<Person>();
        students.add(new Person("John", "Smith"));
        students.add(new Person("Nick", "Long"));
        students.add(new Person("Dave", "Brown"));
        Group messageSent = new Group(1, students, new Person("Angela", "Queen"));

        Person messageSent2 = new Person("John", "Smith");
        String bytesSent = new LightSerializerWriter().serializeDebug(messageSent);
        byte[] bytesSent2 = new LightSerializerWriter().serialize(messageSent);
//        Writer wr = new BufferedWriter(new FileWriter(fileName));
        OutputStream wr = new FileOutputStream(fileName);
        wr.write(bytesSent2);
        wr.flush();
        wr.close();

        //Reading message
//        AllWrappersInOne messageReceived = LightSerializerReader.deSerialize(bytesSent);
//        AllWrappersInOne messageReceived = LightSerializerReader.deSerialize(bytesSent);

//        Assert.assertEquals("Failure - objects are not equal", messageSent, messageReceived);
    }

    //TODO Inherited fields
    //TODO transient mark
    //TODO BigEndian/LittleEndian
    //TODO cyclic references

    //TODO default values
    //TODO recreate objects (constructors, setters) - check for default constructor
    //TODO multiple objects at a file

    //TODO string encoding
    //TODO switch for primitives|arrays|enums|all_other_objects

    @BeforeClass
    public static void prepare() {
        File dir = new File(TMP_DIR);
        dir.mkdir();
    }

    @Before
    public void beforeTest() {
        lightSerializerWriter = new LightSerializerWriter();
        lightSerializerReader = new LightSerializerReader();
    }

    @AfterClass
    public static void clean() {
        File dir = new File(TMP_DIR);
        for (File file : dir.listFiles()) {
            file.delete();
        }
        dir.delete();
    }

}