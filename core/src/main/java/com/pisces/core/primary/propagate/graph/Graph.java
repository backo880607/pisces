package com.pisces.core.primary.propagate.graph;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Stack;
import java.util.TreeMap;
import java.util.function.Consumer;

import com.pisces.core.relation.Sign;

public abstract class Graph  {
	private ArrayList<VertexNode> adjList = new ArrayList<VertexNode>();
	private int invalidVertexNode = 0;
	private static final int maxInvalidVertexNode = 1000; 

	protected abstract EdgeBase createEdge();

	final int getVertexNodeSize() {
		return this.adjList.size();
	}
	
	/**
	 * 创建图
	 * @param observer
	 */
	public static Graph create(VertexData observer) {
		Graph graph = observer.getGraph();
		if (graph == null) {
			graph = observer.createGraph();
			observer.setGraph(graph);
		}
		
		graph.getVertexNode(observer, true);
		return graph;
	}
	/**
	 * 合并图
	 * 
	 * @param graph
	 */
	private void merge(Graph graph) {
		if (graph == null || this == graph)
			return;
		
		final int count = this.adjList.size();
		this.adjList.addAll(graph.adjList);
		for (VertexNode vertexNode : graph.adjList) {
			if (vertexNode.observer != null) {
				vertexNode.observer.setGraph(this);
				vertexNode.observer.adjvex += count;
			}
		}
		
		mergeImpl(graph);
	}

	/**
	 * 子类可以重写该函数，实现自定义合并功能。
	 * @param graph
	 */
	protected void mergeImpl(Graph graph) {
		
	}
	
	/**
	 * 创建边
	 * @param firstObserver
	 * @param firstSign
	 * @param secondObserver
	 * @param secondSign
	 * @return
	 */
	public static boolean createEdge(VertexData firstObserver, Sign firstSign,
			VertexData secondObserver, Sign secondSign) {
		Graph firstGraph = firstObserver.getGraph();
		Graph secondGraph = secondObserver.getGraph();
		if (firstGraph == null) {
			firstGraph = secondGraph == null ? firstObserver.createGraph()
					: secondGraph;
			firstObserver.setGraph(firstGraph);
			if (secondGraph == null) {
				secondObserver.setGraph(firstGraph);
			}
		} else if (secondGraph == null) {
			secondObserver.setGraph(firstGraph);
		} else if (firstGraph != secondGraph) { // 两观察者不在同一图中,合并，将较少的合并到多的里去
			if (firstGraph.adjList.size() < secondGraph.adjList.size()) {
				secondGraph.merge(firstGraph);
				firstGraph = secondGraph;
			} else {
				firstGraph.merge(secondGraph);
			}
		}

		VertexNode firstNode = firstGraph.getVertexNode(firstObserver, true);
		VertexNode secondNode = firstGraph.getVertexNode(secondObserver, true);

		// create the out edge
		if (firstNode.outEdge == null) {
			firstNode.outEdge = new TreeMap<>();
			firstObserver.outEdge = firstNode.outEdge;
		}
		EdgeBase outEdge = firstNode.outEdge.get(firstSign);
		if (outEdge == null) {
			outEdge = firstGraph.createEdge();
			outEdge.sign = firstSign;
			outEdge.reverse = secondSign;
			firstNode.outEdge.put(firstSign, outEdge);
		}
		outEdge.addEdge(secondObserver);

		if (secondSign != null) {
			// create the in edge
			if (secondNode.inEdge == null) {
				secondNode.inEdge = new TreeMap<>();
				secondObserver.inEdge = secondNode.inEdge;
			}
			EdgeBase inEdge = secondNode.inEdge.get(secondSign);
			if (inEdge == null) {
				inEdge = firstGraph.createEdge();
				inEdge.sign = secondSign;
				inEdge.reverse = firstSign;
				secondNode.inEdge.put(secondSign, inEdge);
			}
			inEdge.addEdge(firstObserver);
		}

		return true;
	}

	/**
	 * 获取节点
	 * 
	 * @param observer
	 * @param bCreate	不存在该节点是否创建
	 * @return
	 */
	public VertexNode getVertexNode(VertexData observer, boolean bCreate) {
		if (observer.adjvex >= 0) {
			VertexNode vertexNode = this.adjList.get(observer.adjvex);
			return vertexNode.observer != null ? vertexNode : null;
		}

		if (!bCreate)
			return null;

		VertexNode vertexNode = new VertexNode(observer);
		this.adjList.add(vertexNode);
		observer.adjvex = this.adjList.size() - 1;
		if (observer.getGraph() == null) {
			observer.setGraph(this);
		}
		return vertexNode;
	}

