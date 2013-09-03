package com.ghostofpq.seltyrtactical.commons;

public class Tree<T> {
    private Node<T> root;

    public Tree(T rootData) {
        root = new Node<T>(rootData, this, null, 0);
    }

    public Node<T> getRoot() {
        return root;
    }

    public boolean contains(T element) {
        boolean result = root.contains(element);
        return result;
    }

    public Node<T> find(T element) {
        Node<T> result = root.find(element);
        return result;
    }
}
