package day1.star1;

import utils.Reader;

import java.io.BufferedReader;

public class Day1_1 {

	void run() {
		try (BufferedReader reader = Reader.getReader(getClass().getResourceAsStream("../input.txt"))) {
			int sum = reader.lines()
					.mapToInt(Integer::parseInt)
					.sum();
			System.out.println(sum);
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
	}
	public static void main(String[] args) {
		Day1_1 day = new Day1_1();
		day.run();
	}
}
