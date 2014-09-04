package net.shchastnyi.serializer.node;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

import static net.shchastnyi.serializer.LightSerializerConstants.*;

public class NodeConstructor {

    public static Node getNode(Object entity) throws Exception {
        Node root = getNode("root", entity);
        return root;
    }

    public static Node getNode(String nodeName, Object entity) throws Exception {
        if (entity == null)
            return new Node(nodeName, "", "");

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
                    String arrayElementType = fieldType.getCanonicalName().substring(0, fieldType.getCanonicalName().length() - 2);
                    Object arrayElement = Array.get(childArray, i);
                    if ( arrayElement != null ) {
                        arrayElementType = arrayElement.getClass().getCanonicalName();
                    }
                    Node childArrayNode = writePrimitiveOrObject("arrayElement", arrayElementType, arrayElement);
                    childNode.addChild(childArrayNode);
                }
            }
            else {
                Object fieldEntity = field.get(entity);
                childNode = writePrimitiveOrObject(field.getName(), fieldTypeName, fieldEntity);
            }
            node.addChild(childNode);
        }
        return node;
    }

    private static Node writePrimitiveOrObject(String fieldName, String fieldTypeName, Object entity) throws Exception {
        Node childNode;
        switch (fieldTypeName) {
            case TYPE_BYTE: case TYPE_BYTE_P:
            case TYPE_SHORT: case TYPE_SHORT_P:
            case TYPE_INTEGER: case TYPE_INT_P:
            case TYPE_LONG: case TYPE_LONG_P:
            case TYPE_FLOAT: case TYPE_FLOAT_P:
            case TYPE_DOUBLE: case TYPE_DOUBLE_P:
            case TYPE_BOOLEAN: case TYPE_BOOLEAN_P:
            case TYPE_CHARACTER: case TYPE_CHAR_P:
                childNode = Node.node(fieldName, fieldTypeName, entity); break;
            default:
                childNode = getNode(fieldName, entity); break;
        }
        return childNode;
    }

}
