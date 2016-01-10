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
	public String lsAlgorithm()
	{
		String[] arrLoc = new String[vertices];
		arrLoc = randomInitialize(); 
		
		System.out.println("so?");
		return "ok";
	}
	
	
	/*
	 *  Initialize randomly all the variables
	 *  A String array will be returnt
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
	       return arrLoc;
	}
	
	
}