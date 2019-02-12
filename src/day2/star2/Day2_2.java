package day2.star2;

import utils.Reader;

import java.io.BufferedReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day2_2 {
	static String common(String a, String b) {
		StringBuilder result = new StringBuilder();
		for (int i = 0; i< a.length(); i++) {
			if (a.charAt(i) == b.charAt(i)) {
				result.append(a.charAt(i));
			}
		}
		return result.toString();
	}

	void run() {
		try (BufferedReader reader = Reader.getReader(getClass().getResourceAsStream("../input.txt"))) {
			List<String> lines = reader.lines().collect(Collectors.toList());

			for (int i = 0; i< lines.size() - 1; i++) {
				for (int j = i + 1; j < lines.size(); j++) {
					String r = common(lines.get(i), lines.get(j));
					if (r.length() == 25) {
						System.out.println(r);
						return;
					}
				}
			}

		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
	}

	public static void main(String[] args) {
		Day2_2 day = new Day2_2();
		day.run();
	}
}
