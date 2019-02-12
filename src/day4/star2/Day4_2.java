package day4.star2;

import utils.Reader;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

class Sleep {
	int[] sleep = new int[60];
	int total = 0;
}

public class Day4_2 {

	void run() {
		Map<Integer, Sleep> map = new HashMap<>();
		AtomicInteger guard = new AtomicInteger();
		AtomicInteger sleepStart = new AtomicInteger();
		AtomicInteger maxGuardId = new AtomicInteger();
		AtomicInteger maxSleep = new AtomicInteger();
		AtomicInteger minute = new AtomicInteger();

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
									if (sleep.sleep[i] > maxSleep.get()) {
										maxSleep.set(sleep.sleep[i]);
										minute.set(i);
										maxGuardId.set(guard.get());
									}
								}
								map.put(guard.get(), sleep);


						}
					});

			System.out.println(maxGuardId.get() * minute.get());

		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
	}

	public static void main(String[] args) {
		Day4_2 day = new Day4_2();
		day.run();
	}
}
