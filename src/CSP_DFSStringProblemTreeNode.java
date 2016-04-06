
public class CSP_DFSStringProblemTreeNode implements IProblemTreeNode<String>
{
	protected String sString;

	public  CSP_DFSStringProblemTreeNode(String sString)
	{
		this.sString = sString;
	}

	@Override
	public String returnNodeProposedSolution()
	{
		return sString;
	}

}
