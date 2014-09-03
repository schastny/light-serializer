package net.shchastnyi.serializer;

import java.lang.reflect.Field;
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
 *  TYPE
 *  FIELD_DATA_LENGTH
 *  1234
 *  DATA
 * CLASS_END
 * <pre/>
 */
public class LightSerializerWriter {

    public static byte[] serialize(Object message) throws IllegalAccessException {
        List<Byte> result = new ArrayList<>();
        result.add(CLASS_START);
        result.addAll(stringBytes(message.getClass().getCanonicalName()));
        result.add(CLASS_DELIMITER);

        for (Field field : message.getClass().getDeclaredFields()) {
            List<Byte> fieldBytes = getFieldBytes(message, field);
            result.addAll(fieldBytes);
        }

        result.add(CLASS_END);
        return byteListToArray(result);
    }

    private static List<Byte> getFieldBytes(Object message, Field field) throws IllegalAccessException {
        List<Byte> result = new ArrayList<>();

        result.add(FIELD_START);
        result.addAll(stringBytes(field.getName()));
        result.add(FIELD_DELIMITER);

        result.addAll(stringBytes(field.getType().getCanonicalName()));
        result.add(FIELD_DELIMITER);

        List<Byte> fieldBytes = objectToBytes(message, field);
        result.addAll(listLengthToBytes(fieldBytes));
        result.addAll(fieldBytes);

        return result;
    }

}
