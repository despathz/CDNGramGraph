import java.util.List;

public class LocalSearchStringProblemTreeNode implements IProblemTreeNode<List<String>>
{
	protected List<String> sString;

	public  LocalSearchStringProblemTreeNode(List<String> sString)
	{
		this.sString = sString;
	}

	@Override
	public List<String> returnNodeProposedSolution()
	{
		return sString;
	}
}
