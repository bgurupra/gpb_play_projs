package stack;

public class MyStack implements Stack {

	public static final int TIGHT_STRATEGY = 1;

	public static final int GROWTH_STRATEGY = 2;

	protected final int TIGHT_STRATEGY_GROWTH_CONSTANT = 50;

	protected int initialStackSize = 1024;

	protected Object[] stack = new Object[1024];

	protected int stackIndex = -1;

	protected int sizeIncreaseStrategy = GROWTH_STRATEGY;

	public MyStack() {
	}

	public MyStack(int lInitialsize) {
		if (lInitialsize <= 0) {
			this.initialStackSize = lInitialsize;
		}
	}
	
	public MyStack(int lInitialsize, int lSizeIncreaseStrategy) {
		if (lInitialsize <= 0) {
			this.initialStackSize = lInitialsize;
		}
		if(lSizeIncreaseStrategy == TIGHT_STRATEGY ||lSizeIncreaseStrategy == GROWTH_STRATEGY  ){
			this.sizeIncreaseStrategy = lSizeIncreaseStrategy;
		}
	}

	public Object pop() throws StackEmptyException {
		Object o = null;
		if (stackIndex == -1) {
			throw new StackEmptyException();
		}
		o = stack[stackIndex];
		stack[stackIndex--] = null;
		return o;
	}

	public void push(Object o) throws StackOverFlowException {
		if (stackIndex == (stack.length - 1)) {
			increaseStackSize();
		}
		stack[++stackIndex] = o;

	}

	public Object top() throws StackEmptyException {
		if (stackIndex == -1) {
			throw new StackEmptyException();
		}
		return stack[stackIndex];
	}

	public boolean isEmpty(){
		return stackIndex == -1;
	}
	
	protected void increaseStackSize() throws StackOverFlowException {
		try {
			Object[] newStack = null;
			if (sizeIncreaseStrategy == TIGHT_STRATEGY) {
				//O(n**n/c)
				newStack = new Object[stack.length
						+ TIGHT_STRATEGY_GROWTH_CONSTANT];
			} else {
				//O(n)
				newStack = new Object[stack.length * 2];
			}
			copyStack(newStack);
			stack = newStack;
		} catch (OutOfMemoryError oome) {
			throw new StackOverFlowException();
		}

	}

	
	protected void copyStack(Object[] newStack) {
		for (int i = 0; i < stack.length; i++) {
			newStack[i] = stack[i];
		}
	}

}
