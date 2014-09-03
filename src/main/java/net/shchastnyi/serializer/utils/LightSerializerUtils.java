package net.shchastnyi.serializer.utils;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static net.shchastnyi.serializer.LightSerializerConstants.TYPE_INTEGER;
import static net.shchastnyi.serializer.LightSerializerConstants.TYPE_STRING;

public class LightSerializerUtils {

    //COMMON UTILS
    /**
     * Converting List<Byte> into byte[]
     * @param list
     * @return
     */
    public static byte[] byteListToArray(List<Byte> list) {
        if (list == null) return new byte[0];
        byte[] result = new byte[list.size()];
        for(int i = 0; i < list.size(); i++) result[i] = list.get(i);
        return result;
    }

    /**
     * Converting byte[] into List<Byte>
     * @param array
     * @return
     */
    public static List<Byte> byteArrayToList(byte[] array) {
        if (array == null) return Collections.emptyList();
        List<Byte> result = new ArrayList<>();
        for (byte b : array) result.add(b);
        return result;
    }

    /**
     * Convert given string to bytes (We need this to have one point of assigning encoding)
     * @param str
     * @return
     */
    public static List<Byte> stringBytes(String str) {
        return byteArrayToList(str.getBytes());
    }

    /**
     * Get list size and put it into byte array
     * @param list
     * @return
     */
    public static List<Byte> listLengthToBytes(List<Byte> list) {
        byte[] lengthInfo = ByteBuffer.allocate(4).putInt(list.size()).array();
        return byteArrayToList(lengthInfo);
    }
    //!COMMON UTILS

    //TO OBJECT
    public static Object bytesToObject(byte[] fieldBytes, String fieldType) {
        Object result = fieldBytes;
        switch (fieldType) {
            case TYPE_STRING: result = bytesToString(fieldBytes); break;
            case TYPE_INTEGER: result = bytesToInteger(fieldBytes); break;
        }
        return result;
    }

    public static String bytesToString(byte[] fieldBytes) {
        return new String(fieldBytes);
    }

    public static Integer bytesToInteger(byte[] fieldBytes) {
        int result = ByteBuffer.wrap(fieldBytes)
                .order(ByteOrder.BIG_ENDIAN)
                .getInt();
        return result;
    }
    //!TO OBJECT

    //TO BYTES
    public static List<Byte> objectToBytes(Object message, Field field) throws IllegalAccessException {
        List<Byte> fieldBytes = new ArrayList<>();
        switch (field.getType().getCanonicalName()) {
            case TYPE_STRING: fieldBytes.addAll(stringToBytes(message, field)); break;
            case TYPE_INTEGER: fieldBytes.addAll(integerToBytes(message, field)); break;
        }
        return fieldBytes;
    }

    public static List<Byte> stringToBytes(Object message, Field field) throws IllegalAccessException {
        String fieldValue = (String) field.get(message);
        return stringBytes(fieldValue);
    }

    public static List<Byte> integerToBytes(Object message, Field field) throws IllegalAccessException {
        Integer fieldValue = (Integer) field.get(message);
        byte[] byteArray = ByteBuffer.allocate(4).putInt(fieldValue).array();
        return byteArrayToList(byteArray);
    }
    //!TO BYTES


}
