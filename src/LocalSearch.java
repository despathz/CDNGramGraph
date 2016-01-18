import java.util.*;
import java.util.Map.Entry;

public class LocalSearch
{
	int edges, vertices, getAllAppearances, totalN;
	Map <String, String> setEdges;
	Set <String> setVertices;
	Map<Map<String, String>, Double> weighted_setEdges;
	Map<String, Integer> weighted_degree;
	
	public LocalSearch (int edg, int vert, Map<String, String> setE, Set<String> setV, Map<Map<String, String>, Double> w_d, Map<String, Integer> labelA, int tot)  //constructor
	{ 
		edges = edg;
		vertices = vert;
		setEdges = setE;
		setVertices = setV;
		weighted_setEdges = w_d;
		weighted_degree = labelA;
		totalN = tot;
	}
	
	/*
	 * IsAlgorithm()
	 * Input: 
	 * 		the fields of the LocalSearch class
	 * Output: 
	 * 		A complete assignment
	 * Extra: stopping_criteria: depends on the complexity of the graph
	 * 		arrLoc:  the string list of the solution
	 * 		While no solution is found yet:
	 * 			- randomInitialize a string list
	 * 			- while no solution is found yet and the stopping criteria is not satisfied:
	 * 				~ select a variable
	 * 				~ select a value from the domain
	 * 				~ set the selected value to the selected variable
	 * 				~ if a solution is found 
	 * 					stop the loops and return the solution
	 * 				~ else
	 * 					 increase the stopping criteria
	 */
	public List<String> lsAlgorithm()
	{
		int stopping_criteria = 0, found = 0;
		List<String> arrLoc= new ArrayList<String>();
		
		while (true)
		{
			stopping_criteria = 0;
			arrLoc = randomInitialize(); 
	    				
			while (!isSolution(arrLoc) && (stopping_criteria < 1000)) //the assignement is not a solution
			{
	 			int newVar = new Random().nextInt(vertices);  //select a variable newVar
				String newVal = randomSelect();  //select a value newVal from the domain
				arrLoc.set(newVar, newVal); //change local list
				if (isSolution(arrLoc))
				{
					found = 1;
					break;
				}
				stopping_criteria++;
			}	
			if (found == 1)
				break;
		}
		return arrLoc;
	}
	
	
	/*
	 *  Initialize randomly all the variables
	 *  A String list will be returned
	 */
	public List<String> randomInitialize()
	{
		List<String> arrLoc = new ArrayList<String>();
		List <String> temp = createString();
		int total = temp.size();
		
	       for(int i = 0; i < totalN; i++)
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
	    		temp.remove(str);
	    		arrLoc.add(str);
	    		total--;
	    	}
	       return arrLoc;
	}
	
	/*
	 *  Create a string list from the domain by taking into account the weighted degree of each vertex
	 *  Return: the new string list
	 */
	public List <String> createString()
	{
		List <String> temp = new ArrayList<String>();
		for (Map.Entry<String, Integer> e : weighted_degree.entrySet()) 
    		{
    			Integer i = e.getValue();
    			getAllAppearances+= i;
    			for (int j = 0; j < i; j++)
    				temp.add(e.getKey());
    		}
//		for (String s : temp) //debugging
//			System.out.println(" ~ " + s);
//		System.out.println(getAllAppearances);
		return temp;
	}

	
	/*
	 * Randomly select a value from the domain
	 */
	public String randomSelect()
	{
		Set <String> temp = setVertices;
		int total = vertices;
		int item = new Random().nextInt(total);  //select a variable
		int j = 0;
		String str = "";
		for(String strT : temp)
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
	
	/* Input: the string list arr of the possible solution
	 * Output: true - the arr is a solution
	 * 		 false - the arr is not a solution
	 * Extra:
	 * 	All the constraints are satisfied: 
	 * 	1. Every label l ∈ L(V ) should appear at least once in S. 
	 * 	2. Every label l ∈ L(V ) should appear at most a number of times equal to the weighted
	 *		degree of the vertex v that is labeled by l. We define as weighted degree of a vertex
	 *		v the sum of the weights of the edges where v appears.
	 * 	3. Within a distance D win = 1 of a n-gram there are at most D win neighboring n-grams.
	 * 	4. For every two neighboring n-grams n i , n j of the text S, there is a directed edge e i =
	 *		{v x , v y }, where 1 ≤ i, j, x, y ≤ l. The amount of times an n-gram n i is the neighbor of
	 *		the n-gram n j is the weight w i of the edge e i .
	 * 	5. There are no other vertices and edges in the graph G but those that are described in
	 *		the constraints (i) and (ii)
	 */
	public boolean isSolution(List<String> arr)
	{
		int count = 0;
		String strTemp = "";
		
		/*
		 * 1. Every label l ∈ L(V ) should appear at least once in S. 
		 */
		Set<String> myset = weighted_degree.keySet();
		Iterator<String> it = myset.iterator();
		while (it.hasNext())
		{
			if (!arr.contains(it.next()))
				return false;
		}
		
		/*
		 * 2. Every label l ∈ L(V ) should appear at most a number of times equal to the weighted
		 *	degree of the vertex v that is labeled by l
		 */
		for (Map.Entry<String, Integer> e : weighted_degree.entrySet()) 
		{	
			count = 0;
			String s1 = e.getKey();
			 for (String s : arr)
			 {
				 if (s == s1)
					count++;
			 }
			 if (count > e.getValue())
				 return false;
		}
		
		/*
		 * 4. For every two neighboring n-grams n i , n j of the text S, there is a directed edge e i =
		 *	{v x , v y }, where 1 ≤ i, j, x, y ≤ l. The amount of times an n-gram n i is the neighbor of
		 *	the n-gram n j is the weight w i of the edge e i 
		 *
		 *  checkWeight: a copy of the weighted_setEdges. It will be used to check if the amount of times an n-gram n i is the neighbor of
		 *			    the n-gram n j is the weight w i of the edge e i
		 *  
		 */
		Map<Map<String, String>, Double> checkWeight = new HashMap<Map<String, String>, Double>(); //create a copy of the weighted_setEdges
		for (Entry<Map<String, String>, Double> e : weighted_setEdges.entrySet()) 
		 {
			Map <String, String> edg = new HashMap<String, String>();
			edg = e.getKey();
			checkWeight.put(edg, e.getValue());
		 }
		count = 0; 
		for (String str: arr)
		{
		      if (count == 0) //if count is 0 get the next one so that you can have the first edge of the graph
			      count++;
		      else
		      {
			      if(!setEdges.containsKey(str)) 
				      return false;
			      for (Map.Entry<String, String> e : setEdges.entrySet()) 
				{
					if (str== e.getKey() ) //right vertex of the edge is found
					{
						if (strTemp != e.getValue() ) //if the edge is not in the graph
							return false;
						for (Entry<Map<String, String>, Double> e1 : checkWeight.entrySet()) //decrease the weight of the found edge
						{
							Map <String, String> edg = new HashMap<String, String>();
							edg = e1.getKey();
							if (edg.containsKey(e.getKey()))
							{
								checkWeight.put(edg, e1.getValue() - 1);
								break;
							}
						}
						break;
					}
				}
			      count++;
		      }
		      strTemp = str;
		}
		for (Entry<Map<String, String>, Double> e1 : checkWeight.entrySet()) //if the weight of all the edges is not 0 then the fourth constraing is not satisfied
		{
			if (e1.getValue() != 0)
				return false;
		}
		
		return true;
	}
	
}