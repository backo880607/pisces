package com.pisces.core.primary.propagate.graph;

import java.util.TreeMap;

import com.pisces.core.relation.Sign;

/**
 * 图节点
 * @author niuhaitao
 *
 */
class VertexNode {
	public VertexData observer;
	public TreeMap<Sign, EdgeBase> outEdge;
	public TreeMap<Sign, EdgeBase> inEdge;

	public VertexNode(VertexData observer) {
		this.observer = observer;
		outEdge = null;
		inEdge = null;
	}
}
