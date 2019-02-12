package day6.star2;

import utils.Reader;

import java.io.BufferedReader;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

class Point {
	int x;
	int y;

	Point(String point) {
		String[] p = point.strip().split(", ");
		x = Integer.parseInt(p[0]);
		y = Integer.parseInt(p[1]);
	}
}

public class Day6_2 {

	int getDistance(Point p, int x, int y) {
		return Math.abs(p.x - x) + Math.abs(p.y - y);
	}

	void run() {
		try (BufferedReader reader = Reader.getReader(getClass().getResourceAsStream("../input.txt"))) {

			List<Point> points = reader.lines().map(Point::new).collect(Collectors.toList());

			int left = points.stream().min(Comparator.comparingInt(p -> p.x)).get().x;
			int top = points.stream().min(Comparator.comparingInt(p -> p.y)).get().y;
			int right = points.stream().max(Comparator.comparingInt(p -> p.x)).get().x;
			int bottom = points.stream().max(Comparator.comparingInt(p -> p.y)).get().y;

			int safeArea = 0;
			int limit = 10000;
			for (int i = top; i <= bottom; i++) {
				for (int j = left; j <= right; j++) {
					int sum = 0;

					for (Point point : points) {
						sum += getDistance(point, j, i);
					}

					if (sum < limit) {
						safeArea ++;
					}
				}
			}

			System.out.println(safeArea);

		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
	}

	public static void main(String[] args) {
		Day6_2 day = new Day6_2();
		day.run();
	}
}
