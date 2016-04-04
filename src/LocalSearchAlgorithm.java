import java.util.ArrayList;
import java.util.List;

public class LocalSearchAlgorithm implements ISearchAlgorithm<List<String>>
{
	int methodCost;
	
	public LocalSearchAlgorithm()
	{
		methodCost = 0;
	}
	
	@Override
	public IProblemTreeNode<List<String>> getSolutionFor(IProblem<List<String>> pToSolve)
	{
		IProblemTree<List<String>> ptMyTree = new LocalSearchProblemTree(); // Initalize search tree
		
		int iCnt = 0;
		int stopping_criteria = 0;

		IProblemTreeNode<List<String>> ptnCurrent = ptMyTree.getRoot(); // Start from the beginning of the tree
		methodCost++; //getRoot
		
	        List<IProblemTreeNode<List<String>>> lNextCandidates = new ArrayList<>(); // Initialize list of possible nodes
	        lNextCandidates.add(ptnCurrent);
	        methodCost++; //add
	        
	        while (iCnt++ < 100000000) // If we have done less than 100M steps
	        {
	       	 stopping_criteria = 0;
	       	 
			while ((!pToSolve.isSolution(ptnCurrent)) && (stopping_criteria++ < 1000)) //the assignement is not a solution
			{
				 methodCost++; //isSolution 
				 
				 lNextCandidates.clear(); //clear List as only one element is stored each time
				 methodCost++; //clear
				 
				 lNextCandidates.addAll(pToSolve.getNextStatesFor(ptnCurrent));
				 methodCost+=2; //addAll & getNextStatesFor
				 
				 ptnCurrent = lNextCandidates.get(0); //get first element
				 methodCost++; //get
			}
			// If we have a solution
			
		        methodCost++; //isSolution
		        if (pToSolve.isSolution(ptnCurrent))
		       	 return ptnCurrent; 
		        
		        lNextCandidates.clear();
		        methodCost++; //clear
		        
		        ptnCurrent = ptMyTree.getRoot();
		        methodCost++; //getRoot
		        
		        lNextCandidates.add(ptnCurrent);
		        methodCost++; //add
		        stopping_criteria = 0;
	        }
		return null;
	}

	@Override
	public Integer getMethodCost()
	{
		return methodCost;
	}

}
