package net.shchastnyi.serializer;

import net.shchastnyi.serializer.node.Node;
import net.shchastnyi.serializer.node.NodeConstructor;
import net.shchastnyi.serializer.utils.SerializedNode;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.*;

import static net.shchastnyi.serializer.LightSerializerConstants.*;
import static net.shchastnyi.serializer.utils.LightSerializerUtils.byteArrayToList;
import static net.shchastnyi.serializer.utils.LightSerializerUtils.byteListToArray;
import static net.shchastnyi.serializer.utils.LightSerializerUtils.primitiveToBytes;

public class LightSerializerWriter {

    private int keyId = 0;

    public byte[] serialize(Object message) throws Exception {
        Node node = new NodeConstructor().getNode(message);
        SerializedNode res = serialize(node);
        return byteListToArray(res.bytes);
    }
    public String serializeDebug(Object message) throws Exception {
        Node node = new NodeConstructor().getNode(message);
        return serialize(node).debugString.toString();
    }

    private SerializedNode serialize(Node node) throws Exception {
        List<ByteArrayOutputStream> streamResultList = new ArrayList<>();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        DataOutputStream streamResult = new DataOutputStream(stream);
        List<StringBuilder> sbResultList = new ArrayList<>();
        StringBuilder sbResult = new StringBuilder();

        int referenceId = keyId++;
        writeHeader(referenceId, node, streamResult, sbResult);
        for (Node childNode : node.children) {
            writeField(childNode, streamResult, streamResultList, sbResult, sbResultList);
        }
        writeFooter(streamResult, sbResult);

        streamResultList.add(stream);
        sbResultList.add(sbResult);

        Collections.reverse(streamResultList);
        Collections.reverse(sbResultList);
        return new SerializedNode(referenceId, flattenStream(streamResultList), flatten(sbResultList));
    }

    private void writeField(Node childNode, DataOutputStream streamResult, List<ByteArrayOutputStream> streamResultList,
                            StringBuilder sbResult, List<StringBuilder> sbResultList) throws Exception {

        streamResult.writeBytes(childNode.name);
        streamResult.writeByte(DELIMITER);
        streamResult.writeBytes(childNode.type);
        streamResult.writeByte(DELIMITER);

        sbResult.append(childNode.name);
        sbResult.append(DELIMITER_DEBUG);
        sbResult.append(childNode.type);
        sbResult.append(DELIMITER_DEBUG);

        boolean isPrimitive;
        switch (childNode.type) {
            case TYPE_BYTE: case TYPE_BYTE_P:
            case TYPE_SHORT: case TYPE_SHORT_P:
            case TYPE_INTEGER: case TYPE_INT_P:
            case TYPE_LONG: case TYPE_LONG_P:
            case TYPE_FLOAT: case TYPE_FLOAT_P:
            case TYPE_DOUBLE: case TYPE_DOUBLE_P:
            case TYPE_BOOLEAN: case TYPE_BOOLEAN_P:
            case TYPE_CHARACTER: case TYPE_CHAR_P:
                isPrimitive = true; break;
            default:
                isPrimitive = false; break;
        }
        boolean isArray = false;
        if ( isPrimitive ) {
            streamResult.write(primitiveToBytes(childNode.data, childNode.type));
            sbResult.append(childNode.data);
        }
        else if (isArray) {
            //TODO
        } else {
            SerializedNode child = serialize(childNode);

            streamResult.writeInt(child.referenceId);
            ByteArrayOutputStream stream = new ByteArrayOutputStream(child.bytes.size());
            stream.write(byteListToArray(child.bytes));
            streamResultList.add(stream);

            sbResult.append(child.referenceId);
            sbResultList.add(child.debugString);
        }

        streamResult.writeByte(DELIMITER);
        sbResult.append(DELIMITER_DEBUG);
        sbResult.append("\n");
    }

    private void writeHeader(int referenceId, Node node, DataOutputStream streamResult, StringBuilder sbResult) throws IOException {
        streamResult.writeByte(CLASS_START);
        streamResult.writeInt(referenceId);
        streamResult.writeBytes(node.type);
        streamResult.writeByte(DELIMITER);

        sbResult.append(CLASS_START_DEBUG); sbResult.append("\n");
        sbResult.append(referenceId);
        sbResult.append(node.type);
        sbResult.append(DELIMITER_DEBUG); sbResult.append("\n");
    }

    private void writeFooter(DataOutputStream streamResult, StringBuilder sbResult) throws IOException {
        streamResult.writeByte(FIELD_END);
        streamResult.writeByte(CLASS_END);
        sbResult.append(FIELD_END_DEBUG);;
        sbResult.append(CLASS_END_DEBUG); sbResult.append("\n");
    }

    private List<Byte> flattenStream(List<ByteArrayOutputStream> list) {
        List<Byte> res = new ArrayList<>();
        for (ByteArrayOutputStream str : list) {
            List<Byte> rrr = byteArrayToList(str.toByteArray());
            res.addAll(rrr);
        }
        return res;
    }

    private StringBuilder flatten(List<StringBuilder> list) {
        StringBuilder result = new StringBuilder();
        for (StringBuilder builder : list)
            result.append(builder);
        return result;
    }

}
