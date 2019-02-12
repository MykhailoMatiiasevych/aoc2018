package day5.star2;

import utils.Reader;

import java.io.BufferedReader;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day5_2 {

	List<Integer> removeType(List<Integer> list, int type) {
		return list.stream().filter(i -> i != type && i != type + 32).collect(Collectors.toList());
	}

	List<Integer> react(List<Integer> list) {
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
		return list;
	}

	void run() {
		try (BufferedReader reader = Reader.getReader(getClass().getResourceAsStream("../input.txt"))) {
			List<Integer> list = reader.readLine().strip().chars().boxed().collect(Collectors.toList());
			react(list);

			int minSize = IntStream.range(65, 91)
					.map(type -> react(removeType(list, type)).size())
					.min().getAsInt();

			System.out.println(minSize);
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
	}
	public static void main(String[] args) {
		Day5_2 day = new Day5_2();
		day.run();
	}
}
