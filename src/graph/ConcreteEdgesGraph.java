/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.lang.StringBuilder;
/**
 * An implementation of Graph.
 * 
 * <p>PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteEdgesGraph<L> implements Graph<L> {
    
    private final Set<L> vertices = new HashSet<>();
    private final List<Edge<L>> edges = new ArrayList<>();
    
    // Abstraction function:
    //   AF(r) = an ordered pair (V,E)
    // 		where V = { all v in r.vertices }
    //		and E = { (e.getSource(), e.getTarget()) for e in edges }
    //		and there exists a W such that W(e) = e.getWeight() for all e in E.   

    // Representation invariant:
    //  - Any graph with v vertices will have at most v(v-1) edges
    //  - for all e in edges, vertices contains e.source and e.target
    // Safety from rep exposure:
    //  - set() puts all params into immutable Edge instances
    //  - vertices(), sources(), targets() return copies
    //  - 
    
    // Default constructor will be fine here
    
    /**
     * Looks for an edge in the graph.
     * @param source string of the desired edge's source
     * @param target string of the desired edge's target
     * @return the index of the edge, or -1 if not found
     */
    private int findEdge(L source, L target) {
    	assert checkRep();
    	for (int i = 0; i < edges.size(); ++i) {
    		Edge<L> e = edges.get(i);
    		if (e.getSource().equals(source) && e.getTarget().equals(target))
    			return i;
    	}
    	return -1;
    }
    
    /*
     * Representation invariant:
     * - any graph with v vertices will have no more than v(v-1) edges
     * - the source and target of every edge is in vertices
     */
    public boolean checkRep() {
    	boolean edgeCheck = 
    			edges.size() <= vertices.size() * (vertices.size() - 1);
    	boolean vertexCheck = true;
    	for (Edge<L> e: edges) {
    		vertexCheck = vertices.contains(e.getSource()) 
    				&& vertices.contains(e.getTarget());
    	}
    	return edgeCheck && vertexCheck;
    			
    }
    
    @Override public boolean add(L vertex) {
    	assert checkRep();
    	if (vertices.contains(vertex))
    		return false;
        vertices.add(vertex);
        return true;
    }
    
    @Override public int set(L source, L target, int weight) {
    	assert checkRep();
    	int edgeIx = findEdge(source, target);
    	if (edgeIx != -1) {
    		int oldWeight = edges.get(edgeIx).getWeight();
    		// Edge exists. Update it in-place
    		if (weight == 0)
    			edges.remove(edgeIx);
    		else
    			edges.set(edgeIx, new Edge<L>(source, target, weight));
    		return oldWeight;
    	}
    	if (weight > 0) {
    		// No such edge; create new entry & return 0
    		edges.add(new Edge<L>(source, target, weight));
    		return 0;
    	}
    	// No such edge, and weight was 0: nothing to do
    	return 0;
    }
    
    @Override public void set(L source, L target) {
    	assert checkRep();
    	int edgeIx = findEdge(source, target);
    	if (edgeIx == -1)
    		set(source, target, 1);
    	else
    		set(source, target, edges.get(edgeIx).getWeight() + 1);
    	
    }
    
    @Override public boolean remove(L vertex) {
    	assert checkRep();
    	if (!vertices.contains(vertex))
    		return false;
    	vertices.remove(vertex);
    	// Backwards iteration avoids skipping elements
    	// when we delete one
    	for (int i = edges.size() - 1; i >= 0; --i) {
    		Edge<L> e = edges.get(i);
    		if (e.getSource().equals(vertex) || e.getTarget().equals(vertex))
    			edges.remove(i);
    	}
    	return true;
    }
    
    @Override public Set<L> vertices() {
        assert checkRep();
    	return new HashSet<>(vertices);
    }
    
    @Override public Map<L, Integer> sources(L target) {
        assert checkRep();
    	Map<L, Integer> sources = new HashMap<>();
        edges.forEach(e -> {
        	if (e.getTarget().equals(target)) 
        		sources.put(e.getSource(), e.getWeight());
        });
        return sources;
    }
    
    @Override public Map<L, Integer> targets(L source) {
        assert checkRep();
    	Map<L, Integer> targets = new HashMap<>();
        edges.forEach(e -> {
        	if (e.getSource().equals(source))
        		targets.put(e.getTarget(), e.getWeight());
        });
        return targets;
    }
    
    @Override public String toString() {
    	assert checkRep();
    	StringBuilder sb = new StringBuilder("ConcreteEdgesGraph:");
    	sb.append("vertices={");
    	vertices.forEach((v) -> sb.append(v + ","));
    	sb.append("} edges={");
    	edges.forEach((e) -> sb.append(e + ","));
    	return sb.append("}").toString();
    }
    
}

/**
 * Immutable data class internal to the rep of ConcreteEdgesGraph which
 * describes an edge within the graph. 
 */
class Edge<L> {
    
	private final L source;
	private final L target;
	private final int weight;
    
    /*
     * Abstraction function: 
     *  - AF(r) = an edge, e, such that 
     *  	e.source = r.edge_source
     *  	e.target = r.edge_target
     *  	e.weight = r.edge_weight
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
    
	public Edge(L source, L target, int weight) throws IllegalArgumentException {
		if (weight <= 0)
			throw new IllegalArgumentException("Requires weight > 0");
		if (source.equals(target))
			throw new IllegalArgumentException("Requires source != target");
		this.source = source;
		this.target = target;
		this.weight = weight;
	}
	
	public L getSource() { 
		return source; 
	}
	
	public L getTarget() { 
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
