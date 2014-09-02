package net.shchastnyi.serializer;

import net.shchastnyi.serializer.messages.Employee;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;

public class AppTest {

    private final String fileName = "d:/tmp/serial.ser";

    @Test
    public void testAssertEquals() throws Exception {
        //Writing message
        Employee outputMessage = new Employee("John", "Smith");
        byte[] outputBytes = LightSerializerWriter.serialize(outputMessage);
        OutputStream os = new FileOutputStream(fileName);
        os.write(outputBytes);
        os.flush();
        os.close();

        //Reading message
        byte[] inputBytes = Files.readAllBytes(new File(fileName).toPath());
        Employee inputMessage = LightSerializerReader.deSerialize(inputBytes);

        Assert.assertSame("failure - objects are not equal", outputMessage, inputMessage);
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


}