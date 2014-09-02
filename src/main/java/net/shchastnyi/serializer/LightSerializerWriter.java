package net.shchastnyi.serializer;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import static net.shchastnyi.serializer.LightSerializerConstants.*;
import static net.shchastnyi.serializer.utils.LightSerializerUtils.*;

/**
 * <pre>
 * CLASS_START
 * XXXXXX
 * CLASS_DELIMITER
 *  FIELD_START
 *  YYYYYYY
 *  FIELD_DATA_LENGTH
 *  1234
 *  DATA
 * CLASS_END
 * <pre/>
 */
public class LightSerializerWriter
{
    public static byte[] serialize(Object message) throws Exception {
        return serializeObject(message);
    }

    private static byte[] serializeObject(Object message) throws Exception {
        Class<?> messageClass = message.getClass();
        String canonicalName = messageClass.getCanonicalName();

        List<Byte> result = new ArrayList<>();
        result.add(CLASS_START);
        result.addAll(byteArrayToList(canonicalName.getBytes()));
        result.add(CLASS_DELIMITER);

        Field[] declaredFields = messageClass.getDeclaredFields();
        for (Field field : declaredFields) {
            result.add(FIELD_START);
            result.addAll(
                    byteArrayToList(field.getName().getBytes())
            );
            result.add(FIELD_DELIMITER);

            List<Byte> fieldData = new ArrayList<>();
            switch (field.getType().getCanonicalName()) {
                case "java.lang.String":
                    String fieldValue = (String) field.get(message); //TODO catch private fields access exception
                    fieldData.addAll(byteArrayToList(fieldValue.getBytes()));
                    break;
            }
            byte[] lengthInfo = ByteBuffer.allocate(4).putInt(fieldData.size()).array();
            result.addAll(byteArrayToList(lengthInfo));
            result.addAll(fieldData);
        }

        result.add(CLASS_END);

        return byteListToArray(result);
    }

}
