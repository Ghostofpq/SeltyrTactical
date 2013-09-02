package com.ghostofpq.seltyrtactical.commons;


import java.util.ArrayList;
import java.util.List;

public class Node<T> {
    public T data;
    public Node<T> parent;
    public List<Node<T>> children;
    public int distanceFromTop;

    public Node<T> addChild(T childData, int distance) {
        Node<T> child = new Node<T>();
        child.data = childData;
        child.children = new ArrayList<Node<T>>();
        child.distanceFromTop = distanceFromTop + distance;

        children.add(child);
        return child;
    }
}



