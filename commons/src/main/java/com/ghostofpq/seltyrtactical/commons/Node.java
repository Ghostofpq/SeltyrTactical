package com.ghostofpq.seltyrtactical.commons;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Node<T> {
    private Tree<T> tree;
    private T data;
    private Node<T> parent;
    private List<Node<T>> children;
    private int distanceFromTop;

    public Node(T data, Tree<T> tree, Node<T> parent, int distanceFromTop) {
        this.setTree(tree);
        this.setData(data);
        this.setParent(parent);
        this.setDistanceFromTop(distanceFromTop);
        this.setChildren(new ArrayList<Node<T>>());
    }

    public Node<T> addChild(T childData, int distance) {
        int childDistanceFromTop = (getDistanceFromTop() + distance);
        Node<T> result = null;
        if (!tree.contains(childData)) {
            Node<T> child = new Node<T>(childData, getTree(), this, childDistanceFromTop);
            getChildren().add(child);
            result = child;
        } else {
            List<Node<T>> concurrentChildren = tree.find(childData);
            for (Node<T> concurrentChild : concurrentChildren) {
                if (concurrentChild.getDistanceFromTop() > childDistanceFromTop) {
                    Node<T> concurrentParent = concurrentChild.getParent();
                    if (null != concurrentParent) {
                        concurrentParent.getChildren().remove(concurrentChild);

                        Node<T> child = new Node<T>(childData, getTree(), this, childDistanceFromTop);
                        getChildren().add(child);
                        result = child;
                    }
                }
            }
        }
        return result;
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

    public List<Node<T>> find(T element) {
        List<Node<T>> result = new ArrayList<Node<T>>();
        if (getData().equals(element)) {
            result.add(this);
        } else {
            if (!getChildren().isEmpty()) {
                for (Node<T> child : getChildren()) {
                    result.addAll(child.find(element));
                }
            }
        }
        return result;
    }

    public List<T> getPathToTop() {
        List<T> result = new ArrayList<T>();
        Node<T> node = this;
        while (null != node.getParent()) {
            result.add(node.getData());
            node = node.getParent();
        }
        return result;
    }

    public List<T> getPathFromTop() {
        List<T> result = getPathToTop();
        Collections.reverse(result);
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



