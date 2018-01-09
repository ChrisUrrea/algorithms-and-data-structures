package project5;

import java.util.Arrays;

public class Sorts {

	public void mergeSort(int[] array) {
		recMergeSort(array, 0, array.length -1);
	}
	
	private void recMergeSort(int[] array, int start, int last) {
		int mid = start + last / 2;
		recMergeSort(array, start, mid);
		recMergeSort(array, mid+1, last);
		finalMerge(array, start, mid, last);
	}
	
	private void finalMerge(int[] array, int start, int mid, int last) {
		int[] temp = new int[last-start + 1];
		int i=0; int j=0; int k=0;
		
		while(i < mid-start && j < last- mid) {
			int next = (array[start + i] < array[mid + j]) ? 
				array[start + i++] : array[mid + j++];
				temp[k++] = next;
		}
		
		while(j<last-mid) 
			temp[k++] = array[mid + j++];
		while(i<mid-start)
			temp[k++] = array[start + i++];
		
		int count =0;
		for (int n = start; n <= last; n++) {
            array[n] = temp[count++]; 
        }
		
	}
	
	public void quickSort(int[] array) {
		recQuickSort(array, 0, array.length -1);
	}
	
	private void recQuickSort(int[] array, int start, int last) {
		int pivot = (start + last) / 2;
		int counter = start;
		//swap(pivot, last)
		for(int i=start; i < last; i++)	{
			if (array[i] < array[last]) {
				//swap(i, last)
				counter++;
			}
		}
		//swap(counter, last)
		recQuickSort(array, start, counter-1);
		recQuickSort(array, counter+1, last);
	}
	
	public static double powers(int n, double x) {
		double total = x;
		for(int i=1; i< n;i++)
			total *= x;
		return total;
	}
	
	public static double recPowers(int n, double x) {
		if(n == 0)
			return 1;
		double total = x;
		total *= recPowers(n-1, x);
		return total;
	}
	
	public static void main(String[] args) {
		System.out.println(recPowers(5, 2));
	}
}
