package day8.star1;

import utils.Reader;

import java.io.BufferedReader;
import java.util.Arrays;
import java.util.Iterator;

public class Day8_1 {

	int getMetaSum(Iterator<Integer> itr) {
		int children = itr.next();
		int meta = itr.next();
		int sum = 0;
		for (int i = 0; i< children; i++) {
			sum += getMetaSum(itr);
		}
		for (int j = 0; j< meta; j++) {
			sum += itr.next();
		}

		return sum;
	}

	void run() {
		try (BufferedReader reader = Reader.getReader(getClass().getResourceAsStream("../input.txt"))) {

			Iterator<Integer> itr =  Arrays
					.stream(reader.readLine().trim().split(" "))
					.mapToInt(Integer::valueOf)
					.iterator();

			System.out.println(getMetaSum(itr));

		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
	}

	public static void main(String[] args) {
		Day8_1 day = new Day8_1();
		day.run();
	}
}
