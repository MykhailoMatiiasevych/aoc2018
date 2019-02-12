package day1.star2;

import utils.Reader;

import java.io.BufferedReader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Day1_2 {

	void run() {
		try (BufferedReader reader = Reader.getReader(getClass().getResourceAsStream("../input.txt"))) {
			List<Integer> list = reader.lines()
					.mapToInt(Integer::parseInt)
					.boxed()
					.collect(Collectors.toList());
			Set<Integer> freqs = new HashSet<>();
			int i = 0;
			int freq = 0;
			while (true) {
				freq += list.get(i);
				if (!freqs.add(freq)) {
					System.out.println(freq);
					break;
				}
				i++;
				if (i == list.size()) {
					i = 0;
				}
			}

		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
	}
	public static void main(String[] args) {
		Day1_2 day = new Day1_2();
		day.run();
	}
}
