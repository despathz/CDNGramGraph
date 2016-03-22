
public class DFSStringProblemTreeNode implements IProblemTreeNode<String>
{
	protected String sString;

	public  DFSStringProblemTreeNode(String sString)
	{
		this.sString = sString;
	}

	@Override
	public String returnNodeProposedSolution()
	{
		return sString;
	}

}
