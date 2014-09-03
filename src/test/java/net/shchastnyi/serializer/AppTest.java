package net.shchastnyi.serializer;

import net.shchastnyi.serializer.messages.AllWrappersInOne;
import net.shchastnyi.serializer.messages.Person;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;

public class AppTest {

    private static final String TMP_DIR = "d:/tmp";

    @Test
    public void simpleBeanWithPublicFields() throws Exception {
        //Writing message
        Person messageSent = new Person("John", "Smith");
        byte[] bytesSent = LightSerializerWriter.serialize(messageSent);

        //Reading message
        Person messageReceived = LightSerializerReader.deSerialize(bytesSent);

        Assert.assertEquals("Failure - objects are not equal", messageSent, messageReceived);
    }

    @Test
    public void wrappersTest() throws Exception {
        //Writing message
        AllWrappersInOne messageSent = new AllWrappersInOne(
                (byte)1, (short)2, 3, 4,
                12f, 13d, 'c', true);
        byte[] bytesSent = LightSerializerWriter.serialize(messageSent);

        //Reading message
        AllWrappersInOne messageReceived = LightSerializerReader.deSerialize(bytesSent);

        Assert.assertEquals("Failure - objects are not equal", messageSent, messageReceived);
    }

    //TODO Inherited fields
    //TODO private fields without getters
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

    @AfterClass
    public static void clean() {
        File dir = new File(TMP_DIR);
        for (File file : dir.listFiles()) {
            file.delete();
        }
        dir.delete();
    }

}