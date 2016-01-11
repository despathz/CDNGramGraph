import java.util.*;

import gr.demokritos.iit.jinsect.utils;
import gr.demokritos.iit.jinsect.documentModel.representations.DocumentNGramGraph;
import gr.demokritos.iit.jinsect.structs.UniqueVertexGraph;
import salvo.jesus.graph.*;

public class Main 
{
	public static void main(String[] args) throws Exception 
	{
		// The string we want to represent
		String sTmp = "abcde";
//		String sTmp = "abcdefgh";
		System.out.println("Given string: " + sTmp);

		 // Min n-gram size and max n-gram size set to 1, and the dist parameter set to 1.
		DocumentNGramGraph dngGraph = new DocumentNGramGraph(1, 1, 1); 

		// Create the graph
		dngGraph.setDataString(sTmp);

		int total_vertices = dngGraph.getGraphLevel(0).UniqueVertices.values().size(); //total vertices
		int total_edges = dngGraph.length(); //total edges

		java.util.Iterator iIter = dngGraph.getGraphLevel(0).getEdgeSet().iterator();
		Map<String, String> setEdges = new HashMap<String, String>();
		while (iIter.hasNext())
		{
			WeightedEdge weCurItem = (WeightedEdge)iIter.next();
			String sHead = weCurItem.getVertexA().getLabel();
			String sTail = weCurItem.getVertexB().getLabel();
			setEdges.put(sHead, sTail);
		}
		Set <String> setVertices = dngGraph.getGraphLevel(0).UniqueVertices.keySet(); //set of vertices
		
		LocalSearch newsearch = new LocalSearch(total_edges, total_vertices, setEdges, setVertices); 
		String[] result =  new String[total_vertices];
		
		//long startTime = System.nanoTime();
		result = newsearch.lsAlgorithm();
		//long endTime = System.nanoTime();
		//long duration = endTime - startTime;
		
		System.out.print("Solution: ");
		for (int i = 0; i < total_vertices; i++)
			System.out.print(result[i]);
		System.out.println();
		//System.out.println("Total execution time: " + duration);

		/* The following command gets the first n-gram graph level (with the minimum n-gram
		size) and renders it, using the utils package, as a DOT string */
		System.out.println(utils.graphToDot(dngGraph.getGraphLevel(0), true));	
        
	}
}


