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
		//String sTmp = args[0];
		String sTmp = "mioum";
		System.out.println("Given string: " + sTmp);
		int totaln = sTmp.length();

		 // Min n-gram size and max n-gram size set to 1, and the dist parameter set to 1.
		DocumentNGramGraph dngGraph = new DocumentNGramGraph(1, 1, 1); 

		// Create the graph
		dngGraph.setDataString(sTmp);

		int total_vertices = dngGraph.getGraphLevel(0).getVerticesCount(); //total vertices
		int total_edges = dngGraph.getGraphLevel(0).getEdgesCount(); //total edges

		java.util.Iterator iIter = dngGraph.getGraphLevel(0).getEdgeSet().iterator();
		Map<String, String> setEdges = new HashMap<String, String>();
		Map<String, Double> weighted_degree = new HashMap<String, Double>();
		Map<String, Integer> labelAppearance = new HashMap<String, Integer>();
		while (iIter.hasNext())
		{
			WeightedEdge weCurItem = (WeightedEdge)iIter.next();
			String sHead = weCurItem.getVertexA().getLabel();
			String sTail = weCurItem.getVertexB().getLabel();
			Double degree = weCurItem.getWeight();
			setEdges.put(sHead, sTail);
			weighted_degree.put(sHead, degree);
			if (labelAppearance.containsKey(sHead))
			{
				labelAppearance.put(sHead, labelAppearance.get(sHead)+1);
			}
			else
				labelAppearance.put(sHead, 1);
			if (labelAppearance.containsKey(sTail))
			{
				labelAppearance.put(sTail, labelAppearance.get(sTail)+1);
			}
			else
				labelAppearance.put(sTail, 1);
			
		}
		 for (Map.Entry<String, String> e : setEdges.entrySet()) 
			System.out.print(e.getKey() + e.getValue() +  ", ");
		 for (Map.Entry<String, Double> e : weighted_degree.entrySet()) 
				System.out.print(e.getKey() + e.getValue() +  " - ");
		 for (Map.Entry<String, Integer> e : labelAppearance.entrySet()) 
				System.out.print(e.getKey() + e.getValue() +  " - ");
		Set <String> setVertices = dngGraph.getGraphLevel(0).UniqueVertices.keySet(); //set of vertices
		
		LocalSearch newsearch = new LocalSearch(total_edges, total_vertices, setEdges, setVertices, weighted_degree, labelAppearance, totaln); 
		//String[] result =  new String[total_vertices];
		List <String> result = new ArrayList<String>();
		
		//long startTime = System.nanoTime();
		result = newsearch.lsAlgorithm();
		//long endTime = System.nanoTime();
		//long duration = endTime - startTime;
		
		System.out.print("Solution: ");
		for (String str: result)
			System.out.print(str);
		System.out.println();
		//System.out.println("Total execution time: " + duration);

		/* The following command gets the first n-gram graph level (with the minimum n-gram
		size) and renders it, using the utils package, as a DOT string */
		System.out.println(utils.graphToDot(dngGraph.getGraphLevel(0), true));	
        
	}
}


