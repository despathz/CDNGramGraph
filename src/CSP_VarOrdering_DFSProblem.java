import java.util.*;
import java.util.Map.Entry;


public class CSP_VarOrdering_DFSProblem implements IProblem<String>
{
	private static final String CHARS =
			"0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyzαβγδεζηθικλμνξπστυφχψωΓΔΘΛΞΠΣΦΨΩ!@#$%&";
	
	protected Integer curTreeHeight;
	protected int edges, vertices, getAllAppearances;
	protected Map <String, String> setEdges;
	protected Set <String> setVertices;
	protected Map<Map<String, String>, Double> weighted_setEdges;
	protected Map<String, Integer> weighted_degree;
	
	public CSP_VarOrdering_DFSProblem(Integer curTrHeight, int edg, int vert, Map<String, String> setE, Set<String> setV, Map<Map<String, String>, Double> w_d, Map<String, Integer> labelA)
	{
		this.curTreeHeight = curTrHeight;
		this.edges = edg;
		this.vertices = vert;
		this.setEdges = setE;
		this.setVertices = setV;
		this.weighted_setEdges = w_d;
		this.weighted_degree = labelA;
	}

	@Override
	public boolean isSolution(IProblemTreeNode<String> p)
	{
		String arrTmp = p.returnNodeProposedSolution();
		String strTemp = "";
		/*
		 * 1. Every label l ∈ L(V ) should appear at least once in S. 
		 */
		Set<String> myset = weighted_degree.keySet();
		Iterator<String> it = myset.iterator();
		while (it.hasNext())
		{
			if (!arrTmp.contains(it.next()))
				return false;
		}
		
		 List<String> arr = new ArrayList<String>(); //create an arrayList to store the characters of the string 
		 for (int z = 0; z < arrTmp.length(); z++)
	       	arr.add(String.valueOf(arrTmp.charAt(z)));
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
		int count = 0; 
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
					if (str.equals(e.getKey())) //right vertex of the edge is found
					{
						if (!strTemp.equals(e.getValue())) //if the edge is not in the graph
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
	public boolean isValid(IProblemTreeNode<String> p)
	{
		return !(p.returnNodeProposedSolution().length() + 1 > curTreeHeight);
	}

	@Override
	public double estimateDistanceFromSolution(IProblemTreeNode<String> p)
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
	public List<IProblemTreeNode<String>> getNextStatesFor(IProblemTreeNode<String> p)
	{
		String curStrStates = "";
		
		Map<String, Integer> sortedWeightedDegree = new HashMap<String, Integer>();

		sortedWeightedDegree = fixWeightedDegree(p); //fix the weight of the vertices that are in use
//		print for debugging
		for (Map.Entry<String, Integer> entry : sortedWeightedDegree.entrySet())
		{
			System.out.println("Key 1: " + entry.getKey() + " value 1: " + entry.getValue());
		}
		
//		if  (sortedWeightedDegree.isEmpty())
//			sortedWeightedDegree = weighted_degree;
		sortedWeightedDegree =  sortByValue(sortedWeightedDegree);
		
		//print for debugging
		for (Map.Entry<String, Integer> entry : sortedWeightedDegree.entrySet())
		{
			System.out.println("Key: " + entry.getKey() + " value: " + entry.getValue());
		}
		
		//only vertices from the graph can be inserted
		Iterator<String> verticesIterator = sortedWeightedDegree.keySet().iterator();
		while (verticesIterator.hasNext())
		{
			String curVertex = verticesIterator.next();
			curStrStates += curVertex;
		}
		
		String[] curStringStates = curStrStates.split("");
		List<String> lPossibleChars = Arrays.asList(curStringStates);
		List<IProblemTreeNode<String>> lsRes = new ArrayList<>(); //Init result list
		
		int i = 0, getNext = 0;
		for (String sPossibleChar : lPossibleChars) //For every possible character
		{
			i++;
			if (i == 1)
				continue;
			
		       String sTmp = p.returnNodeProposedSolution() + sPossibleChar; // Create the corresponding new string
		       
		       List<String> arr = new ArrayList<String>();
		       for (int z = 0; z < sTmp.length(); z++)
		       	arr.add(String.valueOf(sTmp.charAt(z)));
		       
		       if (sTmp.length() == 1)
		       {
			       //the label we are about to store belongs to the set of labels
			       Set<String> myset = weighted_degree.keySet();
				Iterator<String> it = myset.iterator();
				while (it.hasNext())
				{
					if (!arr.contains(it.next()))
						getNext = 1;
					else
					{
						getNext = 0;
						break;
					}
				}
				if (getNext == 1)
				{
					 getNext = 0;
					 continue;
				}
		       }
		       else
		       {
			       /*
				 * 2. Every label l ∈ L(V ) should appear at most a number of times equal to the weighted
				 *	degree of the vertex v that is labeled by l
				 */
			       for (Map.Entry<String, Integer> e : weighted_degree.entrySet()) 
				{	
					 int count = 0;
					 String s1 = e.getKey();
					 for (String s : arr)
					 {
						 if (s.equals(s1))
							count++;
					 }
					 if ((count > e.getValue()))
					 {
						 getNext = 1;
						 break;
					 }
				}
			       if (getNext == 1)
				{
					 getNext = 0;
					 continue;
				}
			       
				//The 2 last added characters should be a directed edge

				String strTemp = String.valueOf(sTmp.charAt(sTmp.length() - 2));
				String str = String.valueOf(sTmp.charAt(sTmp.length() - 1));
				Map<Map<String, String>, Double> checkWeight = new HashMap<Map<String, String>, Double>(); //create a copy of the weighted_setEdges
				for (Entry<Map<String, String>, Double> e : weighted_setEdges.entrySet()) 
				{
					Map <String, String> edg = new HashMap<String, String>();
					edg = e.getKey();
					checkWeight.put(edg, e.getValue());
				}
				getNext = 0; 
				if(!setEdges.containsKey(str)) 
					getNext = 1;
				for (Map.Entry<String, String> e : setEdges.entrySet()) 
				{
					if (str== e.getKey() ) //right vertex of the edge is found
					{
						if (strTemp != e.getValue() ) //if the edge is not in the graph
						{
						       getNext = 1;
						       break;
					       }
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

				if (getNext == 1)
				{
					getNext = 0;
					continue;
				}
		       }
		       System.out.println("Proposed: " + sTmp);
		       lsRes.add(0, new DFSStringProblemTreeNode(sTmp)); // Add created concatenation to results
		}
		return lsRes;
	}

	@SuppressWarnings("hiding")
	public <String, Integer extends Comparable<? super Integer>> Map<String, Integer> sortByValue(Map<String, Integer> map) 
	{
		List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>()
		{
			public int compare(Map.Entry<String, Integer> a, Map.Entry<String, Integer> b)
			{
				return (a.getValue()).compareTo(b.getValue());
			}
		});
		Map<String, Integer> result = new LinkedHashMap<>();
		for (Map.Entry<String, Integer> entry: list)
			result.put(entry.getKey(), entry.getValue());
		return result;
	}
	
	public Map<String, Integer> fixWeightedDegree(IProblemTreeNode<String> p)
	{
		Map<String, Integer> fixedWeightedDegree = new HashMap<String, Integer>();
		
		String[] usedVertices = p.returnNodeProposedSolution().split("");
		
		int ignoreFirstChar = 0;
		for (String str : usedVertices)
		{
			ignoreFirstChar++;
			if (ignoreFirstChar == 1) //split first char is empty
				continue;
			fixedWeightedDegree.put(str, weighted_degree.get(str) - 1);
		}
		for (String str : weighted_degree.keySet())
		{
			if (fixedWeightedDegree.containsKey(str))
				continue;
			fixedWeightedDegree.put(str, weighted_degree.get(str));
		}
		
		return fixedWeightedDegree;
	}
	
}
