package com.sortDemo;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by zmm on 2017-09-02.
 */
public class Node {
    private Node parent;
    private List<Node> clientS;
    private String nodeName;

    public Node(String nodeName){
        this.nodeName=nodeName;
        this.parent=null;
        this.clientS=Lists.newArrayList();
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public List<Node> getClientS() {
        return clientS;
    }

    public void setClientS(List<Node> clientS) {
        this.clientS = clientS;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public void putClient(Node node){
        if(this.clientS==null){
            this.clientS= Lists.newArrayList();
        }

        this.clientS.add(node);
    }
}
