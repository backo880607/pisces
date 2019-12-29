package com.pisces.platform.core.primary.propagate;

import com.pisces.platform.core.primary.propagate.graph.EdgeBase;
import com.pisces.platform.core.primary.propagate.graph.Graph;

import java.util.TreeMap;

class GraphPropagate extends Graph {
    TreeMap<VertexPropagate, VertexPropagate> caches = new TreeMap<>();

    @Override
    protected void mergeImpl(Graph graph) {
        GraphPropagate temp = (GraphPropagate) graph;
        this.caches.putAll(temp.caches);
    }

    @Override
    protected EdgeBase createEdge() {
        return new EdgePropagate();
    }

    VertexPropagate get(VertexPropagate target) {
        return this.caches.get(target);
    }

    void add(VertexPropagate target) {
        this.caches.put(target, target);
    }
}
