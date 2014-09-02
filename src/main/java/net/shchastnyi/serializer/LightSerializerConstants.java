package net.shchastnyi.serializer;

public class LightSerializerConstants {

    public static final byte CLASS_START = 0x01;
    public static final byte CLASS_END = 0x02;
    public static final byte CLASS_NAME_START = 0x03;
    public static final byte CLASS_NAME_END = 0x04;

    public static final byte FIELD_START = 0x05;
    public static final byte FIELD_DATA_LENGTH = 0x06;
    public static final byte FIELD_END = 0x07;

}
