import java.util.*;


public class BFSProblem implements IProblem<String>
{
	private static final String CHARS =
			"0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyzαβγδεζηθικλμνξπστυφχψωΓΔΘΛΞΠΣΦΨΩ!@#$%&";
	protected String curStr;
	protected Integer curTreeHeight;
	
	public BFSProblem(String curString, Integer curTrHeight)
	{
		this.curStr = curString;
		this.curTreeHeight = curTrHeight;
	}

	@Override
	public boolean isSolution(IProblemTreeNode<String> p)
	{
		System.out.println("solution???" + curStr + " " + p.returnNodeProposedSolution());
		System.out.println("solution???" + p.returnNodeProposedSolution().equals(curStr));
		return (p.returnNodeProposedSolution().equals(curStr));
	}

	@Override
	public boolean isValid(IProblemTreeNode<String> p)
	{
		System.out.println("valid???" + p.returnNodeProposedSolution().length() + " " + curTreeHeight);
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
		String[] curStringStates = CHARS.split("");
		List<String> lPossibleChars = Arrays.asList(curStringStates);
		
		//Init result list
		List<IProblemTreeNode<String>> lsRes = new ArrayList<>();
		//For every possible character
		for (String sPossibleChar : lPossibleChars)
		{
			// Create the corresponding new string
		       String sTmp = p.returnNodeProposedSolution() + sPossibleChar;
		       // Add created concatenation to results
		       lsRes.add(new BFSStringProblemTreeNode(sTmp));
		}
		
		return lsRes;
	}

}
