package net.shchastnyi.serializer.utils;

import java.util.List;

public class SerializedNode {
    public int referenceId;
    public List<Byte> bytes;
    public StringBuilder debugString;
    public SerializedNode(int referenceId, List<Byte> bytes, StringBuilder debugString) {
        this.referenceId = referenceId;
        this.bytes = bytes;
        this.debugString = debugString;
    }
}
