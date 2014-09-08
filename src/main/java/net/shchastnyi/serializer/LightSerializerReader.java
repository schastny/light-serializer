package net.shchastnyi.serializer;

import net.shchastnyi.serializer.node.Node;
import net.shchastnyi.serializer.node.NodeDecoder;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.shchastnyi.serializer.LightSerializerConstants.*;
import static net.shchastnyi.serializer.utils.LightSerializerUtils.byteListToArray;

public class LightSerializerReader {

    public <T> T deSerialize(byte[] bytes) throws Exception {
        Map<Integer, Node> nodes = getNodesFromBytes(bytes);
        Node rootNode = constructRootNode(nodes);
        T result = (T) new NodeDecoder().constructFromNode(rootNode);
        return result;
    }

    private Node constructRootNode(Map<Integer, Node> nodesMap) {
        Node root = nodesMap.get(0);
        Node rootResult = constructRootNodeInternal(root, nodesMap);
        return rootResult;
    }

    private Node constructRootNodeInternal(Node node, Map<Integer, Node> nodesMap) {
        Node nodeResult = new Node(node.id, node.name, node.type, node.data);
        for ( Node childNode : nodesMap.get(node.id).children) {
            if ( childNode.id != -1 ) {
                Node childNodeResult = constructRootNodeInternal(childNode, nodesMap);
                nodeResult.addChild(childNodeResult);
            } else {
                nodeResult.addChild(childNode);
            }
        }
        return nodeResult;
    }

    public Map<Integer, Node> getNodesFromBytes(byte[] bytes) throws Exception {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        DataInputStream stream = new DataInputStream(bis);
        byte b;
        Map<Integer, Node> nodes = new HashMap<>();
        while ( stream.available() > 0 ) {
            b = stream.readByte();
            while ( b != CLASS_END ) {

                // Read entityType
                int refId = stream.readInt();
                String type = getToken(stream);
                Node rootNode = new Node(refId, "root", type);

                // Read fiedls
                while (b != FIELD_END) { //FIELD_END or DELIMITER
                    String fieldName = getToken(stream);
                    if ( fieldName == null ) {
                        break; //This is an end of a file
                    }
                    String fieldType = getToken(stream);
                    Object fieldValue = null;
                    int fieldRefId = -1;
                    switch (fieldType) {
                        case TYPE_BYTE: case TYPE_BYTE_P: fieldValue = stream.readByte(); break;
                        case TYPE_SHORT: case TYPE_SHORT_P: fieldValue = stream.readShort(); break;
                        case TYPE_INTEGER: case TYPE_INT_P: fieldValue = stream.readInt(); break;
                        case TYPE_LONG: case TYPE_LONG_P: fieldValue = stream.readLong(); break;
                        case TYPE_FLOAT: case TYPE_FLOAT_P: fieldValue = stream.readFloat(); break;
                        case TYPE_DOUBLE: case TYPE_DOUBLE_P: fieldValue = stream.readDouble(); break;
                        case TYPE_BOOLEAN: case TYPE_BOOLEAN_P: fieldValue = stream.readBoolean(); break;
                        case TYPE_CHARACTER: case TYPE_CHAR_P: fieldValue = stream.readChar(); break;
                        default: fieldRefId = stream.readInt(); break;
                    }
                    Node childNode = new Node(fieldRefId, fieldName, fieldType, fieldValue);
                    nodes.put(fieldRefId, childNode);
                    rootNode.addChild(childNode);
                    b = stream.readByte();
                }
                // !Read fiedls
                nodes.put(refId, rootNode);
                b = stream.readByte();

            } //INNER WHILE
        }
        return nodes;
    }

    private String getToken(DataInputStream stream) throws IOException {

        List<Byte> chars = new ArrayList<>();
        byte b;
        while ( (b = stream.readByte()) != DELIMITER ) {
            if ( b == FIELD_END ) {
                return null; //This is an end of file
            }
            chars.add(b);
        }
        return new String(byteListToArray(chars));
    }

}
