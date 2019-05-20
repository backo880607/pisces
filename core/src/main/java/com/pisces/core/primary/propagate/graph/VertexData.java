package com.pisces.core.primary.propagate.graph;

import java.util.Map;
import java.util.TreeMap;

import com.pisces.core.relation.Sign;

public abstract class VertexData {
	transient Graph graph = null;
	transient int adjvex = -1;
	
	transient Map<Sign, EdgeBase> outEdge;	// 方便调试时查看关联对象
	transient Map<Sign, EdgeBase> inEdge;		// 方便调试时查看关联对象
	
	protected abstract Graph createGraph();
	
	protected final Graph getGraph() {
		return this.graph;
	}
	
	final void setGraph(Graph graph) {
		this.graph = graph;
	}
	
	public void CreateEdge() {
		if (this.outEdge == null) {
			this.outEdge = new TreeMap<>();
		}
	}
	protected void createSign(Sign sign) {
		if (this.outEdge == null) {
			this.outEdge = new TreeMap<>();
		}
		this.outEdge.put(sign, null);
	}
	
	/**
	 * 由枚举标识获取相关联的对象边。
	 * @param sign
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected final <T extends EdgeBase> T getEdgeNode(Sign sign) {
		EdgeBase edgeNode = null;
		if (this.outEdge != null) {
			edgeNode = this.outEdge.get(sign);
		}
		
		if (edgeNode == null && this.inEdge != null) {
			edgeNode = this.inEdge.get(sign);
		}
		
		return (T)edgeNode;
	}
	
	/**
	 * 判断是否正向传播
	 * @return true为正向传播，false逆向传播
	 */
	protected boolean isObversePropagate() {
		return true;
	}
}
