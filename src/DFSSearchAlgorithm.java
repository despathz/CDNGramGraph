import java.util.ArrayList;
import java.util.List;

public class DFSSearchAlgorithm implements ISearchAlgorithm<String>
{

	@Override
	public IProblemTreeNode<String> getSolutionFor(IProblem<String> pToSolve)
	{
		 // Initialize tree
	        IProblemTree<String> ptMyTree = new DFSProblemTree();
	        
	        int iCnt = 0;
	        // Start from the beginning of the tree
	        IProblemTreeNode<String> ptnCurrent = ptMyTree.getRoot();
	        
	        // Initialize list of possible nodes
	        List<IProblemTreeNode<String>> lNextCandidates = new ArrayList<>();
	        lNextCandidates.add(ptnCurrent);
	        
	        // If we have done less than 10K steps and
	        // we have not yet found a solution
	        while ((iCnt++ < 1000000) && (!pToSolve.isSolution(ptnCurrent))) 
	        {
	       	 System.out.println("in");
	       	 // If we checked all cases
	       	 if (lNextCandidates.size() == 0)
	       	 {
	       		 System.out.println("break");
	       		 break;
	       	 }
	            
	       	 // Get next node and make it current, IF IT EXISTS
	       	 ptnCurrent = lNextCandidates.get(0);
	       	 // Remove it from candidates
	       	 lNextCandidates.remove(0);
	            
	       	 // If the node is valid
	       	 if (pToSolve.isValid(ptnCurrent))
	       		 // Add its children to the possible next steps
	       		 lNextCandidates.addAll(pToSolve.getNextStatesFor(ptnCurrent));
	       	 System.out.println(lNextCandidates.size());
	        }
	        
	        // If we have a solution
	        if (pToSolve.isSolution(ptnCurrent))
	            return ptnCurrent; // Return it
	        else
	            return null; // Nothing found 
	}

}
