import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 
 * @author bgurupra
 */
public class Mapper extends Thread {

	FileKeyValue fileKeyValue = null;

	public void map(FileKeyValue fileKeyValue) {
		setFileKeyValue(fileKeyValue);
		start();
	}

	public FileKeyValue getFileKeyValue() {
		return fileKeyValue;
	}

	public void setFileKeyValue(FileKeyValue fileKeyValue) {
		this.fileKeyValue = fileKeyValue;
	}
	
	public void run(){
		System.out.println("Starting Mapper Thread " +fileKeyValue.getFileName());
		List<WordKeyValue> outputList = new ArrayList<WordKeyValue>();
		Iterator<String> iter = getFileKeyValue().getFileValue().iterator();
		while(iter.hasNext()){
			outputList.add(new WordKeyValue(iter.next(), new Integer(1)));
		}
		MapReducer.updateMapperOutputs(outputList);
		MapReducer.updateListOfMapperThreadsCompleted();
	}

}
