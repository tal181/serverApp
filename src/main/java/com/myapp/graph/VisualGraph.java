package com.myapp.graph;

/**
 * Created by Tal on 10/06/2017.
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;

import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JApplet;

import org.jgrapht.graph.ExtendedDefaultWeightedEdge;
import org.jgrapht.graph.GraphNode;
import org.jgraph.JGraph;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphConstants;

import org.jgrapht.ext.JGraphModelAdapter;
import org.jgrapht.graph.*;

/**
 * A demo applet that shows how to use JGraph to visualize JGraphT graphs.
 *
 * @author Barak Naveh
 *
 * @since Aug 3, 2003
 */
public class VisualGraph extends JApplet {
    private static final Color     DEFAULT_BG_COLOR = Color.decode( "#FAFBFF" );
    private static final Dimension DEFAULT_SIZE = new Dimension( 530, 320 );

    //
    private JGraphModelAdapter m_jgAdapter;

    /**
     * @see java.applet.Applet#init().
     */
    public void init(  ) {
        // create a JGraphT graph

        SimpleDirectedWeightedGraph<GraphNode, ExtendedDefaultWeightedEdge>  g1 =
                new SimpleDirectedWeightedGraph<GraphNode, ExtendedDefaultWeightedEdge>
                        (ExtendedDefaultWeightedEdge.class);


        GraphNode v1=new GraphNode("vertex1");
        GraphNode v2=new GraphNode("vertex2");
        GraphNode v3=new GraphNode("vertex3");
        GraphNode v4=new GraphNode("vertex4");
        g1.addVertex( v1 );
        g1.addVertex( v2);
        g1.addVertex( v3 );
        g1.addVertex( v4 );

        ExtendedDefaultWeightedEdge e1 = g1.addEdge(v1,v2);
        g1.setEdgeWeight(e1, 5);

        ExtendedDefaultWeightedEdge e2 = g1.addEdge(v2,v3);
        g1.setEdgeWeight(e2, 5);

        ExtendedDefaultWeightedEdge e3 = g1.addEdge(v3,v1);
        g1.setEdgeWeight(e3, 5);

        ExtendedDefaultWeightedEdge e4 = g1.addEdge(v4,v3);
        g1.setEdgeWeight(e4, 51);

        // create a visualization using JGraph, via an adapter
        m_jgAdapter = new JGraphModelAdapter( g1 );

        JGraph jgraph = new JGraph( m_jgAdapter );

        adjustDisplaySettings( jgraph );
        getContentPane(  ).add( jgraph );
        resize( DEFAULT_SIZE );

        // add some sample data (graph manipulated via JGraphT)


        // position vertices nicely within JGraph component
        positionVertexAt( v1, 130, 40 );
        positionVertexAt( v2, 60, 200 );
        positionVertexAt( v3, 310, 230 );
        positionVertexAt( v4, 380, 70 );

        // that's all there is to it!...
    }


    private void adjustDisplaySettings( JGraph jg ) {
        jg.setPreferredSize( DEFAULT_SIZE );

        Color  c        = DEFAULT_BG_COLOR;
        String colorStr = null;

        try {
            colorStr = getParameter( "bgcolor" );
        }
        catch( Exception e ) {}

        if( colorStr != null ) {
            c = Color.decode( colorStr );
        }

        jg.setBackground( c );
    }


    private void positionVertexAt( GraphNode vertex, int x, int y ) {
        DefaultGraphCell cell = m_jgAdapter.getVertexCell( vertex );
        Map              attr = cell.getAttributes(  );
        Rectangle2D b    = GraphConstants.getBounds( attr );

        GraphConstants.setBounds( attr, new Rectangle( x, y,new Double( b.getWidth()).intValue(),
                new Double( b.getHeight() ).intValue()) );

        Map cellAttr = new HashMap(  );
        cellAttr.put( cell, attr );
        m_jgAdapter.edit( cellAttr, null, null, null );
    }
}