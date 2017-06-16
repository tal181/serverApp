package com.myapp.api.tripAdvisor;

import com.myapp.SpringBootJerseyApplication;
import com.myapp.api.activity.ActivityApi;
import com.myapp.api.estimateActivity.EstimateActivityApi;
import com.myapp.api.location.LocationApi;
import org.jgrapht.graph.Graph;
import org.jgrapht.graph.GraphNode;
import com.myapp.domain.activity.Activity;
import com.myapp.domain.activity.ActivityEstimateTimeDistance;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by tal on 26/04/2017.
 */
@Component
public class TAImpl implements TAApi{

    @Autowired
    EstimateActivityApi estimateActivityApi;

    @Autowired
    ActivityApi activityApi;

    @Autowired
    LocationApi locationApi;

    private final Logger log = LoggerFactory.getLogger(TAImpl.class);

    public  void getData() throws Exception{
        ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringBootJerseyApplication.class);
        ManageTA manageTA1 =(ManageTA) ctx.getBean("manageTA");
        manageTA1.setLocation(locationApi.getLocationByName("New York City, New York"));
        Thread thread = new Thread(manageTA1);
        thread.start();

//        ManageTA manageTA2 = (ManageTA)ctx.getBean("manageTA");
//        manageTA2.setLocation("Tel Aviv, Israel");
//        Thread thread2 = new Thread(manageTA2);
//        thread2.start();
//
//        ManageTA manageTA3 = (ManageTA)ctx.getBean("manageTA");
//        manageTA3.setLocation("New York City, New York");
//        Thread thread3 = new Thread(manageTA3);
//        thread3.start();


    }

    @Override
    public void syncEstimateTime() throws Exception{
        ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringBootJerseyApplication.class);
        CalcEstimateTimeThread  calcEstimateTimeThread = (CalcEstimateTimeThread)ctx.getBean("calcEstimateTimeThread");
        calcEstimateTimeThread.run();
    }

    @Override
    //todo refactor
    public Graph buildLocationActivitiesGraph(String locationId) throws Exception{
        Graph graph = new Graph();

        locationId="59445320f74e014908455371"; //new york
        List<Activity> activities = activityApi.getActivitiesByLocation(locationId);
        activities.forEach(activity -> {
            try {
                activity.getRating();
                ActivityEstimateTimeDistance activityEstimateTimeDistance=
                        estimateActivityApi.getEstimateActivities(activity.getActivityId());

                GraphNode sourceNode = new GraphNode(activity.getActivityName() + " " + activity.getActivityId());
                graph.getGraph().addVertex(sourceNode);

                activityEstimateTimeDistance.getActivities().forEach(connectedVertex->{
                    try{

                        //todo bulk fetch
                        Activity targetVertex=activityApi.getActivityById(connectedVertex.getActivityId());
                        GraphNode targetNode = new GraphNode(targetVertex.getActivityName() + " "+ targetVertex.getActivityId());
                        graph.getGraph().addVertex(targetNode);

                        DefaultWeightedEdge edge = graph.getGraph().addEdge(sourceNode,targetNode);
                        graph.getGraph().setEdgeWeight(edge, connectedVertex.getDuration());
                    }
                    catch (Exception e){
                        log.error("Failed to find target activity " + connectedVertex.getActivityId(),e);
                    }

                });
            }
            catch (Exception e){
                log.error("Failed to get time estimation for activity " +activity.getActivityId(),e);
            }
       });

        return graph;

    }

}
