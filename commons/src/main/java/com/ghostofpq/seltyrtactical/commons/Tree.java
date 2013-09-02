package com.ghostofpq.seltyrtactical.commons;

import java.util.ArrayList;

public class Tree<T> {
    private Node<T> root;

    public Tree(T rootData) {
        root = new Node<T>();
        root.setData(rootData);
        root.setChildren(new ArrayList<Node<T>>());
        root.setDistanceFromTop(0);
        root.setTree(this);
    }

    public Node<T> getRoot() {
        return root;
    }

    public boolean contains(T element) {
        boolean result = false;

        if (root.getData().equals(element)) {
            result = true;
        } else {
            if (root.getChildren().isEmpty()) {
                result = false;
            } else {
                int i = 0;
                while (!result && i < root.getChildren().size()) {
                    result = root.getChildren().get(i).contains(element);
                    i++;
                }
            }
        }

        return result;
    }
}
