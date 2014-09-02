package net.shchastnyi.serializer;

import net.shchastnyi.serializer.messages.Employee;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import static net.shchastnyi.serializer.LightSerializerConstants.*;

public class LightSerializer
{
    public static byte[] serialize(Object message) throws Exception {
        return serializeObject(message);
    }

    private static byte[] serializeObject(Object message) throws Exception {
        Class<?> messageClass = message.getClass();
        String canonicalName = messageClass.getCanonicalName();

        List<Byte> result = new ArrayList<>();
        result.add(CLASS_START);
        result.add(CLASS_NAME_START);
        for ( byte b : canonicalName.getBytes() ) {
            result.add(b);
        }
        result.add(CLASS_NAME_END);

        Field[] declaredFields = messageClass.getDeclaredFields();
        for (Field field : declaredFields) {
            result.add(FIELD_START);
            List<Byte> fieldBytes = new ArrayList<>();
            switch (field.getType().getCanonicalName()) {
                case "java.lang.String":
                    String fieldValue = (String) field.get(message); //TODO catch private fields access exception
                    fieldBytes.addAll(
                            byteArrayToList(fieldValue.getBytes())
                    );
                    break;
            }
            result.add(FIELD_DATA_LENGTH);
            byte[] lengthInfo = ByteBuffer.allocate(4).putInt(fieldBytes.size()).array();
            result.addAll(byteArrayToList(lengthInfo));
            result.addAll(fieldBytes);
            result.add(FIELD_END);
        }

        result.add(CLASS_END);

        return byteListToArray(result);
    }

    private static byte[] byteListToArray(List<Byte> list) {
        byte[] result = new byte[list.size()];
        for(int i = 0; i < list.size(); i++) result[i] = list.get(i);
        return result;
    }
    private static List<Byte> byteArrayToList(byte[] array) {
        List<Byte> result = new ArrayList<>();
        for (byte b : array) result.add(b);
        return result;
    }

    public static <T> T fromBytes(T clazz) {
        return null;
    }
    //!Objects

    public static void main( String[] args ) throws Exception {
        Object obj = new Employee("John", "Smith");
        byte[] bytes = LightSerializer.serialize(obj);
        OutputStream os = new FileOutputStream("d:/tmp/serial.ser");
        os.write(bytes);
        os.flush();
        os.close();
    }
}