	/**
	 * 获取节点对象
	 * 
	 * @param adjvex
	 * @return
	 */
	VertexData getObserver(int adjvex) {
		if (adjvex >= this.adjList.size()) {
			return null;
		}

		return this.adjList.get(adjvex).observer;
	}

	/**
	 * 获取出度边节点
	 * 
	 * @param observer
	 * @param sign	边类型
	 * @return
	 */
	EdgeBase getOutEdgeNode(VertexData observer, Sign sign) {
		VertexNode node = this.getVertexNode(observer, false);
		if (node == null || node.outEdge == null || sign == null) {
			return null;
		}

		return node.outEdge.get(sign);
	}

	/**
	 * 获取入度边节点
	 * 
	 * @param observer
	 * @param sign	边类型
	 * @return
	 */
	EdgeBase getInEdgeNode(VertexData observer, Sign sign) {
		VertexNode node = this.getVertexNode(observer, false);
		if (node == null || node.inEdge == null || sign == null) {
			return null;
		}

		return node.inEdge.get(sign);
	}

	/**
	 * 获取边节点，考虑出度及入度
	 * 
	 * @param observer
	 * @param sign	边类型
	 * @return
	 */
	EdgeBase getEdgeNode(VertexData observer, Sign sign) {
		VertexNode node = this.getVertexNode(observer, false);
		if (node == null || sign == null) {
			return null;
		}

		EdgeBase edgeNode = null;
		if (node.outEdge != null) {
			edgeNode = node.outEdge.get(sign);
		}
		
		if (edgeNode == null && node.inEdge != null) {
			edgeNode = node.inEdge.get(sign);
		}
		return edgeNode;
	}

	/**
	 * 获取出度节点对象
	 * 
	 * @param observer
	 * @param sign	边类型
	 * @return
	 */
	@SuppressWarnings("unchecked")
	<T> List<T> getOutObservers(VertexData observer, Sign sign) {
		VertexNode node = this.adjList.get(observer.adjvex);
		if (node.outEdge == null) {
			return null;
		}

		List<T> observers = new ArrayList<T>();
		EdgeBase outEdgeNode = node.outEdge.get(sign);
		if (outEdgeNode != null) {
			for (Integer adjvex : outEdgeNode) {
				observers.add((T) this.adjList.get(adjvex).observer);
			}
		}

		return observers;
	}

	/**
	 * 获取入度节点对象
	 * 
	 * @param observer
	 * @param sign	边类型
	 * @return
	 */
	@SuppressWarnings("unchecked")
	<T> List<T> getInObservers(VertexData observer, Sign sign) {
		VertexNode node = this.adjList.get(observer.adjvex);
		if (node.inEdge == null) {
			return null;
		}

		List<T> observers = new ArrayList<T>();
		EdgeBase inEdgeNode = node.inEdge.get(sign);
		if (inEdgeNode != null) {
			for (Integer adjvex : inEdgeNode) {
				observers.add((T) this.adjList.get(adjvex).observer);
			}
		}

		return observers;
	}

	/**
	 * 获取出度及入度节点对象，考虑出度及入度
	 * 
	 * @param observer
	 * @param sign	边类型
	 * @return
	 */
	@SuppressWarnings("unchecked")
	<T extends VertexData> List<T> getObservers(VertexData observer, Sign sign) {
		List<T> observers = new ArrayList<T>();
		EdgeBase edgeNode = this.getEdgeNode(observer, sign);
		if (edgeNode != null) {
			for (Integer adjvex : edgeNode) {
				observers.add((T) this.adjList.get(adjvex).observer);
			}
		}
		
		return observers;
	}
	
	/**
	 * 遍历关联节点对象,包含出度及入度。
	 * @param observer
	 * @param sign
	 * @param fun
	 */
	@SuppressWarnings("unchecked")
	<T extends VertexData> void foreach(VertexData observer, Sign sign, Consumer<T> fun) {
		EdgeBase edgeNode = this.getEdgeNode(observer, sign);
		if (edgeNode != null) {
			for (Integer adjvex : edgeNode) {
				fun.accept((T) this.adjList.get(adjvex).observer);
			}
		}
	}
	
	/**
	 * 移除到指定节点的出度边，但不移除节点
	 * @param firstObserver
	 * @param firstSign
	 * @param secondObserver
	 */
	void removeOutEdge(VertexData firstObserver, Sign firstSign, VertexData secondObserver) {
		EdgeBase outEdge = this.getOutEdgeNode(firstObserver, firstSign);
		if (outEdge != null) {
			EdgeBase inEdge = this.getInEdgeNode(secondObserver, outEdge.reverse);
			if (inEdge != null) {
				inEdge.removeEdge(firstObserver);
			}
			
			outEdge.removeEdge(secondObserver);
		}
	}
	
