package com.pisces.core.primary.propagate;

import java.util.ArrayList;
import java.util.List;

import com.pisces.core.entity.EntityObject;
import com.pisces.core.primary.propagate.graph.EdgeLinkedList;
import com.pisces.core.primary.propagate.graph.Graph;
import com.pisces.core.primary.propagate.graph.VertexData;
import com.pisces.core.relation.Sign;

class VertexPropagate extends VertexData implements Comparable<VertexPropagate> {
	public static final Sign holder = new Sign();
	int level = 0;
	int visited = 0;
	Propagate.Data data;
	EntityObject entity;
	Sign[] path;

	@Override
	protected Graph createGraph() {
		return new GraphPropagate();
	}

	@Override
	public int compareTo(VertexPropagate arg0) {
		if (this.entity == arg0.entity) {
			if (this.data.id == arg0.data.id) {
				return 0;
			}
			return (this.data.id < arg0.data.id) ? -1 : 1;
		}
		if (this.entity.getId() == arg0.entity.getId()) {
			return 0;
		}
		return (this.entity.getId() < arg0.entity.getId()) ? -1 : 1;
	}
	
	VertexPropagate get(EntityObject entity, Propagate.Data data) {
		GraphPropagate graph = (GraphPropagate)this.getGraph();
		VertexPropagate target = new VertexPropagate();
		target.entity = entity;
		target.data = data;
		return graph.get(target);
	}
	/**
	 * 依据对象属性获取传播图的节点对象，没有则创建。
	 * @param object
	 * @param sign
	 * @return
	 */
	VertexPropagate Create(EntityObject entity, Propagate.Data data) {
		GraphPropagate graph = (GraphPropagate)this.getGraph();
		
		VertexPropagate target = new VertexPropagate();
		target.entity = entity;
		target.data = data;
		Graph.createEdge(this, holder, target, null);
		graph.add(target);
		return target;
	}
	
	/**
	 * 获取下个传播节点对象列表。
	 * @return
	 */
	List<VertexPropagate> getList() {
		EdgeLinkedList edgeNode = this.getEdgeNode(holder);
		return edgeNode != null ? edgeNode.getList() : new ArrayList<>();
	}
	
	boolean isCycle() {
		return this.getGraph().DFSCycleCheck();
	}
}
