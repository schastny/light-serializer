package net.shchastnyi.serializer.utils;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static net.shchastnyi.serializer.LightSerializerConstants.*;

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

            case TYPE_BYTE:     result = bytesToByte(fieldBytes); break;
            case TYPE_BYTE_P:   result = bytesToByte(fieldBytes); break;

            case TYPE_SHORT:    result = bytesToShort(fieldBytes); break;
            case TYPE_SHORT_P:  result = bytesToShort(fieldBytes); break;

            case TYPE_INTEGER:  result = bytesToInteger(fieldBytes); break;
            case TYPE_INT_P:    result = bytesToInteger(fieldBytes); break;

            case TYPE_LONG:     result = bytesToLong(fieldBytes); break;
            case TYPE_LONG_P:   result = bytesToLong(fieldBytes); break;

            case TYPE_FLOAT:    result = bytesToFloat(fieldBytes); break;
            case TYPE_FLOAT_P:  result = bytesToFloat(fieldBytes); break;

            case TYPE_DOUBLE:   result = bytesToDouble(fieldBytes); break;
            case TYPE_DOUBLE_P: result = bytesToDouble(fieldBytes); break;

            case TYPE_BOOLEAN: result = bytesToBoolean(fieldBytes); break;
            case TYPE_BOOLEAN_P: result = bytesToBoolean(fieldBytes); break;

            case TYPE_CHARACTER: result = bytesToCharacter(fieldBytes); break;
            case TYPE_CHAR_P: result = bytesToCharacter(fieldBytes); break;

            default: throw new RuntimeException("Can't get wrapper for type: "+fieldType);
        }
        return result;
    }

    public static String bytesToString(byte[] fieldBytes) {
        return new String(fieldBytes);
    }

    public static Byte bytesToByte(byte[] fieldBytes) {
        return ByteBuffer.wrap(fieldBytes).order(ByteOrder.BIG_ENDIAN).get();
    }

    public static Short bytesToShort(byte[] fieldBytes) {
        return ByteBuffer.wrap(fieldBytes).order(ByteOrder.BIG_ENDIAN).getShort();
    }

    public static Integer bytesToInteger(byte[] fieldBytes) {
        return ByteBuffer.wrap(fieldBytes).order(ByteOrder.BIG_ENDIAN).getInt();
    }

    public static Long bytesToLong(byte[] fieldBytes) {
        return ByteBuffer.wrap(fieldBytes).order(ByteOrder.BIG_ENDIAN).getLong();
    }

    public static Float bytesToFloat(byte[] fieldBytes) {
        return ByteBuffer.wrap(fieldBytes).order(ByteOrder.BIG_ENDIAN).getFloat();
    }

    public static Double bytesToDouble(byte[] fieldBytes) {
        return ByteBuffer.wrap(fieldBytes).order(ByteOrder.BIG_ENDIAN).getDouble();
    }

    public static Boolean bytesToBoolean(byte[] fieldBytes) {
        return ByteBuffer.wrap(fieldBytes).order(ByteOrder.BIG_ENDIAN).getShort() != 0;
    }

    public static Character bytesToCharacter(byte[] fieldBytes) {
        return ByteBuffer.wrap(fieldBytes).order(ByteOrder.BIG_ENDIAN).getChar();
    }
    //!TO OBJECT

    //TO BYTES
    public static List<Byte> objectToBytes(Object message, Field field) throws IllegalAccessException {
        List<Byte> fieldBytes = new ArrayList<>();
        switch (field.getType().getCanonicalName()) {
            case TYPE_STRING: fieldBytes.addAll(stringToBytes(message, field)); break;

            case TYPE_BYTE:     fieldBytes.addAll(byteToBytes(message, field)); break;
            case TYPE_BYTE_P:   fieldBytes.addAll(byteToBytes(message, field)); break;

            case TYPE_SHORT:    fieldBytes.addAll(shortToBytes(message, field)); break;
            case TYPE_SHORT_P:  fieldBytes.addAll(shortToBytes(message, field)); break;

            case TYPE_INTEGER:  fieldBytes.addAll(integerToBytes(message, field)); break;
            case TYPE_INT_P:    fieldBytes.addAll(integerToBytes(message, field)); break;

            case TYPE_LONG:     fieldBytes.addAll(longToBytes(message, field)); break;
            case TYPE_LONG_P:   fieldBytes.addAll(longToBytes(message, field)); break;

            case TYPE_FLOAT:    fieldBytes.addAll(floatToBytes(message, field)); break;
            case TYPE_FLOAT_P:  fieldBytes.addAll(floatToBytes(message, field)); break;

            case TYPE_DOUBLE:   fieldBytes.addAll(doubleToBytes(message, field)); break;
            case TYPE_DOUBLE_P: fieldBytes.addAll(doubleToBytes(message, field)); break;

            case TYPE_BOOLEAN:  fieldBytes.addAll(booleanToBytes(message, field)); break;
            case TYPE_BOOLEAN_P:fieldBytes.addAll(booleanToBytes(message, field)); break;

            case TYPE_CHARACTER:fieldBytes.addAll(characterToBytes(message, field)); break;
            case TYPE_CHAR_P:   fieldBytes.addAll(characterToBytes(message, field)); break;

            default: throw new RuntimeException("Can't get wrapper for type: "+field.getType());
        }
        return fieldBytes;
    }

    public static List<Byte> stringToBytes(Object message, Field field) throws IllegalAccessException {
        String fieldValue = (String) field.get(message);
        return stringBytes(fieldValue);
    }

    public static List<Byte> byteToBytes(Object message, Field field) throws IllegalAccessException {
        Byte fieldValue = (Byte) field.get(message);
        byte[] byteArray = ByteBuffer.allocate(1).put(fieldValue).array();
        return byteArrayToList(byteArray);
    }

    public static List<Byte> shortToBytes(Object message, Field field) throws IllegalAccessException {
        Short fieldValue = (Short) field.get(message);
        byte[] byteArray = ByteBuffer.allocate(2).putShort(fieldValue).array();
        return byteArrayToList(byteArray);
    }

    public static List<Byte> integerToBytes(Object message, Field field) throws IllegalAccessException {
        Integer fieldValue = (Integer) field.get(message);
        byte[] byteArray = ByteBuffer.allocate(4).putInt(fieldValue).array();
        return byteArrayToList(byteArray);
    }

    public static List<Byte> longToBytes(Object message, Field field) throws IllegalAccessException {
        Long fieldValue = (Long) field.get(message);
        byte[] byteArray = ByteBuffer.allocate(8).putLong(fieldValue).array();
        return byteArrayToList(byteArray);
    }

    public static List<Byte> floatToBytes(Object message, Field field) throws IllegalAccessException {
        Float fieldValue = (Float) field.get(message);
        byte[] byteArray = ByteBuffer.allocate(4).putFloat(fieldValue).array();
        return byteArrayToList(byteArray);
    }

    public static List<Byte> doubleToBytes(Object message, Field field) throws IllegalAccessException {
        Double fieldValue = (Double) field.get(message);
        byte[] byteArray = ByteBuffer.allocate(8).putDouble(fieldValue).array();
        return byteArrayToList(byteArray);
    }

    public static List<Byte> booleanToBytes(Object message, Field field) throws IllegalAccessException {
        Boolean fieldValue = (Boolean) field.get(message);
        byte storingValue = (byte) (fieldValue ? 1 : 0);
        byte[] byteArray = ByteBuffer.allocate(1).put(storingValue).array();
        return byteArrayToList(byteArray);
    }

    public static List<Byte> characterToBytes(Object message, Field field) throws IllegalAccessException {
        Character fieldValue = (Character) field.get(message);
        byte[] byteArray = ByteBuffer.allocate(2).putChar(fieldValue).array();
        return byteArrayToList(byteArray);
    }
    //!TO BYTES


}
