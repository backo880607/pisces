package com.pisces.platform.core.primary.propagate.graph;

import java.util.Iterator;

public class EdgeSingleton extends EdgeBase {
    private VertexData adjvex = null;

    @Override
    public Iterator<Integer> iterator() {
        return new SingletonIterator(this.adjvex);
    }

    @Override
    void addEdge(VertexData observer) {
        this.adjvex = observer;
    }

    @Override
    void removeEdge(VertexData observer) {
        this.adjvex = null;
    }

    @Override
    void removeEdge() {
        this.adjvex = null;
    }

    private static class SingletonIterator implements Iterator<Integer> {
        VertexData iter = null;

        public SingletonIterator(VertexData observers) {
            iter = observers;
        }

        public boolean hasNext() {
            return this.iter != null;
        }

        public Integer next() {
            VertexData observer = iter;
            iter = null;
            return observer.adjvex;
        }
    }
}
