import java.util.*;

public class TreeNode<T> implements Iterable<TreeNode<T>>
{
	T data;
	TreeNode<T> parent;
	List<TreeNode<T>> children;
	Integer stringLength;
	String nodeName = "";
	
	public TreeNode(T str) //for root
	{
		this.data = str;
		this.nodeName = "0";
		this.children = new LinkedList<TreeNode<T>>();
	}
	
	public TreeNode(T str, TreeNode<T> parent)
	{
		this.data = str;
		this.parent = parent;
		int numParentChildren = parent.getNumChildren();
		this.nodeName = parent.getName() + "_" + numParentChildren;
		this.children = new LinkedList<TreeNode<T>>();
	}
	
	/*
	 * @param height: the length of the input string
	 */
	public TreeNode<String> TreeCreate(Integer height)
	{
		String CHARS =
			"0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyzαβγδεζηθικλμνξπστυφχψωΓΔΘΛΞΠΣΦΨΩ!@#$%&";

		TreeNode<String> root = new TreeNode<String>("root");
		TreeNode<String> current;
		Queue<TreeNode<String>> queue = new LinkedList<TreeNode<String>>(); //Queue holding the nodes for which children will be created
		queue.add(root);
		
		for (int i = 0; i < height; i++)
		{
			current = queue.remove();
			for (int j = 0; j < CHARS.length(); j++)
			{
				String mychar = Character.toString(CHARS.charAt(j));
				TreeNode<String> child = new TreeNode<String>(mychar, current);
				current.children.add(child);
				queue.add(child);
			}
		}
		return root;
	}

	public String getName()
	{
		return this.nodeName;
	}
	
	public Integer getNumChildren()
	{
		return this.children.size();
	}
	
	@Override
	public Iterator<TreeNode<T>> iterator()
	{
		// TODO Auto-generated method stub
		return null;
	}
	
}


