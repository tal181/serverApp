package org.jgrapht.graph;

import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;

/**
 * Created by Tal on 13/06/2017.
 */
public class ExtendedDefaultWeightedEdge  extends DefaultWeightedEdge
{
    private static final long serialVersionUID = 2297087064673509942L;

    /**
     * Retrieves the weight of this edge. This is protected, for use by subclasses only (e.g. for
     * implementing toString).
     *
     * @return weight of this edge
     */
    public double getWeight()
    {
        return weight;
    }
    
    public String toString(){
        return ""+ weight;
    }
}
