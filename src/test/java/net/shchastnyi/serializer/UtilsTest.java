package net.shchastnyi.serializer;

import net.shchastnyi.serializer.utils.LightSerializerUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class UtilsTest {

    @Test
    public void testListToArayFunction() throws Exception {
        byte[] sampleArr = {12, 13, 14};

        List<Byte> list = new ArrayList<Byte>(){{
            add((byte)12);
            add((byte)13);
            add((byte)14);
        }};
        byte[] targetArr = LightSerializerUtils.byteListToArray(list);

        assertArrayEquals("Fail: Arrays are not equal.", sampleArr, targetArr);
    }

    @Test
    public void testStringBytes() {
        final String sampleString = "Test";

        List<Byte> bytes = LightSerializerUtils.stringBytes(sampleString);
        byte[] sample = LightSerializerUtils.byteListToArray(bytes);
        String targetString = new String(sample);

        assertEquals("Fail: Strings do not match", sampleString, targetString);
    }

}