import java.util.*;
import java.util.Map.Entry;

public class LocalSearch
{
	int edges, vertices, getAllAppearances, totalN;
	Map <String, String> setEdges;
	Set <String> setVertices;
	Map<Map<String, String>, Double> weighted_degree;
	Map<String, Integer> labelAppearance;
	
	public LocalSearch (int edg, int vert, Map<String, String> setE, Set<String> setV, Map<Map<String, String>, Double> w_d, Map<String, Integer> labelA, int tot)  //constructor
	{ 
		edges = edg;
		vertices = vert;
		setEdges = setE;
		setVertices = setV;
		weighted_degree = w_d;
		labelAppearance = labelA;
		totalN = tot;
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
	public List<String> lsAlgorithm()
	{
		int stopping_criteria = 0, found = 0;
		List<String> arrLoc= new ArrayList<String>();
		
		while (true)
		{
			stopping_criteria = 0;
			arrLoc = randomInitialize(); 
			Iterator<String> it = arrLoc.iterator();
	    		while (it.hasNext())
	    			System.out.print(it.next());
	    		System.out.println(" done");
	    				
			while (!isSolution(arrLoc) && (stopping_criteria < 1000)) //the assignement is not a solution
			{
	 			int newVar = new Random().nextInt(vertices);  //select a variable newVar
				String newVal = randomSelect();  //select a value newVal from the domain
				arrLoc.set(newVar, newVal); //change local array
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
	 *  A String array will be returned
	 */
	public List<String> randomInitialize()
	{
		List<String> arrLoc = new ArrayList<String>();
		List <String> temp = createString();
		int total = temp.size();
		
	       for(int i = 0; i < totalN; i++)
	    	{
	    		int item = new Random().nextInt(total); //pick value from the domain
	    		System.out.print(item + " " + temp);
	    		int j = 0;
	    		String str = " ";
	    		for(String strT : temp)
	    		{
	    		    if (j == item)
	    		    {
	    			    System.out.print(strT);
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
	
	public List <String> createString()
	{
		List <String> temp = new ArrayList<String>();
		for (Map.Entry<String, Integer> e : labelAppearance.entrySet()) 
    		{
    			Integer i = e.getValue();
    			getAllAppearances+= i;
    			for (int j = 0; j < i; j++)
    				temp.add(e.getKey());
    		}
//		for (String s : temp)
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
	public boolean isSolution(List<String> arr)
	{
		int count = 0;
		String strTemp = "";
		
		/*
		 * 1. Every label l ∈ L(V ) should appear at least once in S. 
		 */
		Set<String> myset = labelAppearance.keySet();
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
		for (Map.Entry<String, Integer> e : labelAppearance.entrySet()) 
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
		 */
		Map<Map<String, String>, Double> checkWeight = new HashMap<Map<String, String>, Double>();
		for (Entry<Map<String, String>, Double> e : weighted_degree.entrySet()) 
		 {
			Map <String, String> edg = new HashMap<String, String>();
			edg = e.getKey();
			checkWeight.put(edg, e.getValue());
		 }
		count = 0;
		for (String str: arr)
		{
		      if (count == 0)
			      count++;
		      else
		      {
			      if(!setEdges.containsKey(str))
				      return false;
			      for (Map.Entry<String, String> e : setEdges.entrySet()) 
				{
					if (str== e.getKey() ) //edge is correct
					{
						if (strTemp != e.getValue() ) 
							return false;
						for (Entry<Map<String, String>, Double> e1 : checkWeight.entrySet()) 
						{
							Map <String, String> edg = new HashMap<String, String>();
							edg = e1.getKey();
							if (edg.containsKey(e.getKey()))
							{
								checkWeight.put(edg, e1.getValue() - 1);
								System.out.println(edg + " + " + e1.getValue());
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
		for (Entry<Map<String, String>, Double> e1 : checkWeight.entrySet()) 
		{
			if (e1.getValue() != 0)
				return false;
		}
		
		return true;
	}
	
}