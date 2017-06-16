package com.myapp.api.tripAdvisor;

import org.jgrapht.graph.Graph;

/**
 * Created by tal on 26/04/2017.
 */
public interface TAApi {

    void getData() throws Exception;

    void syncEstimateTime() throws Exception;

    Graph buildLocationActivitiesGraph(String location) throws Exception;
}
