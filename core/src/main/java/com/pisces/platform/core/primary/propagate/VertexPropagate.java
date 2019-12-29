package com.pisces.platform.core.primary.propagate;

import com.pisces.platform.core.entity.EntityObject;
import com.pisces.platform.core.primary.propagate.graph.EdgeLinkedList;
import com.pisces.platform.core.primary.propagate.graph.Graph;
import com.pisces.platform.core.primary.propagate.graph.VertexData;
import com.pisces.platform.core.relation.Sign;

import java.util.ArrayList;
import java.util.List;

class VertexPropagate extends VertexData implements Comparable<VertexPropagate> {
    public static final Sign HOLDER = new Sign();
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
        if (this.entity.equals(arg0.entity)) {
            return this.data.id - arg0.data.id;
        }
        return this.entity.getId().compareTo(arg0.entity.getId());
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj;
    }

    VertexPropagate get(EntityObject entity, Propagate.Data data) {
        GraphPropagate graph = (GraphPropagate) this.getGraph();
        VertexPropagate target = new VertexPropagate();
        target.entity = entity;
        target.data = data;
        return graph.get(target);
    }

    /**
     * 依据对象属性获取传播图的节点对象，没有则创建。
     *
     * @param entity
     * @param data
     * @return
     */
    VertexPropagate create(EntityObject entity, Propagate.Data data) {
        GraphPropagate graph = (GraphPropagate) this.getGraph();

        VertexPropagate target = new VertexPropagate();
        target.entity = entity;
        target.data = data;
        Graph.createEdge(this, HOLDER, target, null);
        graph.add(target);
        return target;
    }

    /**
     * 获取下个传播节点对象列表。
     *
     * @return
     */
    List<VertexPropagate> getList() {
        EdgeLinkedList edgeNode = this.getEdgeNode(HOLDER);
        return edgeNode != null ? edgeNode.getList() : new ArrayList<>();
    }

    boolean isCycle() {
        return this.getGraph().dfsCycleCheck();
    }
}
