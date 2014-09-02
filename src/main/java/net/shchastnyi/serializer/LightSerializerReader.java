package net.shchastnyi.serializer;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.*;

import static net.shchastnyi.serializer.LightSerializerConstants.*;
import static net.shchastnyi.serializer.utils.LightSerializerUtils.byteArrayToList;
import static net.shchastnyi.serializer.utils.LightSerializerUtils.byteListToArray;

public class LightSerializerReader {

    public static <T> T deSerialize(byte[] bytes)
            throws ClassNotFoundException, NoSuchFieldException, InstantiationException, IllegalAccessException, IOException {
        ArrayDeque<Byte> bytesSequence = new ArrayDeque<>(byteArrayToList(bytes));
        if (bytesSequence.poll() != CLASS_START) {
            throw new IOException("Unknown file format");
        }

        // Read entityType
        String messageType = getMetaString(bytesSequence, CLASS_DELIMITER);

        // Read fiedls
        Map<String, Object> fields = new HashMap<>();
        Byte b = bytesSequence.poll(); //CLASS end or something else
        while (b != CLASS_END) {
            String fieldName = getMetaString(bytesSequence, FIELD_DELIMITER);
            String fieldType = getMetaString(bytesSequence, FIELD_DELIMITER);
            int lenght = ByteBuffer
                    .wrap(new byte[]{bytesSequence.poll(), bytesSequence.poll(), bytesSequence.poll(), bytesSequence.poll()})
                    .order(ByteOrder.BIG_ENDIAN)
                    .getInt();
            List<Byte> dataValue = new ArrayList<>();
            for (int count = 0; count < lenght; count++) {
                dataValue.add(bytesSequence.poll());
            }
            String fieldValue = new String(byteListToArray(dataValue));
            fields.put(fieldName, fieldValue);
            b = bytesSequence.poll(); //CLASS_END or FIELD_START
        }
        // !Read fiedls

        T s = constructObject(messageType, fields);
        return s;
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

    private static <T> T constructObject(String messageTypeString, Map<String, Object> fields)
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
