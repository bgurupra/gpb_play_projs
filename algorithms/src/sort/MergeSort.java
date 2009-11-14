package sort;

import utils.Utils;

public class MergeSort {
	
	public static void main(String[] args){
		
		int[] inputArray = {4,3,1,-1,89,45,22,199};
		Utils.printArray(mergeSort(inputArray));
		
	}
	
	public static int[] mergeSort(int[] array){
		if(array.length <= 1){
			return array;
		}
		int middle = array.length/2;
		int[] leftArray = new int[middle],rightArray = new int[array.length - middle],results;
		for(int j = 0; j < middle;j++){
			leftArray[j] = array[j];
		}

		for(int j = middle; j < array.length;j++){
			rightArray[j-middle] = array[j];
		}
		leftArray = mergeSort(leftArray);
		rightArray = mergeSort(rightArray);
		
		results  = merge(leftArray, rightArray);
		return results;
	}

	public static int[] merge(int[] a, int[] b){
		int[] results = new int[a.length+b.length];
		int aPointer = 0, bPointer = 0;
		
		for(int i = 0; i < (a.length+b.length);i++){

			if(aPointer <a.length && bPointer >= b.length){
				results[i] = a[aPointer];
				aPointer++;
			}else if(aPointer >= a.length && bPointer < b.length){
				results[i] = b[bPointer];
				bPointer++;
			}
			if( aPointer < a.length && bPointer < b.length && a[aPointer] < b[bPointer]){
				results[i] = a[aPointer];
				aPointer++;
			}else if(aPointer < a.length && bPointer < b.length) {
				results[i] = b[bPointer];
				bPointer++;
			}
		}
		return results;
	}

}
