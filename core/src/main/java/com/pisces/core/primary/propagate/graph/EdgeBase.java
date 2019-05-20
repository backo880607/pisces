package com.pisces.core.primary.propagate.graph;

import com.pisces.core.relation.Sign;

public abstract class EdgeBase implements Iterable<Integer> {
	Sign sign = null;
	Sign reverse = null;
	
	abstract void addEdge(VertexData observer);
	abstract void removeEdge(VertexData observer);
	abstract void removeEdge();
}
