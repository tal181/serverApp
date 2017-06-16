package com.tests;

import com.myapp.api.activity.ActivityApi;
import com.myapp.api.tripAdvisor.TAApi;
import com.tests.db.AbstractLiquiBase;
import com.tests.db.TestContex;
import org.jgraph.JGraph;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphConstants;
import org.jgrapht.ext.JGraphModelAdapter;
import org.jgrapht.graph.Graph;
import org.jgrapht.graph.GraphNode;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Tal on 14/06/2017.
 */

public class GraphTest extends TestContex {
    private static final Color DEFAULT_BG_COLOR = Color.decode( "#FAFBFF" );
    private static final Dimension DEFAULT_SIZE = new Dimension( 530, 320 );

    @Autowired
    TAApi tAApi;

    private JGraphModelAdapter m_jgAdapter;

    @Test
    public void sayHello() throws Exception{
        String locationId="59445320f74e014908455371"; // new york
        Graph graph= tAApi.buildLocationActivitiesGraph(locationId);

        m_jgAdapter = new JGraphModelAdapter( graph.getGraph() );

        JGraph jgraph = new JGraph( m_jgAdapter );

        adjustDisplaySettings( jgraph );
        getContentPane(  ).add( jgraph );
        resize( DEFAULT_SIZE );

        // add some sample data (graph manipulated via JGraphT)


        // position vertices nicely within JGraph component
//        positionVertexAt( v1, 130, 40 );
//        positionVertexAt( v2, 60, 200 );
//        positionVertexAt( v3, 310, 230 );
//        positionVertexAt( v4, 380, 70 );

        // that's all there is to it!...
        System.out.println();
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


    private void positionVertexAt(GraphNode vertex, int x, int y ) {
        DefaultGraphCell cell = m_jgAdapter.getVertexCell( vertex );
        Map attr = cell.getAttributes(  );
        Rectangle2D b    = GraphConstants.getBounds( attr );

        GraphConstants.setBounds( attr, new Rectangle( x, y,new Double( b.getWidth()).intValue(),
                new Double( b.getHeight() ).intValue()) );

        Map cellAttr = new HashMap(  );
        cellAttr.put( cell, attr );
        m_jgAdapter.edit( cellAttr, null, null, null );
    }
}
