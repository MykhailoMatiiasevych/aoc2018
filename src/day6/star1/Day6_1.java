package day6.star1;

import utils.Reader;

import java.io.BufferedReader;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

class Point {
	int x;
	int y;
	int area;

	Point(String point) {
		String[] p = point.strip().split(", ");
		x = Integer.parseInt(p[0]);
		y = Integer.parseInt(p[1]);
		area = 0;
	}
}

public class Day6_1 {

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

			for (int i = top; i <= bottom; i++) {
				for (int j = left; j <= right; j++) {
					int min = Integer.MAX_VALUE;
					Point p = null;
					boolean isSame = false;

					for (Point point : points) {
						int distance = getDistance(point, j, i);
						if (distance == min) {
							isSame = true;
						} else if (distance < min) {
							min = distance;
							p = point;
							isSame = false;
						}
					}

					if (i == top || i == bottom || j == left || j == right) {
						// Edge goes to infinity
						p.area = Integer.MIN_VALUE;
					} else if (!isSame) {
						p.area++;
					}
				}
			}

			int result = points.stream().mapToInt(p -> p.area).max().getAsInt();
			System.out.println(result);

		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
	}

	public static void main(String[] args) {
		Day6_1 day = new Day6_1();
		day.run();
	}
}
