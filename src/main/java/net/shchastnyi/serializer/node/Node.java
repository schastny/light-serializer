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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;

        if (children != null ? !children.equals(node.children) : node.children != null) return false;
        if (data != null ? !data.equals(node.data) : node.data != null) return false;
        if (name != null ? !name.equals(node.name) : node.name != null) return false;
        if (type != null ? !type.equals(node.type) : node.type != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (children != null ? children.hashCode() : 0);
        result = 31 * result + (data != null ? data.hashCode() : 0);
        return result;
    }

}
