/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * An implementation of Graph.
 * 
 * <p>PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteEdgesGraph implements Graph<String> {
    
    private final Set<String> vertices = new HashSet<>();
    private final List<Edge> edges = new ArrayList<>();
    
    // Abstraction function:
    //   TODO
    // Representation invariant:
    //   TODO
    // Safety from rep exposure:
    //   TODO
    
    // TODO constructor
    
    // TODO checkRep
    
    @Override public boolean add(String vertex) {
        throw new RuntimeException("not implemented");
    }
    
    @Override public int set(String source, String target, int weight) {
        throw new RuntimeException("not implemented");
    }
    
    @Override public boolean remove(String vertex) {
        throw new RuntimeException("not implemented");
    }
    
    @Override public Set<String> vertices() {
        throw new RuntimeException("not implemented");
    }
    
    @Override public Map<String, Integer> sources(String target) {
        throw new RuntimeException("not implemented");
    }
    
    @Override public Map<String, Integer> targets(String source) {
        throw new RuntimeException("not implemented");
    }
    
    // TODO toString()
    
}

/**
 * Immutable data class internal to the rep of ConcreteEdgesGraph which
 * describes an edge within the graph. 
 */
class Edge {
    
	private final String source;
	private final String target;
	private final int weight;
    
    /*
     * Abstraction function: 
     * 	- source = vertex the edge is coming from
     *  - target = vertex the edge is going to
     *  - weight = weight of the edge
     * 
     * Representation invariant:
     *  - weight > 0
     *  - source != target
     * 
     * Safety from rep exposure:
     *  - none needed apparently, see 
     *  	testEdgeImmutability(). I guess because
     *  	String and int are themselves immutable
     */
    
	public Edge(String source, String target, int weight) throws IllegalArgumentException {
		if (weight <= 0)
			throw new IllegalArgumentException("Requires weight > 0");
		if (source.equals(target))
			throw new IllegalArgumentException("Requires source != target");
		this.source = source;
		this.target = target;
		this.weight = weight;
	}
	
	public String getSource() { 
		return source; 
	}
	
	public String getTarget() { 
		return target;
	}
	
	public int getWeight() { 
		return weight;
	}
	
	public boolean checkRep() {
		return weight > 0 && !source.equals(target);
	}
    
	public String toString() {
		// "Edge source='A' target='B' weight=1
		return "Edge source='" + source +
				"' target='" + target +
				"' weight=" + weight;
	}
    
}
