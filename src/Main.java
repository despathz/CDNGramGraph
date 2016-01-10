import java.util.*;

import gr.demokritos.iit.jinsect.utils;
import gr.demokritos.iit.jinsect.documentModel.representations.DocumentNGramGraph;
import gr.demokritos.iit.jinsect.structs.UniqueVertexGraph;

public class Main 
{
    public static void main(String[] args) throws Exception 
    {
    	// The string we want to represent
	   	String sTmp = "abcd";
	
	   	 // Min n-gram size and max n-gram size set to 1, and the dist parameter set to 1.
	   	DocumentNGramGraph dngGraph = new DocumentNGramGraph(1, 1, 1); 
	
	   	// Create the graph
	   	dngGraph.setDataString(sTmp);
	   	
	   	int total_vertices = dngGraph.getGraphLevel(0).UniqueVertices.values().size(); //total vertices
	    System.out.println(total_vertices);	    
	    int total_edges = dngGraph.length(); //total edges
	    System.out.println(total_edges); 
	    
	    Set <String> setEdges =  dngGraph.getGraphLevel(0).getEdgeSet(); //set of edges
	    System.out.println(setEdges); 
	    
	    Set <String> setVetrices = dngGraph.getGraphLevel(0).UniqueVertices.keySet(); //set of vertices
	    System.out.println(setVetrices);
	    
	    /*Local Search Algorithm
	     * Input: 
	     * 		- V: the set of variables  
	     * 		- dom: the domain for each variable
	     * 		- C: set of constraints
	     * Output: 
	     * 		A complete assignment
	     * Extra:
	     * 		- arrLoc[V] will be a local array of values indexed by V
	     */
	    
//	    String[] arrLoc = new String[total_vertices]; 
//	    while (true)
//	    {
//	    	for(int i = 0; i < total_vertices; i++)
//	    	{
//	    		
//	    	}
//	    }
	    
	    
	   	/* The following command gets the first n-gram graph level (with the minimum n-gram
        size) and renders it, using the utils package, as a DOT string */
        System.out.println(utils.graphToDot(dngGraph.getGraphLevel(0), true));	
        
    }
}