	/**
	 * 移除指定类型的所有出度边，但不移除节点
	 * @param observer
	 * @param sign
	 */
	void removeOutEdge(VertexData observer, Sign sign) {
		EdgeBase outEdge = this.getOutEdgeNode(observer, sign);
		if (outEdge != null && outEdge.reverse != null) {
			for (int adjvex : outEdge) {
				EdgeBase inEdge = this.adjList.get(adjvex).inEdge.get(outEdge.reverse);
				if (inEdge != null) {
					inEdge.removeEdge(observer);
				}
			}
			outEdge.removeEdge();
		}
	}
	
	/**
	 * 移除到指定节点的入度边，但不移除节点
	 * @param firstObserver
	 * @param firstSign
	 * @param secondObserver
	 */
	void removeInEdge(VertexData firstObserver, Sign firstSign, VertexData secondObserver) {
		EdgeBase inEdge = this.getInEdgeNode(firstObserver, firstSign);
		if (inEdge != null) {
			EdgeBase outEdge = this.getOutEdgeNode(secondObserver, inEdge.reverse);
			if (outEdge != null) {
				outEdge.removeEdge(firstObserver);
			}
			
			inEdge.removeEdge(secondObserver);
		}
	}
	
	/**
	 * 移除指定类型的所有入度边，但不移除节点
	 * @param observer
	 * @param sign
	 */
	void removeInEdge(VertexData observer, Sign sign) {
		EdgeBase inEdge = this.getInEdgeNode(observer, sign);
		if (inEdge != null && inEdge.reverse != null) {
			for (int adjvex : inEdge) {
				EdgeBase outEdge = this.adjList.get(adjvex).outEdge.get(inEdge.reverse);
				if (outEdge != null) {
					outEdge.removeEdge(observer);
				}
			}
			inEdge.removeEdge();
		}
	}
	
	/**
	 * 移除指定类型的关联边，考虑出度及入度。
	 * @param observer
	 * @param sign
	 */
	void removeEdge(VertexData observer, Sign sign) {
		this.removeOutEdge(observer, sign);
		this.removeInEdge(observer, sign);
	}
	
	/**
	 * 移除到指定节点的边，但不移除节点，考虑出度及入度。
	 * @param firstObserver
	 * @param firstSign
	 * @param secondObserver
	 */
	void removeEdge(VertexData firstObserver, Sign firstSign, VertexData secondObserver) {
		this.removeOutEdge(firstObserver, firstSign, secondObserver);
		this.removeInEdge(firstObserver, firstSign, secondObserver);
	}
	
	/**
	 * 移除节点，并移除所有与该节点相关的出度边及入度边
	 * @param observer
	 */
	void removeVertexNode(VertexData observer) {
		VertexNode vertexNode = this.getVertexNode(observer, false);
		if (vertexNode == null) {
			return;
		}
		
		// 移除出度边
		if (vertexNode.outEdge != null) {
			for (Entry<Sign, EdgeBase> entry : vertexNode.outEdge.entrySet()) {
				this.removeOutEdge(observer, entry.getKey());
			}
		}
		vertexNode.outEdge = null;
		
		// 移除入度边
		if (vertexNode.inEdge != null) {
			for (Entry<Sign, EdgeBase> entry : vertexNode.inEdge.entrySet()) {
				this.removeInEdge(observer, entry.getKey());
			}
		}
		vertexNode.inEdge = null;
		
		// 将节点置为无效
		vertexNode.observer = null;
		observer.adjvex = -1;
		observer.graph = null;
		observer.outEdge = null;
		observer.inEdge = null;
		
		// 清除无效的节点
		synchronized (this) {
			++this.invalidVertexNode;
			if (this.invalidVertexNode >= Graph.maxInvalidVertexNode) {
				clearInvalidVertexNode();
			}
		}
	}
	
	private void clearInvalidVertexNode() {
		int index = 0;
		boolean bFind = false;
		Iterator<VertexNode> iter = this.adjList.iterator();
		while (iter.hasNext()) {
			VertexNode vertexNode = iter.next();
			if (vertexNode.observer == null) {
				iter.remove();
				bFind = true;
			}
			
			if (!bFind)
				++index;
		}
		
		if (bFind) {
			for (; index < this.adjList.size(); ++index) {
				VertexNode vertexNode = this.adjList.get(index);
				vertexNode.observer.adjvex = index;
			}
		}
		
		this.invalidVertexNode = 0;
	}
	
