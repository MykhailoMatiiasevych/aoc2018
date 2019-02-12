package day2.star1;

import utils.Reader;

import java.io.BufferedReader;
import java.util.Arrays;
import java.util.stream.Collectors;

class Acc {
	int two;
	int three;

	Acc(int two, int three) {
		this.two = two;
		this.three = three;
	}

	static Acc add(Acc acc1, Acc acc2) {
		if (acc1 == null) {
			return acc2;
		}
		return new Acc(acc1.two + acc2.two, acc1.three + acc2.three);
	}
}

public class Day2_1 {
	void run() {
		try (BufferedReader reader = Reader.getReader(getClass().getResourceAsStream("../input.txt"))) {
			Acc acc = reader.lines()
					.map(s -> {
						int[] arr = new int[26];
						byte[] bytes = s.getBytes();
						for (byte aByte : bytes) {
							arr[aByte-97]++;
						}
						return Arrays.stream(arr).boxed().collect(Collectors.toSet());
					})
					.map(set -> new Acc(
							set.contains(2) ? 1 : 0,
							set.contains(3) ? 1 : 0
					))
					.reduce(Acc::add).orElse(new Acc(0, 0));
			System.out.println(acc.two * acc.three);
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
	}

	public static void main(String[] args) {
		Day2_1 day = new Day2_1();
		day.run();
	}
}
