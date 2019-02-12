package day11;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day11 {

	int serial;
	final Map<String, Integer> map = new HashMap<>();

	String getKey(int x, int y) {
		return x + "," + y;
	}

	int getPower(int x, int y) {
		String key = getKey(x, y);

		if (map.containsKey(key)) {
			return map.get(key);
		} else {
			int rackId = x + 10;
			int value = ((((rackId * y) + serial) * rackId / 100) % 10) - 5;
			synchronized (map) {
				map.put(key, value);
			}
			return value;
		}
	}

	void star1() {
		int maxValue = 0;
		String maxKey = "";

		for (int i = 1; i < 299; i++) {
			for (int j = 1; j < 299; j++) {
				int sum = 0;
				for (int y = 0; y < 3; y++) {
					for (int x = 0; x < 3; x++) {
						sum += getPower(j + x, i + y);
					}
				}
				if (sum > maxValue) {
					maxValue = sum;
					maxKey = getKey(j, i);
				}
			}
		}

		System.out.println(maxKey);
	}

	class Partial {
		int value;
		String key;

		Partial(String key, int value) {
			this.key = key;
			this.value = value;
		}
	}

	Partial getPartial(int i, int j) {
		int maxSize = 301 - Math.max(i, j);
		int sum = 0;
		Partial result = new Partial("", 0);
		for (int size = 1; size <= maxSize; size++) {
			for (int x = 0; x < size - 1; x++) {
				sum += getPower(j + x, i + size - 1);
			}
			for (int y = 0; y < size - 1; y++) {
				sum += getPower(j + size - 1, i + y);
			}
			sum += getPower(j + size - 1, i + size - 1);

			if (sum > result.value) {
				result.value = sum;
				result.key = getKey(j, i) + "," + size;
			}
		}

		return result;
	}


	void star2() {
		long start = System.currentTimeMillis();
		Partial max = new Partial("", 0);

		for (int i = 1; i <= 300; i++) {
			for (int j = 1; j <= 300; j++) {
				Partial p = getPartial(i, j);
				max = p.value > max.value ? p : max;
			}
		}

		System.out.println("Serial result: [ " + max.value + " ] " + max.key);
		System.out.println("Elapsed time: " + ((System.currentTimeMillis() - start)/1000));
	}

	void star2Parallel() {
		long start = System.currentTimeMillis();
		AtomicInteger iAtomic = new AtomicInteger();
		Partial max = new Partial("", 0);

		for (int ii = 1; ii <= 300; ii++) {
			iAtomic.set(ii);
			max = Stream.concat(Stream.of(max),
					IntStream.range(1, 301)
							.parallel()
							.mapToObj( j -> getPartial(iAtomic.get(),j))
			).reduce((a, b) -> a.value > b.value ? a : b).get();
		}
		System.out.println("Serial result: [ " + max.value + " ] " + max.key);
		System.out.println("Elapsed time: " + ((System.currentTimeMillis() - start)/1000));
	}

	void run() {

		this.serial = 8979;

		star1();
		star2();
		star2Parallel();

	}

	public static void main(String[] args) {
		Day11 day = new Day11();
		day.run();
	}
}
