
import java.util.*;

import gr.demokritos.iit.jinsect.utils;
import gr.demokritos.iit.jinsect.documentModel.representations.DocumentNGramGraph;
import salvo.jesus.graph.*;

public class Main 
{	
	
	public static void main(String[] args) throws Exception 
	{
		// The string we want to represent
		Scanner sc = new Scanner(System.in);
		String sTmp = args[1];
		String stop = "";
		System.out.println("Given string: " + sTmp);
		int totaln = sTmp.length();

		 // Min n-gram size and max n-gram size set to 1, and the dist parameter set to 1.
		DocumentNGramGraph dngGraph = new DocumentNGramGraph(1, 1, 1); 

		// Create the graph
		dngGraph.setDataString(sTmp);

		//Getting total vertices and edges from the dngGraph
		int total_vertices = dngGraph.getGraphLevel(0).getVerticesCount(); //total vertices
		int total_edges = dngGraph.getGraphLevel(0).getEdgesCount(); //total edges

		/*
		 * weighted_setEdges: A Hashmap that is used to keep the labels of the ends of each edge of the graph:
		 * 				 <<Right End of the Edge, Left End of the Edge>, Weight of the Edge>
		 * weighted_degree: A Hashmap that contains the weighted degree of each vertex of the graph:
		 * 				 HashMap<Vertex, Weighted Degree>
		 * setVertices: A Set that contains all the vertices of the graph:
		 * 			Set <Vertex> 
		 */
		Iterator iIter = dngGraph.getGraphLevel(0).getEdgeSet().iterator();
		Map<String, String> setEdges = new HashMap<String, String>();
		Map<Map<String, String>, Double> weighted_setEdges = new HashMap<Map<String, String>, Double>();
		Map<String, Integer> weighted_degree = new HashMap<String, Integer>();
		while (iIter.hasNext())
		{
			WeightedEdge weCurItem = (WeightedEdge)iIter.next(); //get weighted edge
			String sHead = weCurItem.getVertexA().getLabel(); //get the right vertex of the edge
			String sTail = weCurItem.getVertexB().getLabel(); //get the left vertex of the edge
			Double degree = weCurItem.getWeight(); //get the weight of the edge
			setEdges.put(sHead, sTail);
			Map <String, String> edg1 = new HashMap<String, String>(); //create a new edge for the weighted_setEdges 
			edg1.put(sHead, sTail); 
			weighted_setEdges.put(edg1, degree); //put the new edge in the weighted_setEdges hashmap
			if (weighted_degree.containsKey(sHead)) //update the weighted degree of the right vertex of the edge
				weighted_degree.put(sHead, weighted_degree.get(sHead)+1);
			else
				weighted_degree.put(sHead, 1);
			if (weighted_degree.containsKey(sTail)) //update the weighted degree of the left vertex of the edge
				weighted_degree.put(sTail, weighted_degree.get(sTail)+1);
			else
				weighted_degree.put(sTail, 1);
			
		}
		Set <String> setVertices = dngGraph.getGraphLevel(0).UniqueVertices.keySet(); //set of vertices
		List <String> solutions = new ArrayList<String>();
		List <String> result = new ArrayList<String>();
		long endSolutionTime, startTime = 0, solutionTime = 0; //timer for finding a new solution
		long startSolutionTime = System.nanoTime();
		while (!stop.equals("quit"))
		{
			switch (args[0]) 
			{
		            	case "ls":
					//new LocalSearch instance, which will be used to solve the decompression problem
					LocalSearch newsearchLs = new LocalSearch(total_edges, total_vertices, setEdges, setVertices, weighted_setEdges, weighted_degree, totaln); 
					
					//Solve and time the CSP
					startTime = System.nanoTime();
					result = newsearchLs.lsAlgorithm();
					break;
				
		            	case "dfs":
		            		TreeNode<String> root = new TreeNode<String>("root");
		            		root = root.TreeCreate(2, root);
		            		
					//Solve and time the CSP
					startTime = System.nanoTime();
					//result = newsearchDfs.lsAlgorithm();
		            		break;
			}
			long endTime = System.nanoTime();
			long duration = endTime - startTime;
			
			if (!solutions.contains(result.toString()))
				solutions.add(result.toString());
			else 
			{
				endSolutionTime = System.nanoTime();
				solutionTime = endSolutionTime - startSolutionTime;
				if (solutionTime < 5000000000L) //5 seconds
					continue;
				else
				{
					System.out.println("Could not find a new solution.");
					break;
				}
			}
			
			//Print the solution and the total execution time
			System.out.print("Solution: ");
			for (String str: result)
				System.out.print(str);
			System.out.println("\nTotal execution time: " + duration + " ns");
			
			/*
			 * Evaluating the algorithm that was used
			 */
			System.out.print("Press y if you want to evaluate the algorithm or n if you do not: ");
			String yes = "y", desicion = sc.nextLine();
			if (desicion.compareTo(yes) == 0)
			{
				/*Evaluate the algorithm*/
				switch (args[0]) 
				{
			            	case "ls":
						//new LocalSearch instance, which will be used to solve the decompression problem
						EvaluatedLocalSearch newsearchLs = new EvaluatedLocalSearch(total_edges, total_vertices, setEdges, setVertices, weighted_setEdges, weighted_degree, totaln); 
						
						//Solve and time the CSP
						result = newsearchLs.lsAlgorithm();
						int methodCost = newsearchLs.getMethodCost();
						System.out.println("MethodCost is: " + methodCost);
						break;
					
			            	case "dfs":
//			            		
				}
			}
			else
				System.out.println("Terminating...");			
			
			System.out.println("Please press \"enter\" for the next solution, otherwise type \"quit\".");
			stop = sc.nextLine();
			startSolutionTime = System.nanoTime();
		}
		sc.close();
		/* The following command gets the first n-gram graph level (with the minimum n-gram
		size) and renders it, using the utils package, as a DOT string */
		System.out.println(utils.graphToDot(dngGraph.getGraphLevel(0), true));	
	}
}


