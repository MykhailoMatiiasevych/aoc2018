package day7.star2;

import day7.Item;
import utils.Reader;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

class Job implements Comparable<Job> {

	char c;
	int ends;

	Job(char c, int ends) {
		this.c = c;
		this.ends = ends;
	}

	@Override
	public int compareTo(Job o) {
		return this.ends - o.ends;
	}

	public int end(Map<Character, Item> items) {
		items.values().forEach(v -> v.deps.remove(this.c));
		return this.ends;
	}
}

public class Day7_2 {

	Item add(Map<Character, Item> items, char c) {
		Item i = items.get(c);
		if (i == null) {
			i = new Item(c);
			items.put(c, i);
		}
		return i;
	}

	Item findItem(Map<Character, Item> items) {
		return items.values().stream().reduce((a, b) -> {
			if (a.deps.size() == 0 && b.deps.size() == 0) {
				return a.c < b.c ? a : b;
			}
			return a.deps.size() < b.deps.size() ? a : b;
		}).get();
	}

	void run() {
		try (BufferedReader reader = Reader.getReader(getClass().getResourceAsStream("../input.txt"))) {

			Map<Character, Item> items = new HashMap<>();

			reader.lines()
					.map(s -> s.split(" "))
					.forEach(a -> {
						add(items, a[7].charAt(0)).deps.add(a[1].charAt(0));
						add(items, a[1].charAt(0));
					});

			int baseDuration = 60;
			int maxJobs = 5;
			Queue<Job> jobs = new PriorityQueue<>();
			int seconds = 0;

			while (items.size() > 0) {
				Item i = findItem(items);
				if (i.deps.size() == 0) {

					if (jobs.size() == maxJobs) {
						seconds = jobs.poll().end(items);
					}
					items.remove(i.c);

					jobs.add(new Job(i.c, seconds + baseDuration + i.c - 'A' + 1));
				} else {
					seconds = jobs.poll().end(items);

				}
			}

			while (jobs.size() > 0) {
				seconds = jobs.poll().ends;
			}


			System.out.println(seconds);
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
	}

	public static void main(String[] args) {
		Day7_2 day = new Day7_2();
		day.run();
	}
}
