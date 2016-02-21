import java.util.List;


public interface LocalSearchI
{
	 List<String> lsAlgorithm();
	 
	 List<String> randomInitialize();
	 
	 List <String> createString();
	 
	 String randomSelect();
	 
	 boolean isSolution(List<String> arr);
}
