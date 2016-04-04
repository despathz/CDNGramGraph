import java.util.Map.Entry;
import java.util.*;

public class LocalSearchProblem implements IProblem<List<String>>
{
	protected int edges, vertices, getAllAppearances, totalN;
	protected Map <String, String> setEdges;
	protected Set <String> setVertices;
	protected Map<Map<String, String>, Double> weighted_setEdges;
	protected Map<String, Integer> weighted_degree;
	
	public LocalSearchProblem (int edg, int vert, Map<String, String> setE, Set<String> setV, Map<Map<String, String>, Double> w_d, Map<String, Integer> labelA, int tot)  //constructor
	{ 
		this.edges = edg;
		this.vertices = vert;
		this.setEdges = setE;
		this.setVertices = setV;
		this.weighted_setEdges = w_d;
		this.weighted_degree = labelA;
		this.totalN = tot;
	}

	@Override
	public boolean isSolution(IProblemTreeNode<List<String>> p)
	{
		int count = 0;
		String strTemp = "";
		List<String> arr = p.returnNodeProposedSolution();
		
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

	@Override
	public boolean isValid(IProblemTreeNode<List<String>> p)
	{
		return false;
	}

	@Override
	public double estimateDistanceFromSolution(IProblemTreeNode<List<String>> p)
	{
		if (isSolution(p))
		{
			return 0.0;
		}
		else
		{
			return 1.0; //Fixed distance estimate
		}
	}

	@Override
	public List<IProblemTreeNode<List<String>>> getNextStatesFor(IProblemTreeNode<List<String>> p)
	{	
		//Init result list
		List<IProblemTreeNode<List<String>>> lsRes = new ArrayList<>();
		List<String> arrLoc= new ArrayList<String>();
		// Create the corresponding new string
		if (p.returnNodeProposedSolution().get(0) == "")
			arrLoc = randomInitialize(); 
		else
		{
			int newVar = new Random().nextInt(vertices);  //select a variable newVar
			String newVal = randomSelect();  //select a value newVal from the domain
			arrLoc = p.returnNodeProposedSolution();
			arrLoc.set(newVar, newVal); //change local list
		}
	       // Add created String to results
	       lsRes.add(new LocalSearchStringProblemTreeNode(arrLoc));
		return lsRes;
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
	    			    temp.remove(j);
	    			    break;
	    		    }
	    		    j = j + 1;
	    		}
	    		//temp.remove(str);
	    		//temp=temp.replaceFirst(String.valueOf(temp.charAt(j)),"");
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
    			int i = e.getValue();
    			getAllAppearances+= i;
    			for (int j = 0; j < i; j++)
    				temp.add(e.getKey());
    		}
		//for (String s : temp) //debugging
		//	System.out.println(" ~ " + s);
		//System.out.println(getAllAppearances);
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
}
