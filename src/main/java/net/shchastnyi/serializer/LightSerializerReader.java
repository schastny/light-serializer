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
            throws ClassNotFoundException, NoSuchFieldException,
            InstantiationException, IllegalAccessException, IOException {
        return deSerializeObject(bytes);
    }

    private static <T> T deSerializeObject(byte[] bytes)
            throws IOException, ClassNotFoundException,
            IllegalAccessException, InstantiationException, NoSuchFieldException {
        ArrayDeque<Byte> bytesSequence = new ArrayDeque<>(byteArrayToList(bytes));
        if (bytesSequence.poll() != CLASS_START) {
            throw new IOException("Unknown file format");
        }

        byte b = bytesSequence.poll(); //First byte of message type
        List<Byte> messageType = new ArrayList<>();
        while (b != CLASS_DELIMITER) {
            messageType.add(b);
            b = bytesSequence.poll();
        }
        String messageTypeString = new String(byteListToArray(messageType));

        Map<String, Object> fields = new HashMap<>();
        b = bytesSequence.poll(); //CLASS end or something else
        while (b != CLASS_END) {
            b = bytesSequence.poll(); //FIELD_START
            List<Byte> fieldName = new ArrayList<>();
            while (b != FIELD_DELIMITER) {
                fieldName.add(b);
                b = bytesSequence.poll();
            }
            String fieldNameString = new String(byteListToArray(fieldName));
            int lenght = ByteBuffer
                    .wrap(new byte[]{bytesSequence.poll(), bytesSequence.poll(), bytesSequence.poll(), bytesSequence.poll()})
                    .order(ByteOrder.BIG_ENDIAN)
                    .getInt();
            List<Byte> dataValue = new ArrayList<>();
            for (int count = 0; count < lenght; count++) {
                dataValue.add(bytesSequence.poll());
            }
            String fieldValue = new String(byteListToArray(dataValue));
            fields.put(fieldNameString, fieldValue);
            b = bytesSequence.poll(); //CLASS_END or FIELD_START
        }

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
