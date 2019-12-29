package com.pisces.platform.core.primary.propagate.graph;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class EdgeLinkedList extends EdgeBase {
    private List<VertexData> adjvexs = new LinkedList<VertexData>();

    public Iterator<Integer> iterator() {
        return new ObjectListIterator(adjvexs);
    }

    @Override
    void addEdge(VertexData data) {
        this.adjvexs.add(data);
    }

    @Override
    void removeEdge(VertexData data) {
        this.adjvexs.remove(data);
    }

    @Override
    void removeEdge() {
        this.adjvexs.clear();
    }

    @SuppressWarnings("unchecked")
    <T extends VertexData> T get() {
        return this.adjvexs.isEmpty() ? null : (T) this.adjvexs.get(0);
    }

    @SuppressWarnings("unchecked")
    public <T extends VertexData> List<T> getList() {
        return (List<T>) this.adjvexs;
    }

    private static class ObjectListIterator implements Iterator<Integer> {
        ListIterator<VertexData> iter;

        public ObjectListIterator(List<VertexData> datas) {
            iter = (ListIterator<VertexData>) datas.iterator();
        }

        public boolean hasNext() {
            while (iter.hasNext()) {
                if (iter.next().adjvex < 0) {
                    iter.remove();
                } else {
                    iter.previous();
                    break;
                }
            }

            return iter.hasNext();
        }

        public Integer next() {
            VertexData observer = iter.next();
            return observer.adjvex;
        }
    }
}
