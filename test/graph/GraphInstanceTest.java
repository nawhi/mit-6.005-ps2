/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

/**
 * Tests for instance methods of Graph.
 * 
 * <p>PS2 instructions: you MUST NOT add constructors, fields, or non-@Test
 * methods to this class, or change the spec of {@link #emptyInstance()}.
 * Your tests MUST only obtain Graph instances by calling emptyInstance().
 * Your tests MUST NOT refer to specific concrete implementations.
 */
public abstract class GraphInstanceTest {
    
    /*
     * Testing strategy:
     * 
     * Mutator methods that need testing - add(), remove(), set()
     * 
     * add():
     *  - add to an empty graph
     *  - add a label that already exists (return false & do nothing)
     *  
     * remove():
     *  - remove a label that doesn't exist (return false & do nothing)
     *  - remove only label from graph
     *  - remove A in a B, C, D > A graph
     *  - remove A in a A, B, C > D graph
     *  - remove A in an A > B > A graph
     *  - remove B in an A > B > C > A graph
     *  
     * set():
     *  - already existing edge, new weight
     *  - already existing edge, same weight
     *  - already existing edge, 0 weight (remove edge)
     *  - creation of new edge, nonzero weight
     *  - creation of new edge, 0 weight (nothing should happen)
     */
    
    /**
     * Overridden by implementation-specific test classes.
     * 
     * @return a new empty graph of the particular implementation being tested
     */
    public abstract Graph<String> emptyInstance();
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testInitialVerticesEmpty() {
        // TODO you may use, change, or remove this test
        assertEquals("expected new graph to have no vertices",
                Collections.emptySet(), emptyInstance().vertices());
    }
    
    @Test
    public void testAddToEmptyGraph() {
    	Graph<String> g = emptyInstance();
    	assertTrue("expected true return from adding to empty graph", 
    			g.add("v1"));
    	Set<String> vertices = new HashSet<>(g.vertices());
    	assertTrue("expected exactly 1 vertex", vertices.size() == 1);
    	assertTrue("expected vertices to contain \"v1\"",
    			vertices.contains("v1"));
    }
    
    @Test
    public void testAddPreExistingVertex() {
    	Graph<String> g = emptyInstance();
    	g.add("v1");
    	assertFalse("expected false return from adding pre-existing vertex",
    			g.add("v1"));
    	assertTrue("expected exactly 1 vertex", g.vertices().size() == 1);
    	assertTrue("expected vertices to contain \"v1\"",
    			g.vertices().contains("v1"));
    }
    
    /*
     * Tests of set() method
     */
    
    @Test
    public void testNewEdge() {
    	Graph<String> g = emptyInstance();
    	g.add("A");
    	g.add("B");
    	assertEquals("expected 0 return from setting new edge",
    			0, g.set("A", "B", 1));
    	
    	// Expect edge value 1 from A to B
    	assertTrue("expected edge from A to B", 
    			g.targets("A").keySet().contains("B"));
    	assertTrue("expected exactly 1 edge from A", 
    			g.targets("A").keySet().size() == 1);
    	assertTrue("expected A>B edge of value 1",
    			g.targets("A").get("B").equals(1));
    	
    	assertTrue("expected no edges from B",
    			g.targets("B").isEmpty());
    }
    
    @Test
    public void testSetExistingEdgeSameWeight() {
    	Graph<String> g = emptyInstance();
    	g.add("A");
    	g.add("B");
    	g.set("A", "B", 1);
    	assertEquals("expected return of previous edge weight",
    			1, g.set("A", "B", 1));
    	
    	// Expect edge value 1 from A to B
    	assertTrue("expected edge from A to B", 
    			g.targets("A").keySet().contains("B"));
    	assertTrue("expected exactly 1 edge from A", 
    			g.targets("A").keySet().size() == 1);
    	assertTrue("expected A>B edge of value 1",
    			g.targets("A").get("B").equals(1));
    	
    	assertTrue("expected no edges from B",
    			g.targets("B").isEmpty());
    }
    
