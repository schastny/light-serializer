package net.shchastnyi.serializer;

import net.shchastnyi.serializer.messages.AllWrappersInOne;
import net.shchastnyi.serializer.messages.Group;
import net.shchastnyi.serializer.messages.Person;
import org.junit.*;

import java.io.File;

import static net.shchastnyi.serializer.SerializationTestSuite.getAllWrappersInOne;
import static net.shchastnyi.serializer.SerializationTestSuite.getGroup;

public class BytesTest {

    private static final String TMP_DIR = "d:/tmp";
    private static final String fileName = TMP_DIR+"/simple.ser";
    private LightSerializerWriter lightSerializerWriter;
    private LightSerializerReader lightSerializerReader;

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

    @Test
    public void testPerson() throws Exception {
        //Writing message
        Person messageSent = SerializationTestSuite.getPerson();
        byte[] bytesSent = lightSerializerWriter.serialize(messageSent);

        //Reading message
        Person messageReceived = lightSerializerReader.deSerialize(bytesSent);
        Assert.assertEquals(messageSent, messageReceived);
    }

//    @Test
    public void testAllWrappersInOne() throws Exception {
        //Writing message
        AllWrappersInOne messageSent = getAllWrappersInOne();
        byte[] bytesSent = lightSerializerWriter.serialize(messageSent);

        //Reading message
        AllWrappersInOne messageReceived = lightSerializerReader.deSerialize(bytesSent);
        Assert.assertEquals(messageSent, messageReceived);
    }

    //    @Test
    public void testGroup() throws Exception {
        //Writing message
        Group messageSent = getGroup();
        byte[] bytesSent = lightSerializerWriter.serialize(messageSent);

        //Reading message
        Group messageReceived = lightSerializerReader.deSerialize(bytesSent);
        Assert.assertEquals("Failure - objects are not equal", messageSent, messageReceived);
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

}