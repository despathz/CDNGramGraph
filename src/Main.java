import gr.demokritos.iit.jinsect.utils;
import gr.demokritos.iit.jinsect.documentModel.representations.DocumentNGramGraph;

public class Main 
{

    public static void main(String[] args) 
    {
    	 // The string we want to represent
	   	 String sTmp = "New graph!";
	
	   	 // The default document n-gram graph, with min n-gram size and max n-gram size set to 3, and the dist parameter set to 3.
	   	DocumentNGramGraph dngGraph = new DocumentNGramGraph(); 
	
	   	// Create the graph
	   	dngGraph.setDataString(sTmp);
	   	
	   	/* The following command gets the first n-gram graph level (with the minimum n-gram
        size) and renders it, using the utils package, as a DOT string */
        System.out.println(utils.graphToDot(dngGraph.getGraphLevel(0), true));
	   	
	   	
    }
}


