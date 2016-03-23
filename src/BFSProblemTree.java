
public class BFSProblemTree implements IProblemTree<String>
{
	IProblemTreeNode<String> ptnRoot;
	    
	public BFSProblemTree()
	{
		ptnRoot = new BFSStringProblemTreeNode("");
	}
	
	@Override
	public IProblemTreeNode<String> getRoot()
	{
		return ptnRoot;
	}

}
