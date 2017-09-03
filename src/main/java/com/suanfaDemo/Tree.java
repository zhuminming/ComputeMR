package com.suanfaDemo;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by zmm on 2017-09-02.
 */
public class Tree {
    public Tree(){
    }

    public Node insert(String nodename,Node parent){
        Node node= node =new Node(nodename);
        if(parent!=null){
            node.setParent(parent);
            parent.putClient(node);
        }
        return node;
    }

    /*
     *功能：通过递归实现深度优先搜索遍历算法
     *
     * */
    public void deepFirstSearch(Node treeNode){
        System.out.println(treeNode.getNodeName());
        List<Node> list = treeNode.getClientS();
        for(Node node:list){
            deepFirstSearch(node);
        }
    }

    /*
     * 功能：通过递归实现广度优先搜索遍历算法
     * */
    public  void breadthFirstSearch(Node treeNode) {
        List<Node> tmpLists = Lists.newArrayList();
        tmpLists.add(treeNode);
        getSameLevelClientNode(tmpLists);
    }

    public void breadthFirstSearchBynoRecursion(Node treeNode){
        List<Node> tmpLists = Lists.newArrayList();
        tmpLists.add(treeNode);
        while(!tmpLists.isEmpty()){
            printNodeInfo(tmpLists);
            tmpLists=getLevelClientNode(tmpLists);
        }
    }

    private void printNodeInfo(List<Node> list){
        for(Node node:list){
            System.out.println(node.getNodeName());
        }
    }


    /*
     * 功能：递归获取一层上面所有的节点
     *  */
    private  void getSameLevelClientNode(List<Node> levelLists){
        List<Node> list = Lists.newArrayList();
        for(Node node : levelLists){
            list.addAll(node.getClientS());
        }
        printNodeInfo(list);
        if(list.isEmpty()) return;
        getSameLevelClientNode(list);
    }

    /*
     * 功能：获取一层上面所有的节点
     *
     */
    private  List<Node> getLevelClientNode(List<Node> levelLists){
        List<Node> list = Lists.newArrayList();
        for(Node node : levelLists){
            list.addAll(node.getClientS());
        }
        return list;
    }


    public static void main(String[] args){
        Tree tree = new Tree();
        Node node = tree.insert("test",null);
        Node node1 =tree.insert("test1",node);
        Node node5 =tree.insert("test5",node1);
        Node node3 =tree.insert("test3",node);
        Node node4 =tree.insert("test4",node1);
        Node node9 =tree.insert("test9",node1);
        Node node2 =tree.insert("test2",node3);
        Node node6 =tree.insert("test6",node2);
        Node node7 =tree.insert("test7",node4);
        Node node8 =tree.insert("test8",node4);

        System.out.println("..................breadthFirstSearch................");
        tree.breadthFirstSearch(node);
        System.out.println("..................breadthFirstSearchByNoRecursion................");
        tree.breadthFirstSearchBynoRecursion(node);
        System.out.println("..................deepFirstSearch...................");
        tree.deepFirstSearch(node);
    }


}
