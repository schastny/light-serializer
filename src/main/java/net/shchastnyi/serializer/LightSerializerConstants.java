package net.shchastnyi.serializer;

public class LightSerializerConstants {

    public static final byte CLASS_START = 0x01;
    public static final byte CLASS_DELIMITER = 0x02;
    public static final byte CLASS_END = 0x03;

    public static final byte FIELD_START = 0x04;
    public static final byte FIELD_DELIMITER = 0x05;

    //Constants
    public static final String TYPE_STRING = "java.lang.String";
    public static final String TYPE_BYTE = "java.lang.Byte";
    public static final String TYPE_SHORT = "java.lang.Short";
    public static final String TYPE_INTEGER = "java.lang.Integer";
    public static final String TYPE_LONG = "java.lang.Long";
    public static final String TYPE_FLOAT = "java.lang.Float";
    public static final String TYPE_DOUBLE = "java.lang.Double";
    public static final String TYPE_BOOLEAN = "java.lang.Boolean";
    public static final String TYPE_CHARACTER = "java.lang.Character";

}
