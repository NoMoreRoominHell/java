package com.accumulate.core.arithmetic;

import java.util.Arrays;

import com.accumulate.core.algs4.In;
import com.accumulate.core.algs4.StdIn;
import com.accumulate.core.algs4.StdOut;

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
			// Key is in a[lo..hi] or not present.
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

	@Deprecated
	public static int rank(int key, int[] a) {
		return indexOf(a, key);
	}

	public static void main(String[] args) {
		String path = BinarySearch.class.getClassLoader().getResource("tinyT.txt").getFile();

		In in = new In(path);
		int[] whitelist = in.readAllInts();

		Arrays.sort(whitelist);

		while (!StdIn.isEmpty()) {
			int key = StdIn.readInt();
			if (BinarySearch.indexOf(whitelist, key) == -1)
				StdOut.println(key);
		}
	}

}
