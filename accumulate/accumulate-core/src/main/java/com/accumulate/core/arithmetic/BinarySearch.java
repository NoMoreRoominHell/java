package com.accumulate.core.arithmetic;

/**
 * 二分查找
 */
public class BinarySearch {

	private BinarySearch() {
	}

	public static int indexOf(int[] a, int key) {
		int lo = 0;
		int hi = a.length - 1;
		while (lo <= hi) {
			int mid = lo + (hi - lo) / 2;
			if (key < a[mid])
				hi = mid - 1;
			else if (key > a[mid])
				lo = mid + 1;
			else
				return mid;
		}
		return -1;
	}

	public static void main(String[] args) {
		// for (int i = 0; i < 10; i++) {
		// System.out.println((int) (Math.random() * 10) + ",");
		// }
		int a[] = { 2, 3, 8, 4, 7, 0, 9, 5, 6, 8 };
		System.out.println(BinarySearch.indexOf(a, 8));

	}

}
