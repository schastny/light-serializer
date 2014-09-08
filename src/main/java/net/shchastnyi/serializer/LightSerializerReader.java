package net.shchastnyi.serializer;

import net.shchastnyi.serializer.node.Node;
import net.shchastnyi.serializer.node.NodeDecoder;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static net.shchastnyi.serializer.LightSerializerConstants.*;
import static net.shchastnyi.serializer.utils.LightSerializerUtils.byteListToArray;

public class LightSerializerReader {

    public <T> T deSerialize(byte[] bytes) throws Exception {
        List<Node> nodes = getNodesFromBytes(bytes);
        Node rootNode = constructRootNode(nodes);
        T result = (T) new NodeDecoder().constructFromNode(rootNode);
        return result;
    }

    private Node constructRootNode(List<Node> nodes) {
        return nodes.get(0);
    }

    public List<Node> getNodesFromBytes(byte[] bytes) throws Exception {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        DataInputStream stream = new DataInputStream(bis);
        if (stream.readByte() != CLASS_START) {
            throw new IOException("Unknown file format");
        }

        // Read entityType
        int refId = stream.readInt();
        String type = getToken(stream);
        List<Node> nodes = new ArrayList<>();
        nodes.add(new Node(refId, "root", type));

        // Read fiedls
        byte b = stream.readByte();
        while (b != CLASS_END) { //CLASS_END or DELIMITER
            String fieldName = getToken(stream);
            if ( fieldName == null ) {
                continue; //This is an end of a file
            }
            String fieldType = getToken(stream);
            Object fieldValue = null;
            int fieldRefId = 0;
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
            nodes.add(new Node(fieldRefId, fieldName, fieldType, fieldValue));
            b = stream.readByte();
        }
        // !Read fiedls

        return nodes;
    }

    private String getToken(DataInputStream stream) throws IOException {

        List<Byte> chars = new ArrayList<>();
        byte b;
        while ( (b = stream.readByte()) != DELIMITER ) {
            if ( b == CLASS_END ) {
                return null; //This is an end of file
            }
            chars.add(b);
        }
        return new String(byteListToArray(chars));
    }

}
