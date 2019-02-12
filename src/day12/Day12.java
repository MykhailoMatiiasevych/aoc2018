package day12;

import utils.Reader;

import java.io.BufferedReader;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

class Snapshot {
	long offset;
	long iteration;

	public Snapshot(long offset, long iteration) {
		this.offset = offset;
		this.iteration = iteration;
	}
}

public class Day12 {
	String replace(String s) {
		return s.replace('.','0').replace('#', '1');
	}

	BigInteger toInt(String s) {
		return new BigInteger(s,2);
	}

	String getEmpty(int size) {
		String empty = "";
		while (size > 0) {
			empty += "0";
			size--;
		}
		return empty;
	}

	void run() {

		try (BufferedReader reader = Reader.getReader(getClass().getResourceAsStream("input.txt"))) {
			String state = replace(reader.readLine().trim().substring(15));
			reader.readLine();
			Map<BigInteger, String> transform = Map.ofEntries(
					reader
							.lines()
							.map(s -> s.split(" => "))
							.map(a -> Map.entry(toInt(replace(a[0])), replace(a[1])))
							.collect(Collectors.toList())
							.<Entry<BigInteger, String>>toArray(new Entry[0])
			);

			long startFrom = 0;
			Map<BigInteger, Snapshot> map = new HashMap<>();

			// Star 1
			// int iteration = 20;

			// Star 2
			long iteration = 50000000000L;

			while (iteration > 0) {
				StringBuilder newState = new StringBuilder();
				for (int i = -4; i < state.length() + 1; i++) {
					String sub = state.substring(Math.max(0, i) , Math.min(state.length(), i+5)) + getEmpty(i + 5 - state.length());
					newState.append(transform.getOrDefault(toInt(sub), "0"));
				}
				newState.append(transform.getOrDefault(0, "0"));
				startFrom -= 2;
				state = newState.toString();
				int first = state.indexOf('1');
				int last = state.lastIndexOf('1');
				startFrom += first;

				iteration--;
				state = state.substring(first, last + 1);
				BigInteger stateInt = toInt(state);
				if (map.containsKey(stateInt)) {
					Snapshot shot =  map.get(stateInt);
					long iterd = shot.iteration - iteration;
					long offsetd = startFrom - shot.offset;
					long full = iteration / iterd;
					iteration -= iterd * full;
					startFrom += offsetd * full;
				} else {
					map.put(stateInt, new Snapshot(startFrom, iteration));
				}

			}

			long result = 0;
			for (Character c : state.toCharArray()) {
				if (c == '1') {
					result += startFrom;
				}
				startFrom ++;
			}

			System.out.println(result);
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}

	}

	public static void main(String[] args) {
		Day12 day = new Day12();
		day.run();
	}
}
