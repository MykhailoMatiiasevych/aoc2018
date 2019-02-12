package day4.star1;

import utils.Reader;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

class Sleep {
	int[] sleep = new int[60];
	int total = 0;
}

public class Day4_1 {

	void run() {
		Map<Integer, Sleep> map = new HashMap<>();
		AtomicInteger guard = new AtomicInteger();
		AtomicInteger sleepStart = new AtomicInteger();

		try (BufferedReader reader = Reader.getReader(getClass().getResourceAsStream("../input.txt"))) {
			reader.lines()
					.sorted()
					.map(s -> s.split(" "))
					.forEachOrdered(v -> {
						switch (v[2]) {
							case "Guard":
								guard.set(Integer.valueOf(v[3].substring(1)));
								break;
							case "falls":
								sleepStart.set(Integer.valueOf(v[1].substring(3, 5)));
								break;
							case "wakes":
								int awake = Integer.valueOf(v[1].substring(3, 5));
								Sleep sleep = map.getOrDefault(guard.get(), new Sleep());
								sleep.total += awake - sleepStart.get();
								for (int i = sleepStart.get(); i< awake; i++) {
									sleep.sleep[i]++;
								}
								map.put(guard.get(), sleep);
						}
					});

			Map.Entry<Integer, Sleep> result = map.entrySet().stream().reduce((a, b) -> a.getValue().total > b.getValue().total ? a : b).get();

			int max = 0;
			int index = 0;
			int[] sleep = result.getValue().sleep;
			for (int i = 0; i< sleep.length; i++) {
				if (sleep[i] > max) {
					max = sleep[i];
					index = i;
				}
			}
			System.out.println(result.getKey() * index);

		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
	}

	public static void main(String[] args) {
		Day4_1 day = new Day4_1();
		day.run();
	}
}
