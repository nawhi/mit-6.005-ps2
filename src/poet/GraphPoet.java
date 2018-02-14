/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package poet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import graph.Graph;

/**
 * A graph-based poetry generator.
 * 
 * <p>GraphPoet is initialized with a corpus of text, which it uses to derive a
 * word affinity graph.
 * Vertices in the graph are words. Words are defined as non-empty
 * case-insensitive strings of non-space non-newline characters. They are
 * delimited in the corpus by spaces, newlines, or the ends of the file.
 * Edges in the graph count adjacencies: the number of times "w1" is followed by
 * "w2" in the corpus is the weight of the edge from w1 to w2.
 * 
 * <p>For example, given this corpus:
 * <pre>    Hello, HELLO, hello, goodbye!    </pre>
 * <p>the graph would contain two edges:
 * <ul><li> ("hello,") -> ("hello,")   with weight 2
 *     <li> ("hello,") -> ("goodbye!") with weight 1 </ul>
 * <p>where the vertices represent case-insensitive {@code "hello,"} and
 * {@code "goodbye!"}.
 * 
 * <p>Given an input string, GraphPoet generates a poem by attempting to
 * insert a bridge word between every adjacent pair of words in the input.
 * The bridge word between input words "w1" and "w2" will be some "b" such that
 * w1 -> b -> w2 is a two-edge-long path with maximum-weight weight among all
 * the two-edge-long paths from w1 to w2 in the affinity graph.
 * If there are no such paths, no bridge word is inserted.
 * In the output poem, input words retain their original case, while bridge
 * words are lower case. The whitespace between every word in the poem is a
 * single space.
 * 
 * <p>For example, given this corpus:
 * <pre>    This is a test of the Mugar Omni Theater sound system.    </pre>
 * <p>on this input:
 * <pre>    Test the system.    </pre>
 * <p>the output poem would be:
 * <pre>    Test of the system.    </pre>
 * 
 * <p>PS2 instructions: this is a required ADT class, and you MUST NOT weaken
 * the required specifications. However, you MAY strengthen the specifications
 * and you MAY add additional methods.
 * You MUST use Graph in your rep, but otherwise the implementation of this
 * class is up to you.
 */
public class GraphPoet {
    
    private final Graph<String> graph = Graph.empty();
    
    // Abstraction function:
    //   TODO
    // Representation invariant:
    //   TODO
    // Safety from rep exposure:
    //   TODO
    
    /**
     * Create a new poet with the graph from corpus (as described above).
     * 
     * @param corpus text file from which to derive the poet's affinity graph
     * @throws IOException if the corpus file cannot be found or read
     */
    public GraphPoet(File corpus) throws IOException {
    	String contents = getContents(corpus);
    	generateGraph(Arrays.asList(contents.toLowerCase().split("\\s+")));
    	
    }
    
    /**
     * Read the contents of a given text file into a string.
     * @param f The file to open and read.
     * @return The string contents of the file.
     */
    private String getContents(File f) throws IOException {
    	BufferedReader reader = new BufferedReader(new FileReader(f));
    	StringBuilder sb = new StringBuilder();
    	String line;
    	while ((line = reader.readLine()) != null)
    		sb.append(line);
    	reader.close();
    	return sb.toString();
    }
    
    /**
     * Generate the word affinity graph for the supplied list of words and
     * assign it to the internal member graph.
     * @param wordList a list of lower-case words (including punctuation)
     * 			from which to form the graph
     */
    private void generateGraph(List<String> wordList) {
    	for (int i=0; i<wordList.size()-2; i++) {
    		String thisWord = wordList.get(i);
    		String nextWord = wordList.get(i+1);
    		graph.add(thisWord);
    		graph.add(nextWord);
    		graph.set(thisWord, nextWord);
    	}
    }
    
    /**
     * Generate a poem.
     * 
     * @param input string from which to create the poem
     * @return poem (as described above)
     */
    public String poem(String input) {
        List<String> words = Arrays.asList(input.split("\\s+"));
        /*
         * Doesn't yet handle choosing the weightiest path
         * if there is more than one available... this is TODO
         */
        for (int i=words.size()-2; i<=0; i--) {
        	String thisWord = words.get(i);
        	String nextWord = words.get(i+1);
        	for (String bridge: graph.targets(thisWord).keySet()) {
        		for (String target: graph.targets(bridge).keySet()) {
        			if (target.equals(nextWord)) {
        				words.add(i+1, bridge);
        			}
        		}
        	}
        }
        StringBuilder sb = new StringBuilder();
        for (String w: words) 
        	sb.append(w).append(" ");
        return sb.toString().trim();
    }
    
    // TODO toString()
    
}
