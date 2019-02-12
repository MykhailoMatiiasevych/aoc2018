package day9;

import utils.Reader;

import java.io.BufferedReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day9_1_2 {

	void run() {
		try (BufferedReader reader = Reader.getReader(getClass().getResourceAsStream("input.txt"))) {

			Pattern pattern = Pattern.compile("(\\d+)[^\\d]*(\\d+)[^\\d]*");
			Matcher matcher = pattern.matcher(reader.readLine());
			matcher.matches();
			int players = Integer.valueOf(matcher.group(1));
			// Star 1
			// int marbles = Integer.valueOf(matcher.group(2));
			// Star 2
			int marbles = Integer.valueOf(matcher.group(2)) * 100;


			long[] scores = new long[players];
			long maxScore = 0;
			Circle circle = new Circle();
			circle.add(0);

			for (int i = 1; i <= marbles; i++) {
				if (i % 23 == 0) {
					circle.move(-7);
					int player = i % players;
					scores[player] += i + circle.remove();
					maxScore = scores[player] > maxScore ? scores[player] : maxScore;
				} else {
					circle.move(1);
					circle.add(i);
				}
			}

			System.out.println(maxScore);

		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
	}

	public static void main(String[] args) {
		Day9_1_2 day = new Day9_1_2();
		day.run();
	}
}
