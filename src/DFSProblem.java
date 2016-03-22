import java.util.*;


public class DFSProblem implements IProblem<String>
{
	protected String curStr;
	protected Integer curTreeHeight;
	
	public DFSProblem(String curString, Integer curTrHeight)
	{
		this.curStr = curString;
		this.curTreeHeight = curTrHeight;
	}

	@Override
	public boolean isSolution(IProblemTreeNode<String> p)
	{
		return (p.returnNodeProposedSolution() == curStr);
	}

	@Override
	public boolean isValid(IProblemTreeNode<String> p)
	{
		return !(p.returnNodeProposedSolution().length() > curTreeHeight);
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
		String[] curStringStates = curStr.split("");
		List<String> lPossibleChars = Arrays.asList(curStringStates);
		
		//Init result list
		List<IProblemTreeNode<String>> lsRes = new ArrayList<>();
		//For every possible character
		for (String sPossibleChar : lPossibleChars)
		{
			// Create the corresponding new string
		       String sTmp = p.returnNodeProposedSolution() + sPossibleChar;
		       // Add created concatenation to results
		       lsRes.add(new DFSStringProblemTreeNode(sTmp));
		}
		
		return lsRes;
	}

}
