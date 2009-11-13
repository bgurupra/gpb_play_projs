import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map.Entry;

public class MapReducer {

	/**
	 * Base folder where list of text files are kept for word counting
	 */
	public static final String baseFolder = "/home/user/workspace/map_reduce/examples";

	/**
	 * The number of Mapper Threads started
	 */
	public static int countOfMapperThreadsStarted = 0;

	/**
	 * The number of Mapper Threads that are completed
	 */
	public static int countOfMapperThreadsCompleted = 0;

	/**
	 * All the Mappers output get written to this variable
	 */
	public static List<List<WordKeyValue>> mapperOutputs = new ArrayList<List<WordKeyValue>>();

	/**
	 * The number of Mapper Threads started
	 */
	public static int countOfReducerThreadsStarted = 0;

	/**
	 * The number of Mapper Threads that are completed
	 */
	public static int countOfReducerThreadsCompleted = 0;

	/**
	 * All the reducer output get written to this variable
	 */
	public static List<WordKeyValue> reducerOutputs = new ArrayList<WordKeyValue>();

	public static void main(String[] args) {

		// get a list of files in the baseFolder
		// create a list of key value pairs (fileName,fileContents)
		List<FileKeyValue> inputData = getInputListForProcessing();

		// Thread out each of the key value pairs (fileName,fileContents) to a
		// Mapper
		Iterator<FileKeyValue> iter = inputData.iterator();
		FileKeyValue input = null;
		while (iter.hasNext()) {
			input = (FileKeyValue) iter.next();
			new Mapper().map(input);
			countOfMapperThreadsStarted++;
		}
		// Wait for all Mappers to finish and return back a list of key value
		// pairs(outputKey, intermediateValue) i.e. (word ,1)
		while (countOfMapperThreadsStarted != countOfMapperThreadsCompleted)
			;

		// Sort by output key and create a list of intermediate value for the
		// same output key
		SortedMap<String, List<Integer>> reducerInputMap = generateReducerInputs();
		Iterator<Entry<String, List<Integer>>> reducerInputMapIter = reducerInputMap.entrySet().iterator();
		// Thread out the Reducers for each (outputKey,listOfIntermedieateValues)
		while(reducerInputMapIter.hasNext()){
			Entry<String, List<Integer>> intermedieateResult = reducerInputMapIter.next();
			new Reducer().reduce(intermedieateResult.getKey(), intermedieateResult.getValue());
			countOfReducerThreadsStarted++;
		}

		// Wait for all reducers to return a key value pair of(outputKey,finalValue)
		while (countOfReducerThreadsStarted != countOfReducerThreadsCompleted)
			;

		// print out the values
		Iterator<WordKeyValue> reducerOutputIter = reducerOutputs.iterator();
		while(reducerOutputIter.hasNext()){
			WordKeyValue finalWordKeyValue = reducerOutputIter.next();
			System.out.println(" Word == "+finalWordKeyValue.getWord() + " Word Count == "+finalWordKeyValue.getWordCount());
		}

	}

	/**
	 * Generate the input values for the reducer
	 * 
	 * @return
	 */
	protected static SortedMap<String, List<Integer>> generateReducerInputs() {
		// the input to each reducer is ( outputKey, ListOfIntermediateValues)
		// this map will hold that combination
		SortedMap<String, List<Integer>> reducerInputMap = new TreeMap<String, List<Integer>>();

		Iterator<List<WordKeyValue>> iter = mapperOutputs.iterator();
		while (iter.hasNext()) {
			List<WordKeyValue> listOfWordKeyValues = iter.next();
			Iterator<WordKeyValue> iter1 = listOfWordKeyValues.iterator();
			while (iter1.hasNext()) {
				WordKeyValue wordKeyValue = iter1.next();
				List<Integer> listOfIntermediateValues = reducerInputMap
						.get(wordKeyValue.getWord());
				if (listOfIntermediateValues == null) {
					listOfIntermediateValues = new ArrayList<Integer>();
				}
				listOfIntermediateValues.add(wordKeyValue.getWordCount());
				reducerInputMap.put(wordKeyValue.getWord(),
						listOfIntermediateValues);

			}
		}

		return reducerInputMap;
	}

	/**
	 * Thread safe increase in count of listOfMapperThreadsCompleted
	 * 
	 */
	public static synchronized void updateListOfMapperThreadsCompleted() {
		countOfMapperThreadsCompleted++;
	}

	/**
	 * Thread safe addition to Mapper outputs from each Mapper
	 * 
	 * @param list
	 */
	public static synchronized void updateMapperOutputs(List<WordKeyValue> list) {
		mapperOutputs.add(list);
	}

	/**
	 * Thread safe increase in count of listOfReducerThreadsCompleted
	 * 
	 */
	public static synchronized void updateListOfReducerThreadsCompleted() {
		countOfReducerThreadsCompleted++;
	}

	/**
	 * Thread safe addition to reducer outputs from each Mapper
	 * 
	 * @param list
	 */
	public static synchronized void updateReducerOutputs(WordKeyValue wordKeyValue) {
		reducerOutputs.add(wordKeyValue);
	}


	/**
	 * This method returns a list of FileKeyValue pairs
	 * 
	 * @return
	 */
	protected static List<FileKeyValue> getInputListForProcessing() {
		List<FileKeyValue> inputData = new ArrayList<FileKeyValue>();
		// get list of files from baseFolder
		File file = new File(baseFolder);
		String[] fileNames = null;
		if (file.isDirectory()) {
			fileNames = file.list();
			if (fileNames != null && fileNames.length != 0) {
				for (int i = 0; i < fileNames.length; i++) {
					// for each file create a FileKeyValue object
					// add it to the list
					inputData.add(getFileKeyValueObject(baseFolder + "/"
							+ fileNames[i]));
				}
			}
		}
		return inputData;

	}

	/**
	 * This method takes a full file path and converts it into a FileKeyValue
	 * object and returns it back
	 * 
	 * @param string
	 * @return
	 */
	protected static FileKeyValue getFileKeyValueObject(String fileName) {
		FileKeyValue fileKeyValue = null;
		try {

			FileReader fileReader = new FileReader(fileName);
			fileKeyValue = new FileKeyValue(fileName, tokenize(fileReader));
		} catch (FileNotFoundException fnfe) {
			System.out.println("Error reading file " + fileName);
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			System.out.println("Error reading file " + fileName);
			ioe.printStackTrace();
		}
		return fileKeyValue;
	}

	/**
	 * This method converts the file contents into a list of words
	 * 
	 * @param fileReader
	 * @throws IOException
	 */
	protected static List<String> tokenize(FileReader fileReader)
			throws IOException {
		// Tokenizer Algorithm
		// 1)get Char by Char
		// 2)keep appending it to a String till we get a Space or a Tab
		// 3)add the word to the String array
		// 4)skip whitespace and start again from Step 1
		StringBuffer word = new StringBuffer();
		List<String> listOfWords = new ArrayList<String>();
		int charReadInt;
		char charRead;
		boolean inSkippingWhiteSpaceMode = false;
		while ((charReadInt = fileReader.read()) != -1) {
			charRead = (char) charReadInt;
			if (charRead != ' ' && charRead != '\t' && charRead != '\n'
					&& charRead != '\r') {
				word.append(charRead);
				inSkippingWhiteSpaceMode = false;
			} else if (!inSkippingWhiteSpaceMode) {
				inSkippingWhiteSpaceMode = true;
				listOfWords.add(word.toString());
				word = new StringBuffer();
			}
		}
		// add the last word
		if (word.toString() != "") {
			listOfWords.add(word.toString());
		}
		return listOfWords;
	}

}
