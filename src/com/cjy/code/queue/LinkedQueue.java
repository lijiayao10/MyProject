package com.cjy.code.queue;

import java.util.concurrent.atomic.AtomicReference;

public class LinkedQueue<E> {

    private static class Node<E> {
        final E                        item;
        final AtomicReference<Node<E>> next;

        public Node(E item, Node<E> next) {
            this.item = item;
            this.next = new AtomicReference<Node<E>>(next);
        }
    }

    private final Node<E>                  dummy = new Node<E>(null, null);

    private final AtomicReference<Node<E>> head  = new AtomicReference<LinkedQueue.Node<E>>(dummy);
    private final AtomicReference<Node<E>> tail  = new AtomicReference<LinkedQueue.Node<E>>(dummy);

    public boolean put(E item) {
        Node<E> newNode = new Node<E>(item, null);
        for (;;) {
            Node<E> curTail = tail.get();
            Node<E> tailNext = curTail.next.get();

            if (curTail == tail.get()) {
                if (tailNext != null) {
                    tail.compareAndSet(curTail, tailNext);
                } else {
                    if (curTail.next.compareAndSet(null, newNode)) {
                        tail.compareAndSet(curTail, newNode);
                    }
                    return true;
                }
            }
        }
    }

    public E take() {
        for (;;) {
            Node<E> headNode = head.get();
            Node<E> nextNode = headNode.next.get();

            if (headNode == head.get()) {
                if (headNode != dummy) {
                    head.compareAndSet(headNode, nextNode);
                    return headNode.item;
                }
                head.compareAndSet(headNode, nextNode);
            }

        }
    }

    public static void main(String[] args) {
        LinkedQueue<String> linkedQueue = new LinkedQueue<>();
        for (int i = 0; i < 1000; i++) {
            linkedQueue.put("i=" + i);
        }

        for (int i = 0; i < 1000; i++) {
            System.out.println(linkedQueue.take());
        }
    }

}
