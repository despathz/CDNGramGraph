import java.util.Stack;

public class DFSSearchAlgorithm implements ISearchAlgorithm<String>
{
	int methodCost;
	
	public DFSSearchAlgorithm()
	{
		methodCost = 0;
	}
	
	@Override
	public IProblemTreeNode<String> getSolutionFor(IProblem<String> pToSolve)
	{
		 // Initialize tree
	        IProblemTree<String> ptMyTree = new DFSProblemTree();
	        
	        int iCnt = 0;
	        // Start from the beginning of the tree
	        IProblemTreeNode<String> ptnCurrent = ptMyTree.getRoot();
	        
	        // Initialize list of possible nodes
	        //List<IProblemTreeNode<String>> lNextCandidates = new ArrayList<>();
	        Stack<IProblemTreeNode<String>> lNextCandidates = new Stack<>();
	        methodCost++; //add  lNextCandidates.add cost
	        lNextCandidates.push(ptnCurrent); 
	        
	        // If we have done less than 10K steps and
	        // we have not yet found a solution
	        while ((iCnt++ < 100000) && (!pToSolve.isSolution(ptnCurrent))) 
	        {
	       	 methodCost++; //isSolution() method cost
	       	 // If we checked all cases
	       	 if (lNextCandidates.size() == 0)
	       	 {
	       		 System.out.println("break");
	       		 break;
	       	 }
	            
	       	 // Get next node and make it current, IF IT EXISTS
	       	 methodCost++; //lNextCandidates.pop cost
	       	 ptnCurrent = lNextCandidates.pop(); 
	            
	       	 // If the node is valid
	       	 methodCost++; //isValid() method cost
	       	 if (pToSolve.isValid(ptnCurrent))
	       	 { // Add its children to the possible next steps
	       	 	methodCost++; //lNextCandidates.addAll cost
	       	 	for (IProblemTreeNode<String> p : pToSolve.getNextStatesFor(ptnCurrent))
	       	 	{
	       	 		//System.out.println(" + " + p.returnNodeProposedSolution());
	       	 		lNextCandidates.push(p);
	       	 	}
	       	 }
	        }
	        
	        // If we have a solution
	        methodCost++; //isSolution() method cost
	        if (pToSolve.isSolution(ptnCurrent))
	            return ptnCurrent; // Return it
	        else
	            return null; // Nothing found 
	}
	
	public Integer getMethodCost()
	{
		return methodCost;
	}

}
