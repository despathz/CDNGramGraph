import java.util.*;


public class CSP_DFSProblem implements IProblem<String>
{
	private static final String CHARS =
			"0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyzαβγδεζηθικλμνξπστυφχψωΓΔΘΛΞΠΣΦΨΩ!@#$%&";
	
	protected String curStr;
	protected Integer curTreeHeight;
	
	public CSP_DFSProblem(String curString, Integer curTrHeight)
	{
		this.curStr = curString;
		this.curTreeHeight = curTrHeight;
	}

	@Override
	public boolean isSolution(IProblemTreeNode<String> p)
	{
		return (p.returnNodeProposedSolution().equals(curStr));
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
		String[] curStringStates = CHARS.split("");
		List<String> lPossibleChars = Arrays.asList(curStringStates);
		
		List<IProblemTreeNode<String>> lsRes = new ArrayList<>(); //Init result list
		
		int i = 0;
		for (String sPossibleChar : lPossibleChars) //For every possible character
		{
			i++;
			if (i == 1)
				continue;
			
		       String sTmp = p.returnNodeProposedSolution() + sPossibleChar; // Create the corresponding new string
		       
		       lsRes.add(0, new CSP_DFSStringProblemTreeNode(sTmp)); // Add created concatenation to results
		}
		
		return lsRes;
	}

}
