package net.shchastnyi.serializer.messages;

import net.shchastnyi.serializer.LightSerializer;

import java.io.FileOutputStream;
import java.io.OutputStream;

public class App {

    public static void main( String[] args ) throws Exception {
        Object message = new Employee("John", "Smith");
        byte[] bytes = LightSerializer.serialize(message);
        OutputStream os = new FileOutputStream("d:/tmp/serial.ser");
        os.write(bytes);
        os.flush();
        os.close();
    }

}
