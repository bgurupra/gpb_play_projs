import java.util.List;

/**
 * 
 * @author bgurupra
 */
public class Reducer extends Thread {
	String word = null;
	List<Integer> listOfIntermediateValues = null;
	
	public void reduce(String word , List<Integer> listOfIntermediateValues){
		setWord(word);
		setListOfIntermediateValues(listOfIntermediateValues);
		start();
	}

	public void run(){
		System.out.println("Starting Reducer Thread " + getWord());
		MapReducer.updateReducerOutputs(new WordKeyValue(getWord(),getListOfIntermediateValues().size()));
		MapReducer.updateListOfReducerThreadsCompleted();
	}
	
	public List<Integer> getListOfIntermediateValues() {
		return listOfIntermediateValues;
	}

	public void setListOfIntermediateValues(List<Integer> listOfIntermediateValues) {
		this.listOfIntermediateValues = listOfIntermediateValues;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

}
