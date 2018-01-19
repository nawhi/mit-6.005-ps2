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
public class ConcreteVerticesGraph<L> implements Graph<L> {
    
    private final List<Vertex<L>> vertices = new ArrayList<>();
    
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
    
    private Vertex<L> getVertexByName(L name) {
    	for (Vertex<L> v: vertices) {
    		if (v.getName().equals(name))
    			return v;
    	}
    	return null;
    }
    
    private Vertex<L> createVertex(L name) {
    	Vertex<L> v = new Vertex<L>(name);
    	vertices.add(v);
    	return v;
    }
    
    @Override public boolean add(L vertex) {
        if (getVertexByName(vertex) != null)
        	return false;
    	vertices.add(new Vertex<L>(vertex));
    	return true;
    }
    
    @Override public int set(L source, L target, int weight) {
        Vertex<L> s = getVertexByName(source);
        if (s == null)
        	s = createVertex(source);
        
        Vertex<L> t = getVertexByName(target);
        if (t == null) 
        	t = createVertex(target);
        
        return s.setEdgeTo(t, weight);
    }
    
    @Override public boolean remove(L vertex) {
        Vertex<L> v = getVertexByName(vertex);
        if (v == null)
        	return false;
        vertices.remove(v);
        
        // Get rid of any edges pointing to v
        for (Vertex<L> source: vertices)
        	source.setEdgeTo(v, 0);
        
        return true;
    }
    
    @Override public Set<L> vertices() {
        Set<L> s = new HashSet<>();
    	for (Vertex<L> v: vertices) {
    		s.add(v.getName());
    	}
    	return s;
    }
    
    @Override public Map<L, Integer> sources(L target) {
    	Map<L, Integer> sources = new HashMap<>();
    	for (Vertex<L> v: vertices) {
    		int edgeValue = v.getEdgeTo(getVertexByName(target));
    		if (edgeValue != 0)
    			sources.put(v.getName(), edgeValue);
    	}
    	return sources;
    }
    
    @Override public Map<L, Integer> targets(L source) {
        Map<L, Integer> targets = new HashMap<>();
        Vertex<L> v = getVertexByName(source);
        for (Map.Entry<Vertex<L>, Integer> e: v.getOutwardEdges().entrySet())
        	targets.put(e.getKey().getName(), e.getValue());
        return targets;
    }
    
    // TODO toString()
    @Override public String toString() {
    	StringBuilder sb = new StringBuilder();
    	sb.append(String.format("%s@{", getClass().getName()));
    	for (Vertex<L> v: vertices)
    		sb.append(v.toString()).append(", ");
    	return sb.append("}").toString();
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
class Vertex<L> {
    
    
	// Refers to edges leading away from is vertex ONLY.
	private Map<Vertex<L>, Integer> edges;
	
	// The name of the vertex (can't be changed once assigned) 
	private final L name;
    
    // Abstraction function:
    //   TODO
    // Representation invariant:
	//	 No edge shold ever point to a null Vertex
    // Safety from rep exposure:
    //   Return a copy of everything
    
    // TODO constructor
	public Vertex(L name) {
		this.name = name;
		this.edges = new HashMap<Vertex<L>, Integer>();
	}
    
    // TODO checkRep
	private boolean checkRep() {
		for (Vertex<L> v: edges.keySet()) {
			if (v.equals(null))
				return false;
		}
		return true;
	}
	
	
	public L getName() {
		return name;
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
	public int setEdgeTo(Vertex<L> target, int weight) {
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
	public int getEdgeTo(Vertex<L> target) {
		if (hasEdgeTo(target))
			return edges.get(target);
		return 0;
	}
	
	public Map<Vertex<L>, Integer> getOutwardEdges() {
        return new HashMap<Vertex<L>, Integer>(edges);
	}
	
	public boolean hasOutwardEdges() {
		return edges.size() != 0;
	}
	
	public boolean hasEdgeTo(Vertex<L> target) {
		return edges.containsKey(target);
	}
    
    public String toString() {
    	StringBuilder sb = new StringBuilder();
    	sb.append(String.format("%s@{%s",
    		getClass().getName(), name));
    	for (Map.Entry<Vertex<L>, Integer> e: edges.entrySet())
    		sb.append(String.format(", %d->%s",
    				e.getValue(), e.getKey().getName()));
    	sb.append("}");
    	return sb.toString();
    }
    
}
 