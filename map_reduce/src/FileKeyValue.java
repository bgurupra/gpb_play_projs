import java.util.List;

/**
 * 
 * @author bgurupra
 */
public class FileKeyValue {
	
	public FileKeyValue(String fileName, List<String> fileValue){
		setFileName(fileName);
		setFileValue(fileValue);
	}
	public String fileName = null;
	public List<String> fileValue = null;
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public List<String> getFileValue() {
		return fileValue;
	}
	public void setFileValue(List<String> fileValue) {
		this.fileValue = fileValue;
	}


}
