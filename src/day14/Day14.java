package day14;

import java.util.ArrayList;
import java.util.List;

public class Day14 {
	String input = "635041";

	int fixIndex(int index, int length) {
		return index >= length ? index % length : index;
	}

	void star1() {
		int inputInt = Integer.valueOf(input);
		List<Integer> recipes = new ArrayList<>(List.of(3,7));
		int index1 = 0;
		int index2 = 1;

		while (recipes.size() < inputInt + 10) {
			int sum = recipes.get(index1) + recipes.get(index2);
			if (sum >= 10) {
				recipes.add(1);
				recipes.add(sum - 10);
			} else {
				recipes.add(sum);
			}

			index1 = fixIndex(index1 + recipes.get(index1) + 1, recipes.size());
			index2 = fixIndex(index2 + recipes.get(index2) + 1, recipes.size());
		}

		recipes.stream().skip(inputInt).limit(10).forEach(System.out::print);
		System.out.println();
	}

	boolean checkLast(List<Integer> list) {
		if (list.size() < input.length()) return false;

		String last = list
				.subList(list.size() - input.length(), list.size())
				.stream()
				.map(String::valueOf)
				.reduce((acc, a) -> acc += a)
				.get();

		return input.equals(last);
	}

	void star2() {
		List<Integer> recipes = new ArrayList<>(List.of(3,7));
		int index1 = 0;
		int index2 = 1;

		while (true) {
			int sum = recipes.get(index1) + recipes.get(index2);
			if (sum >= 10) {
				recipes.add(1);
				if (checkLast(recipes)) break;
				recipes.add(sum - 10);
			} else {
				recipes.add(sum);
			}
			if (checkLast(recipes)) break;

			index1 = fixIndex(index1 + recipes.get(index1) + 1, recipes.size());
			index2 = fixIndex(index2 + recipes.get(index2) + 1, recipes.size());
		}

		System.out.println(recipes.size() - input.length());
	}

	void run() {
		star1();
		star2();
	}
	public static void main(String[] args) {
		Day14 day = new Day14();
		day.run();
	}
}
