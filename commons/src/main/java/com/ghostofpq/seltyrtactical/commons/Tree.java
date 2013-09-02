package com.ghostofpq.seltyrtactical.commons;

import java.util.ArrayList;

public class Tree<T> {
    private Node<T> root;

    public Tree(T rootData) {
        root = new Node<T>();
        root.data = rootData;
        root.children = new ArrayList<Node<T>>();
        root.distanceFromTop = 0;
    }

    public Node<T> getRoot() {
        return root;
    }
}
