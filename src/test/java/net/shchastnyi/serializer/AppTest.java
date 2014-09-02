package net.shchastnyi.serializer;

import net.shchastnyi.serializer.messages.Person;
import org.junit.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;

public class AppTest {

    private static final String TMP_DIR = "d:/tmp";

    @Test
    public void simpleBeanWithPublicFields() throws Exception {
        String simpleFile = TMP_DIR +"/serial.ser";

        //Writing message
        Person messageSent = new Person("John", "Smith");
        byte[] bytesSent = LightSerializerWriter.serialize(messageSent);
        OutputStream os = new FileOutputStream(simpleFile);
        os.write(bytesSent);
        os.flush();
        os.close();

        //Reading message
        byte[] bytesReceived = Files.readAllBytes(new File(simpleFile).toPath());
        Person messageReceived = LightSerializerReader.deSerialize(bytesReceived);

        Assert.assertEquals("Failure - objects are not equal", messageSent, messageReceived);
    }

    //TODO Inherited fields
    //TODO private fields without getters
    //TODO transient mark
    //TODO BigEndian/LittleEndian

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