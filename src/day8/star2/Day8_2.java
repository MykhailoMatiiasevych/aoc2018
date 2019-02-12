package day8.star2;

import utils.Reader;

import java.io.BufferedReader;
import java.util.Arrays;
import java.util.Iterator;

public class Day8_2 {

	int getValue(Iterator<Integer> itr) {
		int children = itr.next();
		int meta = itr.next();
		int[] arr = new int[children];
		int value = 0;
		for (int i = 0; i< children; i++) {
			arr[i] = getValue(itr);
		}
		if (children > 0) {
			for (int j = 0; j < meta; j++) {
				int v = itr.next();
				if (v > 0 && v <= arr.length) {
					value += arr[v - 1];
				}
			}
		} else {
			for (int j = 0; j < meta; j++) {
				value += itr.next();
			}
		}

		return value;
	}

	void run() {
		try (BufferedReader reader = Reader.getReader(getClass().getResourceAsStream("../input.txt"))) {

			Iterator<Integer> itr =  Arrays
					.stream(reader.readLine().trim().split(" "))
					.mapToInt(Integer::valueOf)
					.iterator();

			System.out.println(getValue(itr));

		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
	}

	public static void main(String[] args) {
		Day8_2 day = new Day8_2();
		day.run();
	}
}
