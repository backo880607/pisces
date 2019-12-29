package com.pisces.platform.core.primary.propagate.graph;

import com.pisces.platform.core.relation.Sign;

import java.util.TreeMap;

/**
 * 图节点
 *
 * @author niuhaitao
 */
class VertexNode {
    public VertexData observer;
    public TreeMap<Sign, EdgeBase> outEdge;
    public TreeMap<Sign, EdgeBase> inEdge;

    public VertexNode(VertexData value) {
        this.observer = value;
        outEdge = null;
        inEdge = null;
    }
}
