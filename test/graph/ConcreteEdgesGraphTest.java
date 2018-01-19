/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests for ConcreteEdgesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteEdgesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteEdgesGraphTest extends GraphInstanceTest {
    
    /*
     * Provide a ConcreteEdgesGraph for tests in GraphInstanceTest.
     */
    @Override public Graph<String> emptyInstance() {
        return new ConcreteEdgesGraph<String>();
    }
    
    /*
     * Testing Edge...
     */
    
    @SuppressWarnings("unused")
	@Test 
    public void testEdgeImmutability() {
    	String s = "A";
    	String t = "B";
    	int w = 2;
    	Edge<String> e = new Edge<>(s, t, w);
    	s = "asdfghjkl";
    	t = "lkjhgfdsa";
    	w = 5;
    	assertEquals("Expected no change in Edge",
    			e.getSource(), "A");
    	assertEquals("Expected no change in Edge",
    			e.getTarget(), "B");
    	assertEquals("Expected no change in Edge",
    			e.getWeight(), 2);
    	
    	String ss = e.getSource();
    	String tt = e.getTarget();
    	int ww = e.getWeight();
    	
    	ss += "foobar";
    	tt += "foobarbaz";
    	ww += 95;
    	
    	assertEquals("Expected no change in Edge",
    			e.getSource(), "A");
    	assertEquals("Expected no change in Edge",
    			e.getTarget(), "B");
    	assertEquals("Expected no change in Edge",
    			e.getWeight(), 2);
    	
    }
    
}
