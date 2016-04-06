
public class CSP_DFSProblemTree implements IProblemTree<String>
{
	IProblemTreeNode<String> ptnRoot;
	    
	public CSP_DFSProblemTree()
	{
		ptnRoot = new CSP_DFSStringProblemTreeNode("");
	}
	
	@Override
	public IProblemTreeNode<String> getRoot()
	{
		return ptnRoot;
	}

}
