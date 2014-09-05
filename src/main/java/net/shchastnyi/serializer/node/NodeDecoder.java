package net.shchastnyi.serializer.node;

import org.apache.commons.lang.ArrayUtils;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static net.shchastnyi.serializer.LightSerializerConstants.*;
import static net.shchastnyi.serializer.LightSerializerConstants.TYPE_CHAR_P;
import static org.apache.commons.lang.ClassUtils.primitiveToWrapper;

public class NodeDecoder {

    public Object constructFromNode(Node node) throws Exception {
        String classType = node.type;
        Object s;
        if ( classType.endsWith("[]") ) {
            s = constructFromNodeArray(node);
        } else {
            s = constructFromNodeObject(node);
        }
        return s;
    }

    public Object constructFromNodeArray(Node node) throws Exception {
        String classType = node.type;
        String classTypeTrimmed = classType.substring(0, classType.length() - 2);
        Class<?> clazz = forName(classTypeTrimmed);
        Object[] s = createArray(clazz, node.children.size());
        for (int i = 0; i < node.children.size(); i++) {
            Node childNode = node.children.get(i);
            Object o = constructFromNode(childNode);
            s[i] = o;
        }
        return s;
    }

    public <E> E[] createArray(Class<E> c, int length) {
        // Use Array native method to create array of a type only known at run time
        @SuppressWarnings("unchecked")
        final E[] a = (E[]) Array.newInstance(c, length);
        return a;
    }

    public Object constructFromNodeObject(Node node) throws Exception {
        String classType = node.type;
        Class<?> clazz = forName(classType);
        Object s = newInstance(node);
        for (Node childNode : node.children) {
            Field field = clazz.getDeclaredField(childNode.name);
            if ( !Modifier.isStatic(field.getModifiers()) ) {
                field.setAccessible(true);
                Object o = constructFromNode(childNode);

                Class<?> fieldType = field.getType();
                String arrayType = fieldType.getCanonicalName().substring(0, fieldType.getCanonicalName().length() - 2);
                switch ( arrayType ) {
                    case TYPE_BYTE_P: field.set(s, ArrayUtils.toPrimitive((Byte[]) o)); break;
                    case TYPE_SHORT_P: field.set(s, ArrayUtils.toPrimitive((Short[]) o)); break;
                    case TYPE_INT_P: field.set(s, ArrayUtils.toPrimitive((Integer[]) o)); break;
                    case TYPE_LONG_P: field.set(s, ArrayUtils.toPrimitive((Long[]) o)); break;
                    case TYPE_FLOAT_P: field.set(s, ArrayUtils.toPrimitive((Float[]) o)); break;
                    case TYPE_DOUBLE_P: field.set(s, ArrayUtils.toPrimitive((Double[]) o)); break;
                    case TYPE_BOOLEAN_P: field.set(s, ArrayUtils.toPrimitive((Boolean[]) o)); break;
                    case TYPE_CHAR_P: field.set(s, ArrayUtils.toPrimitive((Character[]) o)); break;
                    default: field.set(s, o); break;
                }
            } //!if
        } //!for
        return s;
    }

    public static Class forName(String classType) throws ClassNotFoundException {
        Class clazz;
        switch ( classType ) {
            case TYPE_BYTE: case TYPE_BYTE_P: clazz = primitiveToWrapper(byte.class); break;
            case TYPE_SHORT: case TYPE_SHORT_P: clazz = primitiveToWrapper(short.class); break;
            case TYPE_INTEGER: case TYPE_INT_P: clazz = primitiveToWrapper(int.class); break;
            case TYPE_LONG: case TYPE_LONG_P: clazz = primitiveToWrapper(long.class); break;
            case TYPE_FLOAT: case TYPE_FLOAT_P: clazz = primitiveToWrapper(float.class); break;
            case TYPE_DOUBLE: case TYPE_DOUBLE_P: clazz = primitiveToWrapper(double.class); break;
            case TYPE_BOOLEAN: case TYPE_BOOLEAN_P: clazz = primitiveToWrapper(boolean.class); break;
            case TYPE_CHARACTER: case TYPE_CHAR_P: clazz = primitiveToWrapper(char.class); break;
            case "": clazz = null; break;
            default: clazz = Class.forName(classType); break;
        }
        return clazz;
    }

    public Object newInstance(Node node) throws Exception {
        String classType = node.type;
        Class<?> clazz = forName(node.type);

        if (clazz == null) return null;

        Object s;
        switch ( classType ) {
            case TYPE_BYTE: case TYPE_BYTE_P: s = clazz.getConstructor(byte.class).newInstance(node.data); break;
            case TYPE_SHORT: case TYPE_SHORT_P: s = clazz.getConstructor(short.class).newInstance(node.data); break;
            case TYPE_INTEGER: case TYPE_INT_P: s = clazz.getConstructor(int.class).newInstance(node.data); break;
            case TYPE_LONG: case TYPE_LONG_P: s = clazz.getConstructor(long.class).newInstance(node.data); break;
            case TYPE_FLOAT: case TYPE_FLOAT_P: s = clazz.getConstructor(float.class).newInstance(node.data); break;
            case TYPE_DOUBLE: case TYPE_DOUBLE_P: s = clazz.getConstructor(double.class).newInstance(node.data); break;
            case TYPE_BOOLEAN: case TYPE_BOOLEAN_P: s = clazz.getConstructor(boolean.class).newInstance(node.data); break;
            case TYPE_CHARACTER: case TYPE_CHAR_P: s = clazz.getConstructor(char.class).newInstance(node.data); break;
            default: s = clazz.newInstance(); break;
        }
        return s;
    }

}
