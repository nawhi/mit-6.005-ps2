/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * An implementation of Graph.
 * 
 * <p>PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteVerticesGraph implements Graph<String> {
    
    private final List<Vertex> vertices = new ArrayList<>();
    
    // Abstraction function:
    //   TODO
    // Representation invariant:
    //   TODO
    // Safety from rep exposure:
    //   TODO
    
    // TODO constructor
    
    // TODO checkRep
    // This needs to check for dangling edges.
    // i.e. that for some vertex V, every t in V.targets
    // must be another vertex which has V listed in its
    // sources with the same weight.
    
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
    @Override public String toString() {
    	throw new RuntimeException("not implemented");
    }
    
}

/**
 * TODO specification
 * Mutable.
 * This class is internal to the rep of ConcreteVerticesGraph.
 * 
 * <p>PS2 instructions: the specification and implementation of this class is
 * up to you.
 */
class Vertex {
    
    
	// Refers to edges leading away from is vertex ONLY.
	private Map<Vertex, Integer> edges;
	
	// The name of the vertex (can't be changed once assigned) 
	private final String name;
    
    // Abstraction function:
    //   TODO
    // Representation invariant:
	//	 No edge shold ever point to a null Vertex
    // Safety from rep exposure:
    //   Return a copy of everything
    
    // TODO constructor
	public Vertex(String name) {
		this.name = name;
		this.edges = new HashMap<Vertex, Integer>();
	}
    
    // TODO checkRep
	private boolean checkRep() {
		for (Vertex v: edges.keySet()) {
			if (v.equals(null))
				return false;
		}
		return true;
	}
	
	
	public String getName() {
		return new String(name);
	}
    
    /**
     * Add, change or remove a weighted directed edge
     * from this vertex to another. 
     * If weight is nonzero, add an edge or update the
     * weight of that edge.
     * If weight is zero, remove the edge if it exists.
     * 
     * @param target  The Vertex to set the edge to.
     * @param weight  nonnegative weight of the edge
     * @return the previous weight of the edge, or 0 if there was
     * 			no such edge
     */
	public int setEdgeTo(Vertex target, int weight) {
		if (edges.containsKey(target)) {
			if (weight == 0)
				return edges.remove(target);
			else
				return edges.put(target, weight);
		}
		// new target
		if (weight != 0) {
			edges.put(target, weight);
		}
		return 0;
	}
	
	public Map<Vertex, Integer> getOutwardEdges() {
        return new HashMap<Vertex, Integer>(edges);
	}
    
    // TODO toString()
    
}
 