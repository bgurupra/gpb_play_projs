package stack;

import utils.Utils;

public class StockSpanAlgorithm {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//Take the value of array[index] and then traverse the array from (index-1) to 0 counting
		//the number of elements which are less than or equal to array[index].Stop the counting
		//when you find an element which is greater than array[index].The count does not include array[index] element
		//or the element that is greater than array[index] for example if the array is 10,1,2,3,6,4,5 , the span
		//of element with value 5 is 1, span of element with value 4 is 0 and span of element of value 3 is 2.
		//The span is -1,if no element is found from (index-1) to 0 which is greater than array[index] value
		//the span of the first element is always -1
		
		int[] dailyStockPrices = {0,5,2,3,4,5};
		int[] stockSpans = {-1,-1,-1,-1,-1,-1};
		//straightforward implementation which is O(n**2)		
		basicStockSpanAlgo(dailyStockPrices, stockSpans);

		//a more efficient implementation using Stacks O(n)
		efficientStockSpanAlgo(dailyStockPrices, stockSpans);
			
	}

	protected static void basicStockSpanAlgo(int[] dailyStockPrices, int[] stockSpans) {
		for(int i = 1; i< dailyStockPrices.length; i++){
			int j = i-1;
			for(; j >=0 && dailyStockPrices[j] <= dailyStockPrices[i] ; j--);
			if(j< 0){
				stockSpans[i] = -1;
			}else{
				stockSpans[i]=i-j-1;
			}
		}
		Utils.printArray(stockSpans);
	}

	protected static void efficientStockSpanAlgo(int[] dailyStockPrices, int[] stockSpans) {
		try{
		Stack myStack = new MyStack();
		int topValue ;
		for(int i = 0; i < dailyStockPrices.length; i++){

			boolean done = false;
			while(!myStack.isEmpty() && !done){
				topValue = (Integer)myStack.top();
				if(dailyStockPrices[i] >= dailyStockPrices[topValue]){
					myStack.pop();
				}else{
					done = true;
				}
			}

			if(myStack.isEmpty()){
				stockSpans[i] = -1;
			}else{
				stockSpans[i] = i - (Integer)myStack.top() -1;
			}
			myStack.push(i);
		}
		Utils.printArray(stockSpans);
		}catch(Exception e){
			e.printStackTrace();
			
		}
	}

}
