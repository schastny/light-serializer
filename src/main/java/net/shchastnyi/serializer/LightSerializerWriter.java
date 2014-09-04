package net.shchastnyi.serializer;

import java.lang.reflect.Array;
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

    public static Node getNode(Object entity) throws Exception {
        Node root = getNode("root", entity);
        return root;
    }

    public static Node getNode(String nodeName, Object entity) throws Exception {
        String nodeType = entity.getClass().getCanonicalName();
        Node node = Node.node(nodeName, nodeType);

        Field[] declaredFields = entity.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            Node childNode;
            Class<?> fieldType = field.getType();
            String fieldTypeName = fieldType.getCanonicalName();

            if ( fieldType.isArray() ) {
                childNode = Node.node(field.getName(), fieldTypeName);
                Object childArray = field.get(entity);
                for (int i = 0; i < Array.getLength(childArray); i++) {
                    String arrayElementType = fieldType.getCanonicalName().substring(0,fieldType.getCanonicalName().length()-2);
                    Object arrayElement = Array.get(childArray, i);
                    if ( arrayElement != null ) {
                        arrayElementType = arrayElement.getClass().getCanonicalName();
                    }
                    Node childArrayNode = Node.node("arrayElement", arrayElementType, arrayElement);
                    childNode.addChild(childArrayNode);
                }
            }
            else {
                switch (fieldTypeName) {
                    case TYPE_BYTE: case TYPE_BYTE_P:
                    case TYPE_SHORT: case TYPE_SHORT_P:
                    case TYPE_INTEGER: case TYPE_INT_P:
                    case TYPE_LONG: case TYPE_LONG_P:
                    case TYPE_FLOAT: case TYPE_FLOAT_P:
                    case TYPE_DOUBLE: case TYPE_DOUBLE_P:
                    case TYPE_BOOLEAN: case TYPE_BOOLEAN_P:
                    case TYPE_CHARACTER: case TYPE_CHAR_P:
                        childNode = Node.node(field.getName(), fieldTypeName, field.get(entity)); break;
                    default: childNode = getNode(field.getName(), field.get(entity));
                }
            }
            node.addChild(childNode);
        }
        return node;
    }

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