    @Test
    public void testSetExistingEdgeNewWeight() {
    	Graph<String> g = emptyInstance();
    	g.add("A");
    	g.add("B");
    	g.set("A", "B", 1);
    	assertEquals("expected return of previous edge weight",
    			1, g.set("A", "B", 2));
    	
    	// Expect edge value 2 from A to B
    	assertTrue("expected edge from A to B", 
    			g.targets("A").keySet().contains("B"));
    	assertTrue("expected exactly 1 edge from A", 
    			g.targets("A").keySet().size() == 1);
    	assertTrue("expected A>B edge of value 2",
    			g.targets("A").get("B").equals(2));
    	
    	assertTrue("expected no edges from B",
    			g.targets("B").isEmpty());
    	
    }
    

    
    @Test
    public void testRemoveExistingEdge() {
    	Graph<String> g = emptyInstance();
    	g.add("A");
    	g.add("B");
    	g.set("A", "B", 1);
    	assertEquals("expected return of previous edge weight",
    			1, g.set("A", "B", 0));
    	
    	// Expect no edges in graph
    	assertTrue("expected no edges from A",
    			g.targets("A").isEmpty());
    	assertTrue("expected no edges from B",
    			g.targets("B").isEmpty());
    }
    
    @Test
    public void testNewEdgeSetToZero() {
    	Graph<String> g = emptyInstance();
    	g.add("A");
    	g.add("B");
    	assertEquals("expected 0 return from setting new edge to 0",
    			0, g.set("A", "B", 0));
    	
    	// Expect no edges in graph
    	
    }
    
    /*
     * Tests of remove() method
     */
    
    @Test
    public void testRemoveNonExistentVertex() {
    	Graph<String> g = emptyInstance();
    	assertFalse("expected false return from removing non-existent vertex",
    			g.remove("v1"));
    	assertEquals("expected empty graph",
                Collections.emptySet(), emptyInstance().vertices());
    }
    
    @Test
    public void testRemoveOnlyVertex() {
    	Graph<String> g = emptyInstance();
    	g.add("v1");
    	assertTrue("expected true return from removing existing vertex", 
    			g.remove("v1"));
    	assertEquals("expected empty graph",
                Collections.emptySet(), emptyInstance().vertices());
    }
    
    @Test 
    public void testRemoveAfromA_B_A() {
    	Graph<String> g = emptyInstance();
    	g.add("A");
    	g.add("B");
    	g.set("A", "B", 1);
    	g.set("B", "A", 1);
    	
    	System.out.println(g);
    	
    	// Check sources and targets
    	assertTrue("expected edge from A to B", 
    			g.targets("A").keySet().contains("B"));
    	assertEquals("expected exactly 1 edge from A", 
    			1, g.targets("A").keySet().size());
    	assertTrue("expected edge from B to A",
    			g.targets("B").keySet().contains("A"));
    	assertEquals("expected exactly 1 edge from B",
    			1, g.targets("B").keySet().size());

    	g.remove("A");
    	assertTrue("expected no edges in graph", 
    			g.sources("B").isEmpty());
    	assertTrue("expected no edges in graph", 
    			g.targets("B").isEmpty());
    }
    
    @Test 
    public void testRemoveAfromBCD_A() {
    	Graph<String> g = emptyInstance();
    	g.add("A");
    	g.add("B");
    	g.add("C");
    	g.add("D");
    	g.set("B", "A", 1);
    	g.set("C", "A", 1);
    	g.set("D", "A", 1);
    	
    	assertTrue("expected edges from B, C, D to A",
    			g.sources("A").keySet()
    				.containsAll(Arrays.asList("B", "C", "D")));
    	g.remove("A");
    	
    	assertTrue("expected no edges in graph", 
    			g.targets("B").isEmpty());
    	assertTrue("expected no edges in graph", 
    			g.targets("C").isEmpty());
    	assertTrue("expected no edges in graph", 
    			g.targets("D").isEmpty());
    }
    
    @Test
    public void testRemoveAfromA_BCD() {
    	Graph<String> g = emptyInstance();
    	g.add("A");
    	g.add("B");
    	g.add("C");
    	g.add("D");
    	g.set("A", "B", 1);
    	g.set("A", "C", 1);
    	g.set("A", "D", 1);
    	
    	assertTrue("expected edges from A to B,C,D",
    			g.targets("A").keySet()
    				.containsAll(Arrays.asList("B", "C", "D")));
    }
    
    @Test 
    public void testRemoveBfromA_B_C_A() {
    	Graph<String> g = emptyInstance();
    	g.add("A");
    	g.add("B");
    	g.add("C");
    	g.set("A", "B", 1);
    	g.set("B", "C", 1);
    	g.set("C", "A", 1);
    	
    	g.remove("C");
    	
    	// Should now have only A > B
    	assertTrue("expected edge from A to B", 
    			g.targets("A").keySet().contains("B"));
    	assertTrue("expected exactly 1 edge from A", 
    			g.targets("A").keySet().size() == 1);
    }
}
