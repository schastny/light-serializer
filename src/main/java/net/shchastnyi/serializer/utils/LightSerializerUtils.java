package net.shchastnyi.serializer.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LightSerializerUtils {

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

}
