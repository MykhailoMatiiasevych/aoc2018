package day10;

import utils.Reader;

import java.io.BufferedReader;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

class Point {
	int x;
	int y;
	int dx;
	int dy;

	Pattern p = Pattern.compile("^position=<\\s?([\\d-]+),\\s+([\\d-]+)> velocity=<\\s?([\\d-]+),\\s+([\\d-]+)>$");

	Point(String s) {
		Matcher m = p.matcher(s);
		m.matches();
		int z = 1;
		x = Integer.valueOf(m.group(z++));
		y = Integer.valueOf(m.group(z++));
		dx = Integer.valueOf(m.group(z++));
		dy = Integer.valueOf(m.group(z++));
	}

	Point(int x, int y, int dx, int dy) {
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
	}

	Point move() {
		return new Point(x + dx, y + dy, dx, dy);
	}

	String key() {
		return x + "," + y;
	}
}

class Area {
	long top = Long.MAX_VALUE;
	long bottom = Long.MIN_VALUE;
	long left = Long.MAX_VALUE;
	long right = Long.MIN_VALUE;

	long area() {
		return Math.abs((top - bottom) * (left - right));
	}
}

public class Day10_1_2 {

	List<Point> moveAll(List<Point> points) {
		return points.stream().map(Point::move).collect(Collectors.toList());
	}

	Area getArea(List<Point> points) {
		Area area = new Area();
		for (Point point : points) {
			area.top = area.top > point.y ? point.y : area.top;
			area.bottom = area.bottom < point.y ? point.y : area.bottom;
			area.left = area.left > point.x ? point.x : area.left;
			area.right = area.right < point.x ? point.x : area.right;
		}
		return area;
	}

	void print(List<Point> points) {
		Area area = getArea(points);
		Set<String> set = points.stream().map(Point::key).collect(Collectors.toSet());
		for (long i = area.top; i < area.bottom + 1; i++) {
			for (long j = area.left; j < area.right + 1; j++) {
				if (set.contains(j + "," + i)) {
					System.out.print("#");
				} else {
					System.out.print(".");
				}
			}
			System.out.println();
		}
		System.out.println();
	}

	void run() {
		try (BufferedReader reader = Reader.getReader(getClass().getResourceAsStream("input.txt"))) {

			List<Point> points = reader.lines().map(Point::new).collect(Collectors.toList());

			int count = 0;
			Area area = getArea(points);
			while (true) {
				List<Point> moved = moveAll(points);
				Area newArea = getArea(moved);
				if (newArea.area() > area.area()) {
					// Star 1
					print(points);
					// Star 2
					System.out.println(count);
					break;
				} else {
					count ++;
					area = newArea;
					points = moved;
				}

			}

		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
	}

	public static void main(String[] args) {
		Day10_1_2 day = new Day10_1_2();
		day.run();
	}
}
