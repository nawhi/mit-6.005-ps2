/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package poet;

import static org.junit.Assert.*;
import java.io.File;
import java.io.IOException;

import org.junit.Test;

/**
 * Tests for GraphPoet.
 */
public class GraphPoetTest {
    
    public final String pangram = "The quick brown fox jumped over the lazy dog";
	/*
     * Partitions for constructor:
     * Throw IOException if it can't read the corpus file
     * 
     */
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    /*
     * Should throw IOException if it can't read
     */
    
    @SuppressWarnings("unused")
	@Test(expected=IOException.class)
    public void testGraphPoetNonexistentCorpus() throws IOException {
    	File f = new File("does/not/exist");
    	GraphPoet gp = new GraphPoet(f);
    }
    
    @SuppressWarnings("unused")
	@Test(expected=IOException.class)
    public void testGraphPoetUnreadableCorpus() throws Exception {
    	File f = new File("test/poet/dir");
    	GraphPoet gp = new GraphPoet(f);
    }
    
    /*
     * Covers:
     * checks case insensitivity
     * checks empty graph and graph with no connections
     */
    @Test
    public void testGraphPoetNoPaths() throws Exception {
    	
    	// Empty input
    	GraphPoet gp = new GraphPoet(new File("test/poet/empty.txt"));
    	assertEquals("Empty corpus should leave input unchanged",
    			pangram, gp.poem(pangram));
    	
    	// Input no edges
    	GraphPoet gp2 = new GraphPoet(new File("test/poet/case_insensitivity.txt"));
    	assertEquals("Corpus with only case variations of 1 word should leave input unchanged",
    			pangram, gp2.poem(pangram));
    	
    	// Input with edges that can't be inserted into poem
    	GraphPoet gp3 = new GraphPoet(new File("test/poet/antipangram.txt"));
    	assertEquals("Corpus with no relevant edges should leave input unchanged",
    			pangram, gp3.poem(pangram));
    	
    }
    
    /*
     * Covers:
     * checks extra word is correctly inserted according to the affinity graph
     * when there is exactly one candidate 
     */
    public void testGraphPoetOnePathPerEdge() throws Exception {
    	GraphPoet gp = new GraphPoet(new File("test/poet/one_path.txt"));
    	String expected = "The quick slow brown fox jumped over the lazy dog";
    	assertEquals("Should insert correct word", 
    			expected.toLowerCase(), gp.poem(pangram).toLowerCase());
    	
    	GraphPoet gp2 = new GraphPoet(new File("test/poet/three_paths.txt"));
    	String expected2 = "The quick slow brown white fox jumped over the lazy energetic dog";
    	assertEquals("Should insert correct words",
    			expected2.toLowerCase(), gp2.poem(pangram).toLowerCase());
    }
    
    /*
     * checks the correct extra word is inserted according to the path
     * on the affinity graph with the heaviest weighting, when there is
     * more than one option 
     */
    public void testGraphPoetMultiplePaths() throws Exception {
    	GraphPoet gp = new GraphPoet(new File("test/poet/multiple_paths.txt"));
    	String expected = "The quick esoteric fox jumped over the lazy dog";
    	assertEquals("Should insert word with highest weighting",
    			expected.toLowerCase(), gp.poem(pangram).toLowerCase());
    }
    
}
