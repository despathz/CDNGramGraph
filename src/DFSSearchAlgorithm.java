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
	        IProblemTree<String> ptMyTree = new DFSProblemTree(); // Initialize tree
	        
	        int iCnt = 0;
	        IProblemTreeNode<String> ptnCurrent = ptMyTree.getRoot();  // Start from the beginning of the tree
	        methodCost++; //getRoot
	        
	        Stack<IProblemTreeNode<String>> lNextCandidates = new Stack<>(); // Initialize list of possible nodes
	        lNextCandidates.push(ptnCurrent); 
	        methodCost++; //push 
	        
	        // If we have done less than 100M steps and we have not yet found a solution
	        while ((iCnt++ < 100000000) && (!pToSolve.isSolution(ptnCurrent))) 
	        {
	       	 methodCost++; //isSolution
	       	 
	       	 if (lNextCandidates.size() == 0)  // If we checked all cases
	       		 break;
	       	 methodCost++; //size
	            
	       	 ptnCurrent = lNextCandidates.pop(); // Get next node and make it current, IF IT EXISTS
	       	 methodCost++; //pop 
	            
	       	 if (pToSolve.isValid(ptnCurrent))  // If the node is valid
	       	 { 
	       		 methodCost++; //isValid
	       		
	       	 	for (IProblemTreeNode<String> newNode : pToSolve.getNextStatesFor(ptnCurrent))  // Add its children to the possible next steps
	       	 	{
	       	 		lNextCandidates.push(newNode);
	       	 		methodCost++; //push
	       	 	}
	       	 	methodCost++; //getNextStatesFor
	       	 }
	        }
	        
	        methodCost++; //isSolution  // If we have a solution
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
