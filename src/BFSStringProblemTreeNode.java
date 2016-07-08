
public class BFSStringProblemTreeNode implements IProblemTreeNode<String>
{
	protected String sString;

	public  BFSStringProblemTreeNode(String sString)
	{
		this.sString = sString;
	}

	@Override
	public String returnNodeProposedSolution()
	{
		return sString;
	}

}
