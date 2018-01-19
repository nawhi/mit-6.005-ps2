/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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
    
    private Vertex getVertexByName(String name) {
    	for (Vertex v: vertices) {
    		if (v.getName().equals(name))
    			return v;
    	}
    	return null;
    }
    
    private Vertex createVertex(String name) {
    	Vertex v = new Vertex(name);
    	vertices.add(v);
    	return v;
    }
    
    @Override public boolean add(String vertex) {
        if (getVertexByName(vertex) != null)
        	return false;
    	vertices.add(new Vertex(vertex));
    	return true;
    }
    
    @Override public int set(String source, String target, int weight) {
        Vertex s = getVertexByName(source);
        if (s == null)
        	s = createVertex(source);
        
        Vertex t = getVertexByName(target);
        if (t == null) 
        	t = createVertex(target);
        
        return s.setEdgeTo(t, weight);
    }
    
    @Override public boolean remove(String vertex) {
        Vertex v = getVertexByName(vertex);
        if (v == null)
        	return false;
        vertices.remove(v);
        return true;
    }
    
    @Override public Set<String> vertices() {
        Set<String> s = new HashSet<>();
    	for (Vertex v: vertices) {
    		s.add(v.getName());
    	}
    	return s;
    }
    
    @Override public Map<String, Integer> sources(String target) {
    	Map<String, Integer> sources = new HashMap<>();
    	for (Vertex v: vertices) {
    		int edgeValue = v.getEdgeTo(getVertexByName(target));
    		if (edgeValue != 0)
    			sources.put(v.getName(), edgeValue);
    	}
    	return sources;
    }
    
    @Override public Map<String, Integer> targets(String source) {
        Map<String, Integer> targets = new HashMap<>();
        Vertex v = getVertexByName(source);
        for (Map.Entry<Vertex, Integer> e: v.getOutwardEdges().entrySet())
        	targets.put(e.getKey().getName(), e.getValue());
        return targets;
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
	
	/**
	 * Get the value of the edge to a particular other vertex,
	 * if it exists.
	 * 
	 * @param target The vertex to get the edge to
	 * @return the value of the edge, or zero if there is no edge.
	 */
	public int getEdgeTo(Vertex target) {
		if (hasEdgeTo(target))
			return edges.get(target);
		return 0;
	}
	
	public Map<Vertex, Integer> getOutwardEdges() {
        return new HashMap<Vertex, Integer>(edges);
	}
	
	public boolean hasOutwardEdges() {
		return edges.size() != 0;
	}
	
	public boolean hasEdgeTo(Vertex target) {
		return edges.containsKey(target);
	}
    
    public String toString() {
    	StringBuilder sb = new StringBuilder();
    	sb.append(String.format("%s@{%s",
    		getClass().getName(), name));
    	for (Map.Entry<Vertex, Integer> e: edges.entrySet())
    		sb.append(String.format(", %d->%s",
    				e.getValue(), e.getKey().getName()));
    	sb.append("}");
    	return sb.toString();
    }
    
}
 