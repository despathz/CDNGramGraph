
import java.util.List;

public interface IProblem<SOLUTION_TYPE>
{
	public boolean isSolution(IProblemTreeNode<SOLUTION_TYPE> p);
	public boolean isValid(IProblemTreeNode<SOLUTION_TYPE> p);
	public double estimateDistanceFromSolution(IProblemTreeNode<SOLUTION_TYPE> p);
	public List<IProblemTreeNode<SOLUTION_TYPE>> getNextStatesFor(IProblemTreeNode<SOLUTION_TYPE> p);
}
