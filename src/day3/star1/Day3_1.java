package day3.star1;

import utils.Reader;

import java.io.BufferedReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3_1 {
	final int CLAIMED = 1;
	final int OVERLAPPED = 2;

	Pattern regex = Pattern.compile("#\\d+ @ (\\d+),(\\d+): (\\d+)x(\\d+)");

	int apply(int[][] map, String claim) {
		int newOverlaps = 0;

		Matcher m = regex.matcher(claim);
		if (m.matches()) {
			int z = 1;
			int left = Integer.parseInt(m.group(z++));
			int top = Integer.parseInt(m.group(z++));
			int width = Integer.parseInt(m.group(z++));
			int height = Integer.parseInt(m.group(z));

			for (int i = left; i < left + width; i++) {
				for (int j = top; j < top + height; j++) {
					switch (map[i][j]) {
						case OVERLAPPED:
							break;
						case CLAIMED:
							map[i][j] = OVERLAPPED;
							newOverlaps++;
							break;
						default:
							map[i][j] = CLAIMED;
							break;
					}
				}
			}


		}

		return newOverlaps;
	}

	void run() {
		try (BufferedReader reader = Reader.getReader(getClass().getResourceAsStream("../input.txt"))) {
			int[][] map = new int[1000][1000];
			int sum = reader.lines().mapToInt(s -> apply(map, s)).sum();
			System.out.println(sum);

		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
	}

	public static void main(String[] args) {
		Day3_1 day = new Day3_1();
		day.run();
	}
}
