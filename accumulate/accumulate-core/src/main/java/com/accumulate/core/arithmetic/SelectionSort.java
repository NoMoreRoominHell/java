package com.accumulate.core.arithmetic;

public class SelectionSort {

	public static void sort(int a[]) {
		for (int a_v : a) {
			System.out.print(a_v + " ");
		}
		System.out.println();
		
		int minIndex = 0;
		for (int i = 0; i < a.length; i++) {
			minIndex = i;
			for (int j = i + 1; j < a.length; j++) {
				if (a[minIndex] > a[j]) {
					minIndex = j;
				}
			}

			int tem = a[i];
			a[i] = a[minIndex];
			a[minIndex] = tem;

			for (int a_v : a) {
				System.out.print(a_v + " ");
			}
			System.out.println("minIndex:" + minIndex);
		}
	}

	public static void main(String[] args) {
		int array[] = { 9, 5, 3, 7, 8, 4, 1, 3, 6, 8, 5, 24 };
		sort(array);
	}
}
