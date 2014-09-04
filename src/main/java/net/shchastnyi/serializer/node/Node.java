package net.shchastnyi.serializer.node;

import java.util.ArrayList;
import java.util.List;

public class Node {

    public String name;
    public String type;
    public List<Node> children = new ArrayList<>();
    public Object data;

    public Node(String name, String type, Object data) {
        this.name = name;
        this.type = type;
        this.data = data;
    }

    public void addChild(Node child) {
        children.add(child);
    }

    public static Node node(String name, String type){
        return new Node(name, type, null);
    }
    public static Node node(String name, String type, Object data){
        return new Node(name, type, data);
    }

    @Override
    public String toString() {
        return name +": "+ type +(children.isEmpty()?"":" ("+children.size()+")");
    }
}
