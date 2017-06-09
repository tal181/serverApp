package com.myapp.graph;

import org.jgrapht.*;
import org.jgrapht.alg.*;
import org.jgrapht.graph.*;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class Graph {
    private static Integer MAX_HOPS=3;
    public static Map<String,GraphNode> allPaths=new HashMap<String,GraphNode>();

    public static void main(String args[]) throws Exception{

        //SimpleDirectedWeightedGraph graph=genGraph();

        SimpleDirectedWeightedGraph graph=genGraph2();
        //get all paths with size 3

        String startVertex="vertex1";

        GraphNode node= new GraphNode ( startVertex, 0 ,false,0,0D);
        BFS(graph,node);

    }
    public static SimpleDirectedWeightedGraph genGraph(){

        SimpleDirectedWeightedGraph<String, DefaultWeightedEdge>  graph =
                new SimpleDirectedWeightedGraph<String, DefaultWeightedEdge>
                        (DefaultWeightedEdge.class);

        graph.addVertex("vertex1");
        graph.addVertex("vertex2");
        graph.addVertex("vertex3");
        graph.addVertex("vertex4");
        graph.addVertex("vertex5");


        DefaultWeightedEdge e1 = graph.addEdge("vertex1", "vertex2");
        graph.setEdgeWeight(e1, 5);

        DefaultWeightedEdge e2 = graph.addEdge("vertex2", "vertex3");
        graph.setEdgeWeight(e2, 3);

        DefaultWeightedEdge e3 = graph.addEdge("vertex4", "vertex5");
        graph.setEdgeWeight(e3, 6);

        DefaultWeightedEdge e4 = graph.addEdge("vertex2", "vertex4");
        graph.setEdgeWeight(e4, 2);



        DefaultWeightedEdge e6 = graph.addEdge("vertex2", "vertex5");
        graph.setEdgeWeight(e6, 9);

        DefaultWeightedEdge e7 = graph.addEdge("vertex4", "vertex1");
        graph.setEdgeWeight(e7, 7);

        DefaultWeightedEdge e9 = graph.addEdge("vertex1", "vertex3");
        graph.setEdgeWeight(e9, 10);

        DefaultWeightedEdge e10 = graph.addEdge("vertex3", "vertex5");
        graph.setEdgeWeight(e10, 1);

        return graph;
    }

    public static SimpleDirectedWeightedGraph genGraph2(){
        SimpleDirectedWeightedGraph<GraphNode, DefaultWeightedEdge>  graph =
                new SimpleDirectedWeightedGraph<GraphNode, DefaultWeightedEdge>
                        (DefaultWeightedEdge.class);

        GraphNode v1=new GraphNode("vertex1");
        graph.addVertex(v1);

        GraphNode v2=new GraphNode("vertex2");
        graph.addVertex(v2);

        GraphNode v3=new GraphNode("vertex3");
        graph.addVertex(v3);

        GraphNode v4=new GraphNode("vertex4");
        graph.addVertex(v4);

        DefaultWeightedEdge e1 = graph.addEdge(v1, v2);
        graph.setEdgeWeight(e1, 5);

        DefaultWeightedEdge e2 = graph.addEdge(v2, v3);
        graph.setEdgeWeight(e2, 6);

        DefaultWeightedEdge e3 = graph.addEdge(v3, v4);
        graph.setEdgeWeight(e3, 7);

        DefaultWeightedEdge e4 = graph.addEdge(v4, v1);
        graph.setEdgeWeight(e4, 17);
        return graph;


    }

    public static void BFS(SimpleDirectedWeightedGraph graph,GraphNode s) throws Exception{

        // Create a queue for BFS
        LinkedList<GraphNode> queue = new LinkedList<GraphNode>();

        // Mark the current node as visited and enqueue it
        queue.add(s);

        while (queue.size() != 0) {
            // Dequeue a vertex from queue and print it
            s = queue.poll();

            // Get all adjacent vertices of the dequeued vertex s
            // If a adjacent has not been visited, then mark it
            // visited and enqueue it
            if(s.getHops()<MAX_HOPS) {
                List<GraphNode> list = getAllVertex( graph, s);

                for(GraphNode graphNode: list){
                    List fathers = new ArrayList();
                    fathers.addAll(s.getFathers());
                    fathers.add(s.getVertexName());

                    graphNode.setFathers(fathers);

                    graphNode.setHops(s.getHops() + 1);
                    graphNode.setTotalSum(s.getTotalSum() + graphNode.getWeight());

                    queue.add(graphNode);
                   // allNodes.put(graphNode.getVertexName(),graphNode);
                }

            }
            else{
                s.getFathers().add(s.getVertexName());

                s.getFathers().forEach(item->{
                    System.out.print(item+"->");
                });
                  //  System.out.println("Vertex is " + key + " fathers are " + value);}

            }
            System.out.println("");
        }

        System.out.println("end");
    }


    private static List<GraphNode>  getAllVertex(SimpleDirectedWeightedGraph graph,  GraphNode v1) throws Exception{
        Set<DefaultWeightedEdge> edges= graph.edgesOf(v1);
        List nodes= new ArrayList();
        for (DefaultWeightedEdge edge : edges) {
            DefaultEdge privateObject = edge;


            String source=getVertexName(privateObject,"getSource");
            String target=getVertexName(privateObject,"getTarget");
            double weight=getVertexWeight(edge,"getWeight");
//            System.out.println("source = " + source);
//            System.out.println("target = " + target);
//            System.out.println("weight = " + weight);

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
    private static String getVertexName(DefaultEdge privateObject,String methodName) throws Exception{
        Method privateStringMethod = DefaultEdge.class.
                getDeclaredMethod(methodName, null);

        privateStringMethod.setAccessible(true);

        GraphNode returnValue = (GraphNode)
                privateStringMethod.invoke(privateObject, null);

        return returnValue.getVertexName();
    }

    private static double getVertexWeight(DefaultWeightedEdge privateObject,String methodName) throws Exception{
        Method privateStringMethod = DefaultWeightedEdge.class.
                getDeclaredMethod(methodName, null);

        privateStringMethod.setAccessible(true);

        double returnValue = (double)
                privateStringMethod.invoke(privateObject, null);

        return returnValue;
    }
}