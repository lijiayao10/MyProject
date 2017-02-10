package com.cjy.code.xunhuan;

public class WhileTest {

    public static void main(String[] args) {

        Node[] nodes = new Node[11];

        Node hard = new Node();
        hard.i = 0;

        Node tail = new Node();

        for (int i = 1; i <= 10; i++) {
            Node node = new Node();
            node.i = i;

            node.prev = tail;
            tail = node;

            nodes[i] = node;
        }

        xunHuan(nodes[10].prev, nodes[10]);

    }

    static class Node {
        private Node prev;

        private Node next;

        private int  i = 0;

    }

    static void xunHuan(Node pred, Node node) {
        System.out.println("pred:" + pred.i + ",node:" + node.i);
        do {
            pred = pred.prev;
            System.out.println("node.prev:" + node.prev.i + ",pred" + pred.i + ",pred.prev:" + pred.prev.i);

            System.out.println(pred.i);
        } while (pred == null || pred.i > 1);
    }

}
