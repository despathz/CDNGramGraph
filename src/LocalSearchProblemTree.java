import java.util.ArrayList;
import java.util.List;

public class LocalSearchProblemTree implements IProblemTree<List<String>>
{
	IProblemTreeNode<List<String>> ptnRoot;
	    
	public LocalSearchProblemTree()
	{
		List<String> arrLoc= new ArrayList<String>();
		arrLoc.add("");
		ptnRoot = new LocalSearchStringProblemTreeNode(arrLoc);
	}
	
	@Override
	public IProblemTreeNode<List<String>> getRoot()
	{
		return ptnRoot;
	}
}
