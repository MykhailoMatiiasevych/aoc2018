package day7.star1;

import day7.Item;
import utils.Reader;

import java.io.BufferedReader;
import java.util.*;

public class Day7_1 {

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
				return a.c < b.c ? a: b;
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

			while (items.size() > 0) {
				Item i = findItem(items);
				items.remove(i.c);
				System.out.print(i.c);

				items.values().forEach(v -> v.deps.remove(i.c));
			}


		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
	}

	public static void main(String[] args) {
		Day7_1 day = new Day7_1();
		day.run();
	}
}
