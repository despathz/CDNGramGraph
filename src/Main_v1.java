
public class Main_v1
{

	public static void main(String[] args) throws Exception 
	{
		String curString = "000";
		Integer treeHeight = 3;
		
		 // Create the problem
	        IProblem<String> myProblem = new DFSProblem(curString, treeHeight);
	        // Create the Search algorithm
	        ISearchAlgorithm<String> s = new DFSSearchAlgorithm();
	        
	        IProblemTreeNode<String> ptnSol = s.getSolutionFor(myProblem);
	        String sSolution = ptnSol == null ? "[No solution found]"
	                : ptnSol.returnNodeProposedSolution();
	        
	        System.out.println("Solution:" + sSolution);

	}

}
