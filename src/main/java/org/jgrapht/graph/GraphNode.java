package org.jgrapht.graph;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tal on 24/05/2017.
 */
public class GraphNode {
    Double weight;
    String vertexName;
    Double totalSum;
    Boolean visited;
    Integer hops;


    public List<String> getFathers() {
        return fathers;
    }

    public void setFathers(List<String> fathers) {
        this.fathers = fathers;
    }

    List<String> fathers;
    public GraphNode() {
        this.vertexName="";
        this.weight=0D;
        this.visited=false;
        this.hops=0;
        this.totalSum=0D;
        this.fathers=new ArrayList<>();
    }
    public GraphNode(String vertexName) {
        this.vertexName=vertexName;
        this.weight=0D;
        this.visited=false;
        this.hops=0;
        this.totalSum=0D;
        this.fathers=new ArrayList<>();

    }

    public GraphNode(String vertexName, double weight,Boolean visited,Integer hops,Double totalSum) {
        this.vertexName=vertexName;
        this.weight=weight;
        this.visited=visited;
        this.hops=hops;
        this.totalSum=totalSum;
        this.fathers=new ArrayList<>();
    }


    public Double getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(Double totalSum) {
        this.totalSum = totalSum;
    }



    public Boolean getVisited() {
        return visited;
    }

    public void setVisited(Boolean visited) {
        this.visited = visited;
    }

    public Integer getHops() {
        return hops;
    }

    public void setHops(Integer hops) {
        this.hops = hops;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getVertexName() {
        return vertexName;
    }

    public void setVertexName(String vertexName) {
        this.vertexName = vertexName;
    }


    @Override
    public String toString() {
        return  vertexName ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GraphNode graphNode = (GraphNode) o;

 //       if (weight != null ? !weight.equals(graphNode.weight) : graphNode.weight != null) return false;
        if (!vertexName.equals(graphNode.vertexName)) return false;

        return true;

 //       if (totalSum != null ? !totalSum.equals(graphNode.totalSum) : graphNode.totalSum != null) return false;
       // if (visited != null ? !visited.equals(graphNode.visited) : graphNode.visited != null) return false;
//        if (hops != null ? !hops.equals(graphNode.hops) : graphNode.hops != null) return false;
//        return fathers.equals(graphNode.fathers);
    }

    @Override
    public int hashCode() {
 //       int result = weight != null ? weight.hashCode() : 0;
        int    result = 31 * 1 + vertexName.hashCode();
        //result = 31 * result + (totalSum != null ? totalSum.hashCode() : 0);
        //result = 31 * result + (visited != null ? visited.hashCode() : 0);
       // result = 31 * result + (hops != null ? hops.hashCode() : 0);
       // result = 31 * result + fathers.hashCode();
        return result;
    }
}
