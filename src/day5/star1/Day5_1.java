package day5.star1;

import utils.Reader;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day5_1 {

	void react(List<Integer> list) {
		int index = 0;

		while (index < list.size() - 1) {
			int current = list.get(index);
			int next = list.get(index + 1);
			if (Math.abs(current - next) == 32) {
				list.remove(index);
				list.remove(index);
				index = Math.max(index - 1, 0);
			} else {
				index++;
			}
		}
	}

	void run() {
		try (BufferedReader reader = Reader.getReader(getClass().getResourceAsStream("../input.txt"))) {
			List<Integer> list = reader.readLine().strip().chars().boxed().collect(Collectors.toCollection(ArrayList::new));

			react(list);

			System.out.println(list.size());
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
	}
	public static void main(String[] args) {
		Day5_1 day = new Day5_1();
		day.run();
	}
}
