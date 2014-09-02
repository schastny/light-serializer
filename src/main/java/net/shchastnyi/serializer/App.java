package net.shchastnyi.serializer;

import net.shchastnyi.serializer.messages.Employee;

import java.io.*;
import java.nio.file.Files;

public class App {

    public static void main( String[] args ) throws Exception {
        String fileName = "d:/tmp/serial.ser";

        //Writing message
        Object outputMessage = new Employee("John", "Smith");
        byte[] outputBytes = LightSerializerWriter.serialize(outputMessage);

        OutputStream os = new FileOutputStream(fileName);
        os.write(outputBytes);
        os.flush();
        os.close();

        //Reading message
        byte[] inputBytes = Files.readAllBytes(
            new File(fileName).toPath()
        );
        Employee inputMessage = LightSerializerReader.deSerialize(inputBytes);
        System.out.println(inputMessage == outputMessage);

    }

}
