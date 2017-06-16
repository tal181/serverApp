package com.myapp.api.algorithm;

import org.jgrapht.graph.GraphNode;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by Tal on 17/05/2017.
 */
@Component
public class Hamdan {

    @Autowired
    HamdanUtils hamdanUtils;

    private Double rating;
    private Integer duration;

    private static Integer MAX_HOPS=3;
    public static Map<Double,GraphNode> allPaths=new HashMap<Double,GraphNode>();

    public Hamdan(){

    }
    public Hamdan(Double rating,Integer duration){
        this.rating=rating;
        this.duration=duration;

    }


    public  void runHamdan(SimpleDirectedWeightedGraph graph, GraphNode s) throws Exception{
        GraphNode minPath=new GraphNode();
        minPath.setTotalSum(Double.MAX_VALUE);

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
                List<GraphNode> list = hamdanUtils.getAllVertex( graph, s);

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

        System.out.println("end");
    }
}
