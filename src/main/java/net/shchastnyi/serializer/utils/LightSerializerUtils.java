package net.shchastnyi.serializer.utils;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
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
    //!COMMON UTILS

    //TO BYTES
    public static byte[] primitiveToBytes(Object entity, String type) throws IllegalAccessException, IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        DataOutputStream streamResult = new DataOutputStream(stream);
        switch (type) {
            case TYPE_BYTE: case TYPE_BYTE_P: streamResult.writeByte((Byte) entity); break;
            case TYPE_SHORT: case TYPE_SHORT_P: streamResult.writeShort((Short) entity); break;
            case TYPE_INTEGER: case TYPE_INT_P: streamResult.writeInt((Integer) entity); break;
            case TYPE_LONG: case TYPE_LONG_P: streamResult.writeLong((Long) entity); break;
            case TYPE_FLOAT: case TYPE_FLOAT_P: streamResult.writeFloat((Float) entity); break;
            case TYPE_DOUBLE: case TYPE_DOUBLE_P: streamResult.writeDouble((Double) entity); break;
            case TYPE_BOOLEAN: case TYPE_BOOLEAN_P: streamResult.writeBoolean((Boolean) entity); break;
            case TYPE_CHARACTER: case TYPE_CHAR_P: streamResult.writeChar((Character) entity); break;
        }
        return stream.toByteArray();
    }
    //!TO BYTES

}
