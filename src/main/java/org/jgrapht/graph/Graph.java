package org.jgrapht.graph;

/**
 * Created by Tal on 10/06/2017.
 */
public class Graph {

    SimpleDirectedWeightedGraph<GraphNode, DefaultWeightedEdge> graph;

    public SimpleDirectedWeightedGraph<GraphNode, DefaultWeightedEdge> getGraph() {
        return graph;
    }

    public void setGraph(SimpleDirectedWeightedGraph<GraphNode, DefaultWeightedEdge> graph) {
        this.graph = graph;
    }

    public Graph(){
        this.graph = new SimpleDirectedWeightedGraph<GraphNode, DefaultWeightedEdge>
                        (DefaultWeightedEdge.class);
    }
}