	/**
	 * 基于DFS（深度优先搜索）的拓扑排序
	 * 
	 * @return
	 */
	protected Stack<VertexData> DFSTopological() {
		boolean visited[] = new boolean[adjList.size()];
		Stack<VertexData> sortedObservers = new Stack<VertexData>();
		for (VertexNode vertexNode : this.adjList) {
			this.DFSTopologicalImpl(visited, vertexNode.observer, sortedObservers);
		}
		return sortedObservers;
	}

	protected Stack<VertexData> DFSTopological(VertexData observer) {
		boolean visited[] = new boolean[adjList.size()];
		Stack<VertexData> sortedObservers = new Stack<VertexData>();
		this.DFSTopologicalImpl2(visited, observer, sortedObservers);
		return sortedObservers;
	}

	private void DFSTopologicalImpl(boolean[] visited, VertexData observer,
			Stack<VertexData> sortedObservers) {
		if (!visited[observer.adjvex]) {
			visited[observer.adjvex] = true;
			
			VertexNode node = this.adjList.get(observer.adjvex);
			if (node.outEdge != null) {
				for (Entry<Sign, EdgeBase> entry : node.outEdge.entrySet()) {
					EdgeBase outEdgeNode = entry.getValue();
					if (outEdgeNode != null) {
						for (int adjvex : outEdgeNode) {
							DFSTopologicalImpl(visited,
									this.adjList.get(adjvex).observer,
									sortedObservers);
						}
					}
				}
			}

			sortedObservers.add(observer);
		}
	}
	
	private void DFSTopologicalImpl2(boolean[] visited, VertexData observer,
			Stack<VertexData> sortedObservers) {
		if (observer.isObversePropagate()) {
			DFSTopologicalObverse(visited, observer, sortedObservers);
		} else {
			DFSTopologicalConverse(visited, observer, sortedObservers);
		}
	}
	
	/**
	 * 正向拓扑排序
	 * @param visited
	 * @param observer
	 * @param sortedObservers
	 */
	private void DFSTopologicalObverse(boolean[] visited, VertexData observer,
			Stack<VertexData> sortedObservers) {
		if (!visited[observer.adjvex]) {
			visited[observer.adjvex] = true;
			
			VertexNode node = this.adjList.get(observer.adjvex);
			if (node.outEdge != null) {
				for (Entry<Sign, EdgeBase> entry : node.outEdge.entrySet()) {
					EdgeBase outEdgeNode = entry.getValue();
					if (outEdgeNode != null) {
						for (int nextAdjvex : outEdgeNode) {
							DFSTopologicalImpl2(visited,
									this.adjList.get(nextAdjvex).observer,
									sortedObservers);
						}
					}
				}
			}

			sortedObservers.add(observer);
		}
	}
	
	/**
	 * 逆向拓扑排序
	 * @param visited
	 * @param observer
	 * @param sortedObservers
	 */
	private void DFSTopologicalConverse(boolean[] visited, VertexData observer,
			Stack<VertexData> sortedObservers) {
		if (!visited[observer.adjvex]) {
			visited[observer.adjvex] = true;
			
			VertexNode node = this.adjList.get(observer.adjvex);
			if (node.inEdge != null) {
				for (Entry<Sign, EdgeBase> entry : node.inEdge.entrySet()) {
					EdgeBase inEdgeNode = entry.getValue();
					if (inEdgeNode != null) {
						for (int nextAdjvex : inEdgeNode) {
							DFSTopologicalImpl2(visited,
									this.adjList.get(nextAdjvex).observer,
									sortedObservers);
						}
					}
				}
			}

			sortedObservers.add(observer);
		}
	}

	/**
	 * 循环检测，采用基于深度优先遍历方法检测
	 * 
	 * @return true 存在环
	 */
	public boolean DFSCycleCheck() {
		short visited[] = new short[this.adjList.size()];
		for (int i = 0; i < visited.length; ++i) {
			visited[i] = 0;
		}

		for (int i = 0; i < this.adjList.size(); ++i) {
			if (visited[i] == 0) {
				if (DFSCycleCheckImpl(i, visited))
					return true;
			}
		}

		return false;
	}

	protected boolean DFSCycleCheck(VertexData observer) {
		short visited[] = new short[this.adjList.size()];
		return DFSCycleCheckImpl(observer.adjvex, visited);
	}
	
	private boolean DFSCycleCheckImpl(int index, short visited[]) {
		visited[index] = -1;

		VertexNode node = this.adjList.get(index);
		if (node.outEdge != null) {
			for (Entry<Sign, EdgeBase> entry : node.outEdge.entrySet()) {
				for (Integer adjvex : entry.getValue()) {
					if (visited[adjvex] == -1) {
						return true;
					} else if (visited[adjvex] == 0 && DFSCycleCheckImpl(adjvex, visited)) {
						return true;
					}
				}
			}
		}

		visited[index] = 1;
		return false;
	}
}
