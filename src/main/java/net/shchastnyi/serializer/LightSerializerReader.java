package net.shchastnyi.serializer;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.*;

import static net.shchastnyi.serializer.LightSerializerConstants.*;
import static net.shchastnyi.serializer.utils.LightSerializerUtils.*;

public class LightSerializerReader {

//    public static <T> T deSerialize(byte[] bytes)
    public static <T> T deSerialize(byte[] bytes)
            throws ClassNotFoundException, NoSuchFieldException, InstantiationException, IllegalAccessException, IOException {
        ArrayDeque<Byte> bytesSequence = new ArrayDeque<>(byteArrayToList(bytes));
        if (bytesSequence.poll() != CLASS_START) {
            throw new IOException("Unknown file format");
        }

        // Read entityType
//        String messageType = getMetaString(bytesSequence, DELIMITER_DEBUG);

        // Read fiedls
        Map<String, Object> fields = new HashMap<>();
        Map<String, Object> fieldTypes = new HashMap<>();
        Byte b = bytesSequence.poll(); //CLASS end or something else
//        while (b != CLASS_END_DEBUG) {
//            String fieldName = getMetaString(bytesSequence, FIELD_DELIMITER);
//            String fieldType = getMetaString(bytesSequence, FIELD_DELIMITER);
//            byte[] fieldBytes = byteListToArray(getFieldBytes(bytesSequence));
//            Object fieldValue = bytesToObject(fieldBytes, fieldType);
//
//            fields.put(fieldName, fieldValue);
//            fieldTypes.put(fieldName, fieldType);
//
//            b = bytesSequence.poll(); //CLASS_END_DEBUG or FIELD_START
//        }
        // !Read fiedls

//        T s = constructObject(messageType, fields, fieldTypes);
//        return s;
        return null;
    }

    private static List<Byte> getFieldBytes(ArrayDeque<Byte> bytesSequence) {
        int lenght = ByteBuffer
                .wrap(new byte[]{bytesSequence.poll(), bytesSequence.poll(), bytesSequence.poll(), bytesSequence.poll()})
                .order(ByteOrder.BIG_ENDIAN)
                .getInt();
        List<Byte> data = new ArrayList<>();
        for (int count = 0; count < lenght; count++) {
            data.add(bytesSequence.poll());
        }
        return data;
    }

    private static String getMetaString(ArrayDeque<Byte> bytesSequence, byte delimiter) {
        byte b = bytesSequence.poll(); //First byte of message type
        List<Byte> typeBytes = new ArrayList<>();
        while (b != delimiter) {
            typeBytes.add(b);
            b = bytesSequence.poll();
        }
        return new String(byteListToArray(typeBytes));
    }

    @SuppressWarnings("unchecked")
    private static <T> T constructObject(String messageTypeString, Map<String, Object> fields, Map<String, Object> fieldTypes)
            throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, InstantiationException {
        Class<?> clazz = Class.forName(messageTypeString);
        T s = (T) clazz.newInstance();
        for (String key : fields.keySet()) {
            Field field = clazz.getDeclaredField(key);
            field.setAccessible(true);
            field.set(s, fields.get(key));
        }
        return s;
    }

}
