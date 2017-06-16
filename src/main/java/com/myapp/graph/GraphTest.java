package com.myapp.graph;

import com.myapp.api.algorithm.HamdanUtils;
import org.jgrapht.graph.GraphNode;
import org.jgrapht.graph.*;

import java.lang.reflect.Method;
import java.util.*;

public class GraphTest {
    private static Integer MAX_HOPS=2;
    public static Map<Double,GraphNode> allPaths=new HashMap<Double,GraphNode>();

    public static GraphNode minPath=new GraphNode();
    public static void main(String args[]) throws Exception{

        minPath.setTotalSum(Double.MAX_VALUE);

        //SimpleDirectedWeightedGraph graph=genGraph();

        SimpleDirectedWeightedGraph graph=genGraph();
        //get all paths with size 3

        String startVertex="vertex1";

        GraphNode node= new GraphNode ( startVertex, 0 ,false,0,0D);
        BFS(graph,node);

        HamdanUtils HamdanUtils= new HamdanUtils();
        HamdanUtils.removePath(graph,minPath);

        startVertex="vertex3";
        node= new GraphNode ( startVertex, 0 ,false,0,0D);
        BFS(graph,node);

    }
    public static SimpleDirectedWeightedGraph genGraph(){

        SimpleDirectedWeightedGraph<GraphNode, DefaultWeightedEdge>  graph =
                new SimpleDirectedWeightedGraph<GraphNode, DefaultWeightedEdge>
                        (DefaultWeightedEdge.class);
        GraphNode v1=new GraphNode("vertex1");
        GraphNode v2=new GraphNode("vertex2");
        GraphNode v3=new GraphNode("vertex3");
        GraphNode v4=new GraphNode("vertex4");
        GraphNode v5=new GraphNode("vertex5");
        graph.addVertex(v1);
        graph.addVertex(v2);
        graph.addVertex(v3);
        graph.addVertex(v4);
        graph.addVertex(v5);


        DefaultWeightedEdge e1 = graph.addEdge(v1,v2);
        graph.setEdgeWeight(e1, 5);

        DefaultWeightedEdge e2 = graph.addEdge(v2,v3);
        graph.setEdgeWeight(e2, 3);

        DefaultWeightedEdge e3 = graph.addEdge(v4,v5);
        graph.setEdgeWeight(e3, 6);

        DefaultWeightedEdge e4 = graph.addEdge(v2,v4);
        graph.setEdgeWeight(e4, 2);



        DefaultWeightedEdge e6 = graph.addEdge(v2,v5);
        graph.setEdgeWeight(e6, 9);

        DefaultWeightedEdge e7 = graph.addEdge(v4,v1);
        graph.setEdgeWeight(e7, 7);

        DefaultWeightedEdge e9 = graph.addEdge(v1,v3);
        graph.setEdgeWeight(e9, 10);

        DefaultWeightedEdge e10 = graph.addEdge(v3,v5);
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
                allPaths.put(s.getTotalSum(),s);

                if(minPath.getTotalSum()>s.getTotalSum()){
                    minPath=s;
                }
                s.getFathers().forEach(item->{
                    System.out.print(item+"->");
                });
                  //  System.out.println("Vertex is " + key + " fathers are " + value);}

            }
            System.out.println("");
        }
        //to do case that found less than max

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