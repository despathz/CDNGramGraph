import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import gr.demokritos.iit.jinsect.utils;
import gr.demokritos.iit.jinsect.documentModel.representations.DocumentNGramGraph;
import salvo.jesus.graph.WeightedEdge;

public class Main_v1
{
	private static final String CHARS =
			"0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyzαβγδεζηθικλμνξπστυφχψωΓΔΘΛΞΠΣΦΨΩ!@#$%&";
	
	public static void main(String[] args) throws Exception 
	{
		/*
		 * Getting the parameters from the user:
		 * First parameter:		lengthOfString	-> 		the length of the strings
		 * Second parameter: 	totalStrings 		-> 		the number of the produced strings
		 * Third parameter:	dProbabilityOfRepetition ->  probability of repetition for the current set of string
		 */
		Scanner sc = new Scanner(System.in);
		System.out.println("Please give the length of the strings: ");
		Integer lengthOfString = Integer.parseInt(sc.nextLine());
		System.out.println("Please give the number of the produced strings: ");
		Integer totalStrings = Integer.parseInt(sc.nextLine());
		System.out.println("Please give probability of repetition for the current set of string: ");
		Integer dProbabilityOfRepetition = Integer.parseInt(sc.nextLine());
		System.out.println("Do you want to load an existing data file? Type yes or no ");
		String loadfile = sc.nextLine();
		Boolean loadingfile = false;
		List <String> StringsFromFIle = new ArrayList<String>();
		
		if (loadfile.equals("yes"))
		{
			loadingfile = true;
			System.out.println("Please type the name of tha file you want to load ");
			String inputFile = "inputFiles/" + sc.nextLine();
			System.out.println("Loading file " + inputFile);
			try (BufferedReader bufferReader = new BufferedReader(new FileReader(inputFile)))
			{ 
				String line = "";
				String csvSplitChar = ",";
				while ((line =bufferReader.readLine()) != null)
				{
					String[] str = line.split(csvSplitChar);
					StringsFromFIle.add(str[0]);
				}
				StringsFromFIle.remove(0); //Removing the header
			}
			catch (FileNotFoundException e)
			{
				    System.out.println(e);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		else if (!loadfile.equals("no"))
		{
			System.out.println("An error occured: invalid answer!");
			sc.close();
			return;
		}
		
		/* 
		 * Creating the csv file 
		 * String | Probability Of Repetition | Execution Time (ns) | Solutions Found | Method Cost 
		 */
		Integer totalSolutions = 0;
		String file  = "";
		file += args[0] + "_file_" + lengthOfString + "_" + dProbabilityOfRepetition + "_" + totalStrings + ".csv"; System.out.println(file); //the name of the file 
		FileWriter writer = new FileWriter(file);
		writer.append("String");  
		writer.append(','); writer.append("Probability Of Repetition");
		writer.append(','); writer.append("ExecutionTime");
		writer.append(','); writer.append("Solutions Found");
		writer.append(','); writer.append("MethodCost");
		writer.append('\n');
		
		for (int noString = 0; noString < totalStrings; noString++)
		{
			totalSolutions = 0;
			String myString = ""; //the new string that will be generated
			
			if (loadfile.equals("yes")) //get the next string from the file
			{
				myString = StringsFromFIle.get(0);
				StringsFromFIle.remove(0);
			}
			else //create a new string 
			{
				String mainChar = getRandomStringCharacters(); //select a main character from the available characters
				for (int iCur = 0; iCur < lengthOfString; iCur++) //generating the new string 
				{
					int percent = (100 / lengthOfString) * iCur; 
					if (percent <= dProbabilityOfRepetition) //if the probability is not correct add the main character to the string
						myString+=mainChar;
					else //the probability is achieved, select randomly the rest of the characters
					{
						String newStr = "";
						while (true)
						{
							newStr = getRandomStringCharacters(); 
							if (!myString.contains(newStr)) //do not add the main character again
								break;
						}
						myString += newStr;
					}
				}
			}
			
			System.out.println("New: " +  myString);
			myString = "Hi Deppie";
			
			String stop = "";
			// Min n-gram size and max n-gram size set to 1, and the dist parameter set to 1.
			DocumentNGramGraph dngGraph = new DocumentNGramGraph(1, 1, 1); 
		
			// Create the graph
			dngGraph.setDataString(myString);
		
			/* The following command gets the first n-gram graph level (with the minimum n-gram
			size) and renders it, using the utils package, as a DOT string */
			//System.out.println(utils.graphToDot(dngGraph.getGraphLevel(0), true));	
			
			//Getting total vertices and edges from the dngGraph
			int total_vertices = dngGraph.getGraphLevel(0).getVerticesCount(); //total vertices
			int total_edges = dngGraph.getGraphLevel(0).getEdgesCount(); //total edges
		
			/*
			 * weighted_setEdges: A Hashmap that is used to keep the labels of the ends of each edge of the graph:
			 * 				<<Right End of the Edge, Left End of the Edge>, Weight of the Edge>
			 * weighted_degree: A Hashmap that contains the weighted degree of each vertex of the graph:
			 * 				HashMap<Vertex, Weighted Degree>
			 * setVertices: A Set that contains all the vertices of the graph:
			 * 				Set <Vertex> 
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
					weighted_degree.put(sTail, weighted_degree.get(sTail)+degree.intValue());
				else
					weighted_degree.put(sTail, 1);
				
			}
			Set <String> setVertices = dngGraph.getGraphLevel(0).UniqueVertices.keySet(); //set of vertices
			List <String> solutions = new ArrayList<String>();
			List <String> result = new ArrayList<String>();
			Integer methodCost = 0;
			long endSolutionTime, startTime = 0, solutionTime = 0; //timer for finding a new solution
			long startSolutionTime = System.nanoTime();
			
			 for (Map.Entry<String, String> e : setEdges.entrySet()) 
		 			System.out.print(e.getKey() + e.getValue() +  ", ");
			 for (Map.Entry<String, Integer> e : weighted_degree.entrySet()) 
				 System.out.print(e.getKey() + e.getValue() +  ", ");
			
			IProblem<String> myProblem;
			ISearchAlgorithm<String> s;
			IProblemTreeNode<String> ptnSol;
			IProblem<List<String>> myLSProblem;
			ISearchAlgorithm<List<String>> sLS;
			IProblemTreeNode<List<String>> ptnLSSol;
			String sSolution;
			
			switch (args[0]) 
			{
		            	case "ls":
				      	//new LocalSearch instance, which will be used to solve the decompression problem
					startTime = System.nanoTime();
				      	// Create the problem
				      	myLSProblem = new LocalSearchProblem(total_edges, total_vertices, setEdges, setVertices, weighted_setEdges, weighted_degree, lengthOfString); 	
			      	       // Create the Search algorithm
			      		sLS = new LocalSearchAlgorithm();
			      		ptnLSSol = sLS.getSolutionFor(myLSProblem);
				 	      methodCost = sLS.getMethodCost();
				 	      sSolution = ptnLSSol.toString() == null ? "[No solution found]"
				 	                : ptnLSSol.returnNodeProposedSolution().toString();
				 	        
				 	      System.out.println("Solution:" + sSolution);
				      	break;
		            	
				case "dfs":
			      	startTime = System.nanoTime();
			 		// Create the problem
			 	      myProblem = new DFSProblem(myString, lengthOfString);
					// Create the Search algorithm
					s = new DFSSearchAlgorithm();
	
					ptnSol = s.getSolutionFor(myProblem);
					methodCost = s.getMethodCost();
					sSolution = ptnSol == null ? "[No solution found]"
					    : ptnSol.returnNodeProposedSolution();
	
					System.out.println("Solution:" + sSolution);
					break;
					
		            	case "csp_dfs":
						startTime = System.nanoTime();
						// Create the problem
						myProblem = new CSP_DFSProblem(lengthOfString, total_edges, total_vertices, setEdges, setVertices, weighted_setEdges, weighted_degree);
						// Create the Search algorithm
						s = new DFSSearchAlgorithm();
	
						ptnSol = s.getSolutionFor(myProblem);
						methodCost = s.getMethodCost();
						sSolution = ptnSol == null ? "[No solution found]"
						    : ptnSol.returnNodeProposedSolution();
	
						System.out.println("Solution:" + sSolution);
						break;	
		            	case "csp_varOrdering_dfs":
		            		System.out.println("first heuristic");
					startTime = System.nanoTime();
					// Create the problem
					myProblem = new CSP_VarOrdering_DFSProblem(lengthOfString, total_edges, total_vertices, setEdges, setVertices, weighted_setEdges, weighted_degree);
					// Create the Search algorithm
					s = new DFSSearchAlgorithm();

					ptnSol = s.getSolutionFor(myProblem);
					methodCost = s.getMethodCost();
					sSolution = ptnSol == null ? "[No solution found]"
					    : ptnSol.returnNodeProposedSolution();

					System.out.println("Solution:" + sSolution);
					break;
		            	case "csp_possibility_dfs":
		            		System.out.println("second heuristic");
					startTime = System.nanoTime();
					// Create the problem
					myProblem = new CSP_PossibilityHeuristic_DFSProblem(lengthOfString, total_edges, total_vertices, setEdges, setVertices, weighted_setEdges, weighted_degree);
					// Create the Search algorithm
					s = new DFSSearchAlgorithm();

					ptnSol = s.getSolutionFor(myProblem);
					methodCost = s.getMethodCost();
					sSolution = ptnSol == null ? "[No solution found]"
					    : ptnSol.returnNodeProposedSolution();

					System.out.println("Solution:" + sSolution);
					break;
		            	case "bfs":
				      	startTime = System.nanoTime();
			 		// Create the problem
			 	      myProblem = new BFSProblem(myString, lengthOfString);
					// Create the Search algorithm
					s = new BFSSearchAlgorithm();
	
					ptnSol = s.getSolutionFor(myProblem);
					methodCost = s.getMethodCost();
					sSolution = ptnSol == null ? "[No solution found]"
					    : ptnSol.returnNodeProposedSolution();
	
					System.out.println("Solution:" + sSolution);
					break;
		            	case "csp_bfs":
					startTime = System.nanoTime();
					// Create the problem
					myProblem = new CSP_BFSProblem(lengthOfString, total_edges, total_vertices, setEdges, setVertices, weighted_setEdges, weighted_degree);
					// Create the Search algorithm
					s = new BFSSearchAlgorithm();

					ptnSol = s.getSolutionFor(myProblem);
					methodCost = s.getMethodCost();
					sSolution = ptnSol == null ? "[No solution found]"
					    : ptnSol.returnNodeProposedSolution();

					System.out.println("Solution:" + sSolution);
					break;
		            	case "csp_varOrdering_bfs":
		            		System.out.println("first heuristic");
					startTime = System.nanoTime();
					// Create the problem
					myProblem = new CSP_VarOrdering_BFSProblem(lengthOfString, total_edges, total_vertices, setEdges, setVertices, weighted_setEdges, weighted_degree);
					// Create the Search algorithm
					s = new BFSSearchAlgorithm();

					ptnSol = s.getSolutionFor(myProblem);
					methodCost = s.getMethodCost();
					sSolution = ptnSol == null ? "[No solution found]"
					    : ptnSol.returnNodeProposedSolution();

					System.out.println("Solution:" + sSolution);
					break;
		            	case "csp_possibility_bfs":
		            		System.out.println("second heuristic");
					startTime = System.nanoTime();
					// Create the problem
					myProblem = new CSP_PossibilityHeuristic_BFSProblem(lengthOfString, total_edges, total_vertices, setEdges, setVertices, weighted_setEdges, weighted_degree);
					// Create the Search algorithm
					s = new BFSSearchAlgorithm();

					ptnSol = s.getSolutionFor(myProblem);
					methodCost = s.getMethodCost();
					sSolution = ptnSol == null ? "[No solution found]"
					    : ptnSol.returnNodeProposedSolution();

					System.out.println("Solution:" + sSolution);
					break;
			}
			
			long endTime = System.nanoTime();
			long duration = endTime - startTime;
			
			if (!solutions.contains(result.toString()))
			{
				totalSolutions++;
				solutions.add(result.toString());
				writer.append(myString);  //add the new string and its properities to the csv file
				writer.append(','); writer.append(dProbabilityOfRepetition.toString());
				writer.append(','); writer.append(Long.toString(duration));
				writer.append(','); writer.append(totalSolutions.toString());
				writer.append(','); writer.append(methodCost.toString());
				writer.append('\n');	
			}
			else 
			{
				endSolutionTime = System.nanoTime();
				solutionTime = endSolutionTime - startSolutionTime;
//					if (solutionTime < 1000000000L) //1 second
//						continue;
//					else //Could not find a new solution
//						break;
			}
			startSolutionTime = System.nanoTime();

			writer.flush();
		}
		sc.close();
		writer.close();
	}
	
	private static String getRandomStringCharacters() 
	{
		Random randomNum = new Random();
		StringBuilder randomChar = new StringBuilder();
		
		randomChar.append(CHARS.charAt(randomNum.nextInt(CHARS.length() - 1)));
				
		return randomChar.toString();
	}

}
