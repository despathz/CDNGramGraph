//
//import java.io.*;
//import java.util.*;
//
//public class MyTreeNode<T> implements Iterable<MyTreeNode<T>>
//{
//	String CHARS =
//		"0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyzαβγδεζηθικλμνξπστυφχψωΓΔΘΛΞΠΣΦΨΩ!@#$%&";
//
//	String data;
//	MyTreeNode<String> parent;
//	List<MyTreeNode<String>> children;
//	Integer stringLength;
//	List<String> arrLoc= new ArrayList<String>();
//
//	public MyTreeNode(String name, Integer length) 
//	{
//		data = name;
//		stringLength = length;
//		children = new LinkedList<MyTreeNode<String>>();
//		
//		for (char ch: CHARS.toCharArray())
//			arrLoc.add(String.valueOf(ch));
//		MyTreeNode<String> root = new MyTreeNode<String>(data, stringLength);
//		TreeAddChild(root, stringLength);
//		
//	}
//
//	public MyTreeNode(String str)
//	{
//		data = str;
//		children = new LinkedList<MyTreeNode<String>>();
//	}
//
//	public MyTreeNode<String> addChild(String child) 
//	{
//		MyTreeNode<String> childNode = new MyTreeNode<String>(child);
//		childNode.parent = this;
//		this.children.add(childNode);
//		return childNode;
//	}
//
////	public void TreeCreate()
////	{
////		for (char ch: CHARS.toCharArray())
////			arrLoc.add(String.valueOf(ch));
////		
////		String str = "root";
////		MyTreeNode<String> root = new MyTreeNode<String>(str, stringLength);
////		TreeAddChild(root, stringLength);
////		return;
////	}
//	
//	public void TreeAddChild(MyTreeNode node, Integer length)
//	{
//		if (length == 0)
//			return;
//		
//		//add all the characters of the CHARS string
//		for (String str: arrLoc)
//		{
//			MyTreeNode<String> str = node.addChild(str);
//			TreeAddChild(str, length - 1);
//		}
//		
//	}
//	
//}


