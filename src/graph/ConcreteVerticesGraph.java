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
    //   AF(r) = an ordered pair (V, E)
    //		where V = { all v in r.vertices }
    //      and E = { (v, v') for all (v,v') pair in r.vertices where v.hasEdgeTo(v') }
    //		and there exists a W such that W(v, v') = v.getEdgeTo(v') for all (v, v') in E.
    // Representation invariant:
    //   - Any graph with v vertices will have at most v(v-1) edges
    // Safety from rep exposure:
    //   - mutable Vertex class is never exposed in public methods
    //   - non-primitive types aren't returned by reference where
    //     callers could mutate the rep
    
    // Default constructor will be fine here
    
    private boolean checkRep() {
    	int nEdges = 0;
    	for(Vertex<L> v: vertices)
    		nEdges += v.getOutwardEdges().size();
    	return nEdges <= vertices.size() * (vertices.size() - 1);
    }
    
    private Vertex<L> getVertexByName(L name) {
    	assert checkRep();
    	for (Vertex<L> v: vertices) {
    		if (v.getName().equals(name))
    			return v;
    	}
    	
    	return null;
    }
    
    private Vertex<L> ensureVertexByName(L name) {
    	assert checkRep();
    	Vertex<L> v = getVertexByName(name);
    	if (v == null) v = createVertex(name);
    	return v;
    }
    
    private Vertex<L> createVertex(L name) {
    	Vertex<L> v = new Vertex<L>(name);
    	vertices.add(v);
    	return v;
    }
    
    @Override public boolean add(L vertex) {
    	assert checkRep();
    	if (getVertexByName(vertex) != null)
        	return false;
    	vertices.add(new Vertex<L>(vertex));
    	return true;
    }
    
    @Override public int set(L source, L target, int weight) {
    	assert checkRep();
    	Vertex<L> s = ensureVertexByName(source);
        Vertex<L> t = ensureVertexByName(target);
        
        return s.setEdgeTo(t, weight);
    }
    
    @Override public void set(L source, L target) {
    	assert checkRep();
    	Vertex<L> s = ensureVertexByName(source);
    	Vertex<L> t = ensureVertexByName(target);
    	
    	s.setEdgeTo(t, s.getEdgeTo(t) + 1);
    }
    
    @Override public boolean remove(L vertex) {
    	assert checkRep();
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
    	assert checkRep();
    	Set<L> s = new HashSet<>();
    	for (Vertex<L> v: vertices) {
    		s.add(v.getName());
    	}
    	return s;
    }
    
    @Override public Map<L, Integer> sources(L target) {
    	assert checkRep();
    	Map<L, Integer> sources = new HashMap<>();
    	for (Vertex<L> v: vertices) {
    		Vertex<L> t = getVertexByName(target);
    		assert t != null;
    		int edgeValue = v.getEdgeTo(getVertexByName(target));
    		if (edgeValue != 0)
    			sources.put(v.getName(), edgeValue);
    	}
    	return sources;
    }
    
    @Override public Map<L, Integer> targets(L source) {
    	assert checkRep();
    	Map<L, Integer> targets = new HashMap<>();
        Vertex<L> v = getVertexByName(source);
        assert v != null;
        for (Map.Entry<Vertex<L>, Integer> e: v.getOutwardEdges().entrySet())
        	targets.put(e.getKey().getName(), e.getValue());
        return targets;
    }
    
    @Override public String toString() {
    	assert checkRep();
    	StringBuilder sb = new StringBuilder();
    	sb.append(String.format("%s@{", getClass().getName()));
    	for (Vertex<L> v: vertices)
    		sb.append(v.toString()).append(", ");
    	return sb.append("}").toString();
    }
    
}

/**
 * Class representing a vertex in a concrete vertices graph.
 * Mutable.
 * This class is internal to the rep of ConcreteVerticesGraph.
 * 
 * Internally, it stores a map of the vertices to which it has
 * edges (and the weight of that edge). 
 * Vertex doesn't know about any edges originating from other 
 * vertices, even if they point to it.
 */
class Vertex<L> {
    
    
	// Refers to edges leading away from this vertex ONLY.
	private Map<Vertex<L>, Integer> edges;
	
	// The name of the vertex (can't be changed once assigned) 
	private final L name;
    
    // Abstraction function:
    //   AF(r) = a vertex, v, such that
	//           v = r.edge_source
	//           v.edges = { r.edge_target: r.edge_weight } 
	//           
    // Representation invariant:
	//	 No edge should ever point to a null Vertex - i.e. the
	//   'edges' map should never contain dangling references
	//
    // Safety from rep exposure:
    //   Always returns a copy of the edges map
    
	public Vertex(L name) {
    	this.name = name;
		this.edges = new HashMap<Vertex<L>, Integer>();
	}
    
	private boolean checkRep() {
		for (Vertex<L> v: edges.keySet())
			if (v.equals(null))
				return false;
		return true;
	}
	
	
	public L getName() {
		assert checkRep();
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
		assert checkRep();
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
		assert checkRep();
    	if (hasEdgeTo(target))
			return edges.get(target);
		return 0;
	}
	
	public Map<Vertex<L>, Integer> getOutwardEdges() {
		assert checkRep();
    	return new HashMap<Vertex<L>, Integer>(edges);
	}
	
	public List<Vertex<L>> getTargets() {
		return new ArrayList<>(edges.keySet());
	}
	
	public boolean hasOutwardEdges() {
		assert checkRep();
    	return edges.size() != 0;
	}
	
	public boolean hasEdgeTo(Vertex<L> target) {
		return edges.containsKey(target);
	}
    
    public String toString() {
    	assert checkRep();
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
 