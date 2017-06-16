package com.myapp.api.algorithm;

import org.jgrapht.graph.GraphNode;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Tal on 09/06/2017.
 */

@Component
public class HamdanUtils {

    public  List<GraphNode> getAllVertex(SimpleDirectedWeightedGraph graph, GraphNode v1) throws Exception{
        Set<DefaultWeightedEdge> edges= graph.edgesOf(v1);
        List nodes= new ArrayList();
        for (DefaultWeightedEdge edge : edges) {
            DefaultEdge privateObject = edge;


            String source=getVertexName(privateObject,"getSource");
            String target=getVertexName(privateObject,"getTarget");
            double weight=getVertexWeight(edge,"getWeight");

            if(!source.equals(v1.getVertexName())){

                GraphNode graphNode =new GraphNode(source);
                if(!v1.getFathers().contains(source)) {
                    graphNode.setWeight(weight);
                    nodes.add(graphNode);
                }
            }
            if(!target.equals(v1.getVertexName())){
                GraphNode graphNode =new GraphNode(target);
                if(!v1.getFathers().contains(target)) {
                    graphNode.setWeight(weight);
                    nodes.add(graphNode);
                }
            }


        }
        return nodes;
    }
    private  String getVertexName(DefaultEdge privateObject,String methodName) throws Exception{
        Method privateStringMethod = DefaultEdge.class.
                getDeclaredMethod(methodName, null);

        privateStringMethod.setAccessible(true);

        GraphNode returnValue = (GraphNode)
                privateStringMethod.invoke(privateObject, null);

        return returnValue.getVertexName();
    }

    private  double getVertexWeight(DefaultWeightedEdge privateObject,String methodName) throws Exception{
        Method privateStringMethod = DefaultWeightedEdge.class.
                getDeclaredMethod(methodName, null);

        privateStringMethod.setAccessible(true);

        double returnValue = (double)
                privateStringMethod.invoke(privateObject, null);

        return returnValue;
    }

    public void removePath(SimpleDirectedWeightedGraph graph,GraphNode minPath){
        Set verticesToRemove = new HashSet();
        minPath.getFathers().forEach(item->{
            GraphNode vertex=new GraphNode(item);
            verticesToRemove.add(vertex);
        });
       // graph.removeAllVertices(verticesToRemove);

        //delete vertices that doesn't have edges
        //verticesToRemove = new ArrayList();
        Set<GraphNode> vertexSet=graph.vertexSet();
        if(vertexSet!=null){
            vertexSet.forEach(item->{
                GraphNode vertex=new GraphNode(item.getVertexName());
                if(graph.incomingEdgesOf(vertex)==null && graph.outgoingEdgesOf(vertex)==null) {
                    verticesToRemove.add(vertex);
                }

            });
        }
        graph.removeAllVertices(verticesToRemove);
    }

}
