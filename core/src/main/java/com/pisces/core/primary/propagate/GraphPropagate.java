package com.pisces.core.primary.propagate;

import java.util.TreeMap;

import com.pisces.core.primary.propagate.graph.EdgeBase;
import com.pisces.core.primary.propagate.graph.Graph;

class GraphPropagate extends Graph {
	TreeMap<VertexPropagate, VertexPropagate> caches = new TreeMap<>();

	@Override
	protected void mergeImpl(Graph graph) {
		GraphPropagate temp = (GraphPropagate)graph;
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
