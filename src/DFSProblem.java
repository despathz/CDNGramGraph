import java.util.*;


public class DFSProblem implements IProblem<String>
{
	private static final String CHARS =
			"0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyzαβγδεζηθικλμνξπστυφχψωΓΔΘΛΞΠΣΦΨΩ!@#$%&";
	
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
		System.out.println("solution???" + curStr + " " + p.returnNodeProposedSolution());
		System.out.println("solution???" + p.returnNodeProposedSolution().equals(curStr));
		return (p.returnNodeProposedSolution().equals(curStr));
	}

	@Override
	public boolean isValid(IProblemTreeNode<String> p)
	{
		System.out.println("valid???" + p.returnNodeProposedSolution().length() + " " + curTreeHeight);
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
		
		//Init result list
		List<IProblemTreeNode<String>> lsRes = new ArrayList<>();
		//For every possible character
		int i = 0;
		for (String sPossibleChar : lPossibleChars)
		{
			i++;
			if (i == 1)
				continue;
			System.out.print(sPossibleChar + " ~ ");
			// Create the corresponding new string
		       String sTmp = p.returnNodeProposedSolution() + sPossibleChar;
		       // Add created concatenation to results
		       lsRes.add(0, new DFSStringProblemTreeNode(sTmp));
		       System.out.println("\n" + lsRes.get(0).returnNodeProposedSolution());
		}
		
		return lsRes;
	}

}
