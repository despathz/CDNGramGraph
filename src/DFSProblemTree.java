
public class DFSProblemTree implements IProblemTree<String>
{
	IProblemTreeNode<String> ptnRoot;
	    
	public DFSProblemTree()
	{
		ptnRoot = new DFSStringProblemTreeNode("");
	}
	
	@Override
	public IProblemTreeNode<String> getRoot()
	{
		return ptnRoot;
	}

}
