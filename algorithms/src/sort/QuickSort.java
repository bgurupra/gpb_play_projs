package sort;

import utils.Utils;

public class QuickSort {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		int[] array = {1,4,3,10,2,5,-1,5};
		Utils.printArray(array);
		quickSort(array, 0, array.length);
		Utils.printArray(array);

	}
	
	
	protected static int partition(int[] array, int p, int q){
		int pivot = array[p];
		int i = p;
		int temp;
		for(int j = p+1; j < q;j++){
			if(array[j] <= pivot){
				i++;
				temp = array[j];
				array[j]= array[i];
				array[i]=temp;
			}
		}
		temp = array[i];
		array[i]= array[p];
		array[p]=temp;
		System.out.println("Pivot = "+pivot);
		Utils.printArray(array);
		return i;
	}
	
	protected static void quickSort(int[] array, int p, int q){
		if(p < q){
			int r = partition(array, p, q);
			quickSort(array, p, r-1);
			quickSort(array,r+1,q);
		}
	}

}
