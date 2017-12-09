/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

/**
 * Tests for ConcreteVerticesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteVerticesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteVerticesGraphTest extends GraphInstanceTest {
    
    /*
     * Provide a ConcreteVerticesGraph for tests in GraphInstanceTest.
     */
    @Override public Graph<String> emptyInstance() {
        return new ConcreteVerticesGraph();
    }
    
    /*
     * Testing ConcreteVerticesGraph...
     */
    
    // Testing strategy for ConcreteVerticesGraph.toString()
    //   TODO
    
    // TODO tests for ConcreteVerticesGraph.toString()
    
    /*
     * Testing Vertex...
     */
    
    // Testing strategy for Vertex
    //   TODO
    
    // TODO tests for operations of Vertex
    
    /*
     * setEdgeTo:
     * - set a new edge with a value
     * - set a nonexistent edge to zero
     * - set an existent edge to zero
     * - set an existent edge to a new value
     */
    @Test
    public void testSetEdgeToNewEdge() {
    	Vertex a = new Vertex("A");
    	Vertex b = new Vertex("B");
    	
    	a.setEdgeTo(b, 1);
    	Map<Vertex, Integer> targets = a.getOutwardEdges();
    	
    	assertTrue("expected A to have B as a target",
    			targets.containsKey(b));
    	assertTrue("expected A to have an edge to B of weight 1",
    			targets.containsValue(1));
    	assertEquals("expected only one entry in a.targets",
    			1, targets.size());
    }
    
    @Test
    public void testSetExistingEdgeToZero() {
    	Vertex a = new Vertex("A");
    	Vertex b = new Vertex("B");
    	
    	a.setEdgeTo(b, 1);
    	a.setEdgeTo(b, 0);
    	
    	assertTrue("expected no targets at A",
    			a.getOutwardEdges().isEmpty());
    }
    
    @Test
    public void testSetNonexistentEdgeToZero() {
    	Vertex a = new Vertex("A");
    	Vertex b = new Vertex("B");
    	
    	a.setEdgeTo(b,  0);
    	
    	assertTrue("expected no targets at A",
    			a.getOutwardEdges().isEmpty());
    }
    
    @Test
    public void testSetExistingEdgeToNewValue() {
    	Vertex a = new Vertex("A");
    	Vertex b = new Vertex("B");
    	
    	a.setEdgeTo(b, 1);
    	a.setEdgeTo(b, 2);
    	
    	Map<Vertex, Integer> outwardEdges = a.getOutwardEdges();
    	
    	assertTrue("expected A to have B as a target",
    			outwardEdges.containsKey(b));
    	assertTrue("expected A to have an edge to B of weight 1",
    			outwardEdges.containsValue(1));
    	assertEquals("expected only one entry in a.targets",
    			1, outwardEdges.size());
    }
    
}
