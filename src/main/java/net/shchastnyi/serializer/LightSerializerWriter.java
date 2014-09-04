package net.shchastnyi.serializer;

import net.shchastnyi.serializer.node.Node;
import net.shchastnyi.serializer.node.NodeConstructor;

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

    public static byte[] serialize(Object message) throws Exception {
        Node node = NodeConstructor.getNode(message);
        List<Byte> result = new ArrayList<>();

        result.add(CLASS_START);
        result.addAll(stringBytes(node.type));
        result.add(CLASS_DELIMITER);

        for (Node childNode : node.children) {
            List<Byte> fieldBytes = getFieldBytes(childNode);
            result.addAll(fieldBytes);
        }

        result.add(CLASS_END);
        return byteListToArray(result);
    }

    private static List<Byte> getFieldBytes(Node node) throws IllegalAccessException {
        List<Byte> result = new ArrayList<>();

        result.add(FIELD_START);
        result.addAll(stringBytes(node.name));
        result.add(FIELD_DELIMITER);

        result.addAll(stringBytes(node.type));
        result.add(FIELD_DELIMITER);

        List<Byte> fieldBytes = objectToBytes(node.data, node.type);
        result.addAll(listLengthToBytes(fieldBytes));
        result.addAll(fieldBytes);

        return result;
    }

}
