package net.shchastnyi.serializer.utils;

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
    public static List<Byte> stringToBytes(String str) {
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
    private static final ByteOrder ORDER = ByteOrder.BIG_ENDIAN;

    public static Object bytesToObject(byte[] fieldBytes, String fieldType) {
        Object result = null;
        switch (fieldType) {
            case TYPE_BYTE: case TYPE_BYTE_P: result = bytesToByte(fieldBytes); break;
            case TYPE_SHORT: case TYPE_SHORT_P: result = bytesToShort(fieldBytes); break;
            case TYPE_INTEGER: case TYPE_INT_P: result = bytesToInteger(fieldBytes); break;
            case TYPE_LONG: case TYPE_LONG_P: result = bytesToLong(fieldBytes); break;
            case TYPE_FLOAT: case TYPE_FLOAT_P: result = bytesToFloat(fieldBytes); break;
            case TYPE_DOUBLE: case TYPE_DOUBLE_P: result = bytesToDouble(fieldBytes); break;
            case TYPE_BOOLEAN: case TYPE_BOOLEAN_P: result = bytesToBoolean(fieldBytes); break;
            case TYPE_CHARACTER: case TYPE_CHAR_P: result = bytesToCharacter(fieldBytes); break;
            default: throw new RuntimeException("Can't get wrapper for type: " + fieldType);
        }
        return result;
    }

    public static Byte bytesToByte(byte[] fieldBytes) {
        return ByteBuffer.wrap(fieldBytes).order(ORDER).get();
    }

    public static Short bytesToShort(byte[] fieldBytes) {
        return ByteBuffer.wrap(fieldBytes).order(ORDER).getShort();
    }

    public static Integer bytesToInteger(byte[] fieldBytes) {
        return ByteBuffer.wrap(fieldBytes).order(ORDER).getInt();
    }

    public static Long bytesToLong(byte[] fieldBytes) {
        return ByteBuffer.wrap(fieldBytes).order(ORDER).getLong();
    }

    public static Float bytesToFloat(byte[] fieldBytes) {
        return ByteBuffer.wrap(fieldBytes).order(ORDER).getFloat();
    }

    public static Double bytesToDouble(byte[] fieldBytes) {
        return ByteBuffer.wrap(fieldBytes).order(ORDER).getDouble();
    }

    public static Boolean bytesToBoolean(byte[] fieldBytes) {
        return ByteBuffer.wrap(fieldBytes).order(ORDER).get() != 0;
    }

    public static Character bytesToCharacter(byte[] fieldBytes) {
        return ByteBuffer.wrap(fieldBytes).order(ORDER).getChar();
    }
    //!TO OBJECT

    //TO BYTES
    public static byte[] primitiveToBytes(Object entity, String type) throws IllegalAccessException {
        List<Byte> fieldBytes = new ArrayList<>();
        switch (type) {
            case TYPE_BYTE: case TYPE_BYTE_P: fieldBytes.addAll(byteToBytes((Byte) entity)); break;
            case TYPE_SHORT: case TYPE_SHORT_P: fieldBytes.addAll(shortToBytes((Short) entity)); break;
            case TYPE_INTEGER: case TYPE_INT_P: fieldBytes.addAll(integerToBytes((Integer) entity)); break;
            case TYPE_LONG: case TYPE_LONG_P: fieldBytes.addAll(longToBytes((Long) entity)); break;
            case TYPE_FLOAT: case TYPE_FLOAT_P: fieldBytes.addAll(floatToBytes((Float) entity)); break;
            case TYPE_DOUBLE: case TYPE_DOUBLE_P: fieldBytes.addAll(doubleToBytes((Double) entity)); break;
            case TYPE_BOOLEAN: case TYPE_BOOLEAN_P: fieldBytes.addAll(booleanToBytes((Boolean) entity)); break;
            case TYPE_CHARACTER: case TYPE_CHAR_P: fieldBytes.addAll(characterToBytes((Character) entity)); break;
        }
        return byteListToArray(fieldBytes);
    }

//    public static List<Byte> referenceToBytes(Object fieldValue){
//        return primitiveToBytes(fieldValue);
//    }

    public static List<Byte> byteToBytes(Byte fieldValue){
        byte[] byteArray = ByteBuffer.allocate(1).put(fieldValue).array();
        return byteArrayToList(byteArray);
    }

    public static List<Byte> shortToBytes(Short fieldValue){
        byte[] byteArray = ByteBuffer.allocate(2).putShort(fieldValue).array();
        return byteArrayToList(byteArray);
    }

    public static List<Byte> integerToBytes(Integer fieldValue){
        byte[] byteArray = ByteBuffer.allocate(4).putInt(fieldValue).array();
        return byteArrayToList(byteArray);
    }

    public static List<Byte> longToBytes(Long fieldValue){
        byte[] byteArray = ByteBuffer.allocate(8).putLong(fieldValue).array();
        return byteArrayToList(byteArray);
    }

    public static List<Byte> floatToBytes(Float fieldValue){
        byte[] byteArray = ByteBuffer.allocate(4).putFloat(fieldValue).array();
        return byteArrayToList(byteArray);
    }

    public static List<Byte> doubleToBytes(Double fieldValue){
        byte[] byteArray = ByteBuffer.allocate(8).putDouble(fieldValue).array();
        return byteArrayToList(byteArray);
    }

    public static List<Byte> booleanToBytes(Boolean fieldValue) {
        byte storingValue = (byte) (fieldValue ? 1 : 0);
        byte[] byteArray = ByteBuffer.allocate(1).put(storingValue).array();
        return byteArrayToList(byteArray);
    }

    public static List<Byte> characterToBytes(Character message) {
        byte[] byteArray = ByteBuffer.allocate(2).putChar(message).array();
        return byteArrayToList(byteArray);
    }
    //!TO BYTES


}
