package day3.star2;

import utils.Reader;

import java.io.BufferedReader;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3_2 {
	Pattern regex = Pattern.compile("#(\\d+) @ (\\d+),(\\d+): (\\d+)x(\\d+)");

	void apply(Set<Integer> set, int[][] map, String claim) {

		Matcher m = regex.matcher(claim);
		if (m.matches()) {
			int z = 1;
			int id = Integer.parseInt(m.group(z++));
			int left = Integer.parseInt(m.group(z++));
			int top = Integer.parseInt(m.group(z++));
			int width = Integer.parseInt(m.group(z++));
			int height = Integer.parseInt(m.group(z));

			set.add(id);

			for (int i = left; i < left + width; i++) {
				for (int j = top; j < top + height; j++) {
					if (map[i][j] == 0) {
						map[i][j] = id;
					} else {
						set.remove(id);
						set.remove(map[i][j]);
					}
				}
			}
		}
	}

	void run() {
		try (BufferedReader reader = Reader.getReader(getClass().getResourceAsStream("../input.txt"))) {
			int[][] map = new int[1000][1000];
			Set<Integer> set = new HashSet<>();
			reader.lines().forEach(s -> apply(set, map, s));
			System.out.println(set.toArray()[0]);

		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
	}

	public static void main(String[] args) {
		Day3_2 day = new Day3_2();
		day.run();
	}
}
