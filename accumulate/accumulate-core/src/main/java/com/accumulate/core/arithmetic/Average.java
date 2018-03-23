package com.accumulate.core.arithmetic;

import com.accumulate.core.algs4.StdIn;
import com.accumulate.core.algs4.StdOut;

public class Average {

	private Average() {
	}

	public static void main(String[] args) {
		int count = 0; // number input values
		double sum = 0.0; // sum of input values

		// read data and compute statistics
		while (!StdIn.isEmpty()) {
			double value = StdIn.readDouble();
			sum += value;
			count++;
		}

		// compute the average
		double average = sum / count;

		// print results
		StdOut.println("Average is " + average);
	}

}
