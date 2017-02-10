package com.cjy.code.atomic;

import java.util.concurrent.atomic.AtomicReference;

public class ConcurrentStack<E> {

    AtomicReference<Node<E>> top = new AtomicReference<ConcurrentStack.Node<E>>();

    public void push(E item) {
        Node<E> newHead = new Node<>(item);

        Node<E> oldHead;
        do {
            oldHead = top.get();
            newHead.next = oldHead;
        } while (!top.compareAndSet(oldHead, newHead));
    }

    public E pop() {
        Node<E> oldHead;
        Node<E> newHead;

        do {
            oldHead = top.get();
            if (oldHead == null)
                return null;
            newHead = oldHead.next;
        } while (!top.compareAndSet(oldHead, newHead));
        return oldHead.item;
    }

    private static class Node<E> {

        public Node(E e) {
            this.item = e;
        }

        private Node<E> next;

        private E       item;
    }

    public static void main(String[] args) {
        ConcurrentStack<String> stack = new ConcurrentStack<>();
        for (int i = 0; i < 1000; i++) {
            stack.push("i=" + i);
        }

        for (int i = 0; i < 1000; i++) {
            System.out.println(stack.pop());
        }
    }
}
