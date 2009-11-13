package stack;

public interface Stack {
	
	public void push(Object o) throws StackOverFlowException;
	public Object pop() throws StackEmptyException;
	public Object top() throws StackEmptyException;
	public boolean isEmpty();

}
