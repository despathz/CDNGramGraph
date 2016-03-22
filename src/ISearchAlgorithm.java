
/*
 * Can solve a given problem
 */

public interface ISearchAlgorithm<SOLUTION_TYPE>
{
	public IProblemTreeNode<SOLUTION_TYPE> getSolutionFor(IProblem<SOLUTION_TYPE> pToSolve);
}
