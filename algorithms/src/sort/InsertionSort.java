package sort;

import utils.Utils;


public class InsertionSort {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int[] array = { 1, 3, 2, 10, 3, 2 };
		Utils.printArray(array);
		
		int value = 0;
		for (int i = 1; i < array.length; i++) {
			value = array[i];
			int j = i - 1;
			while (j >= 0 && array[j] > value) {
				array[j + 1] = array[j];
				j = j - 1;
			}
			array[j + 1] = value;

		}
		Utils.printArray(array);
	}

}
