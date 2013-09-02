package com.ghostofpq.seltyrtactical.commons;


import java.util.ArrayList;
import java.util.List;

public class Node<T> {
    private Tree<T> tree;
    private T data;
    private Node<T> parent;
    private List<Node<T>> children;
    private int distanceFromTop;

    public Node<T> addChild(T childData, int distance) {
        if (!tree.contains(childData)) {
            Node<T> child = new Node<T>();
            child.setData(childData);
            child.setChildren(new ArrayList<Node<T>>());
            child.setDistanceFromTop(distanceFromTop + distance);
            child.setTree(getTree());
            getChildren().add(child);
            return child;
        } else {
            return null;
        }
    }

    public boolean contains(T element) {
        boolean result = false;

        if (getData().equals(element)) {
            result = true;
        } else {
            if (getChildren().isEmpty()) {
                result = false;
            } else {
                int i = 0;
                while (!result && i < getChildren().size()) {
                    result = getChildren().get(i).contains(element);
                    i++;
                }
            }
        }

        return result;
    }

    public Tree<T> getTree() {
        return tree;
    }

    public void setTree(Tree<T> tree) {
        this.tree = tree;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Node<T> getParent() {
        return parent;
    }

    public void setParent(Node<T> parent) {
        this.parent = parent;
    }

    public List<Node<T>> getChildren() {
        return children;
    }

    public void setChildren(List<Node<T>> children) {
        this.children = children;
    }

    public int getDistanceFromTop() {
        return distanceFromTop;
    }

    public void setDistanceFromTop(int distanceFromTop) {
        this.distanceFromTop = distanceFromTop;
    }
}



