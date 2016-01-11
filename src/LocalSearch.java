import java.util.*;

public class LocalSearch
{
	int edges, vertices;
	Set <String> setEdges;
	Set <String> setVertices;
	
	public LocalSearch (int edg, int vert, Set<String> setE, Set<String> setV)  //constructor
	{ 
		edges = edg;
		vertices = vert;
		setEdges = setE;
		setVertices = setV;
	}
	
	
	/*
	 * Local Search Algorithm
	 * Input: 
	 * 		- arrVert: the set of variables  
	 * 		- setVertices: the domain for each variable
	 * 		- C: set of constraints
	 * Output: 
	 * 		A complete assignment
	 * Extra:
	 * 		- arrLoc[V] will be a local array of values indexed by V
	 */
	public String[] lsAlgorithm()
	{
		String[] arrLoc = new String[vertices];
		arrLoc = randomInitialize(); 
		while (!isSolution(arrLoc)) //the assignement is not a solution
		{
			int newVar = new Random().nextInt(vertices);  //select a variable newVar
			String newVal = randomSelect();  //select a value newVal from the domain
			arrLoc[newVar] = newVal; //change local array
			if (isSolution(arrLoc))
				break;
		}
		
		System.out.println("so?");
		return arrLoc;
	}
	
	
	/*
	 *  Initialize randomly all the variables
	 *  A String array will be returned
	 */
	public String[] randomInitialize()
	{
		String[] arrLoc = new String[vertices]; 
		Set <String> temp = setVertices;
		int total = vertices;
		
	       for(int i = 0; i < vertices; i++)
	    	{
	    		int item = new Random().nextInt(total); //pick value from the domain
	    		int j = 0;
	    		String str = " ";
	    		for(String strT : temp)
	    		{
	    		    if (j == item)
	    		    {
	    			    str = strT;
	    			    break;
	    		    }
	    		    j = j + 1;
	    		}
	    		arrLoc[i] = str;
	    		total--;
	    		temp.remove(str);
	    	}
	       
//	       for (int i = 0; i < vertices; i++) //debugging
//	       	System.out.print(arrLoc[i]);
//	       System.out.println();
	       
	       return arrLoc;
	}
	
	
	/*
	 * Randomly select a value from the domain
	 */
	public String randomSelect()
	{
		int item = new Random().nextInt(vertices);  //select a variable
		int j = 0;
    		String str = " ";
    		for(String strT : setVertices)
    		{
    		    if (j == item)
    		    {
    			    str = strT;
    			    break;
    		    }
    		    j = j + 1;
    		}
    		return str;
	}
	
	/* 
	 * All the constraints are satisfied: 
	 * 1. Every label l ∈ L(V ) should appear at least once in S. 
	 * 2. Every label l ∈ L(V ) should appear at most a number of times equal to the weighted
	 *	degree of the vertex v that is labeled by l. We define as weighted degree of a vertex
	 *	v the sum of the weights of the edges where v appears.
	 * 3. Within a distance D win = 1 of a n-gram there are at most D win neighboring n-grams.
	 * 4. For every two neighboring n-grams n i , n j of the text S, there is a directed edge e i =
	 *	{v x , v y }, where 1 ≤ i, j, x, y ≤ l. The amount of times an n-gram n i is the neighbor of
	 *	the n-gram n j is the weight w i of the edge e i .
	 * 5. There are no other vertices and edges in the graph G but those that are described in
	 *	the constraints (i) and (ii)
	 */
	public boolean isSolution(String[] arr)
	{
		for (int i = 0; i < vertices; i++) //first constraint
		{
			for (int j = 0; j < vertices; j++)
			{
				if ((arr[i] == arr[j]) && (i != j))
					return false;
			}
		}
		
		//TODO: fourth constraint
		
		return true;
	}
	
}