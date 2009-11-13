
public class WordKeyValue {
	public WordKeyValue(String word, Integer wordCount){
		setWord(word);
		setWordCount(wordCount);
	}
	private String word = null;
	private Integer wordCount = 0;
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public Integer getWordCount() {
		return wordCount;
	}
	public void setWordCount(Integer wordCount) {
		this.wordCount = wordCount;
	}

}
