package day9;

class Circle {

	Item current = null;
	Item first = null;

	void move(int steps) {
		while (steps != 0) {
			if (steps > 0) {
				current = current.next;
				steps --;
			} else {
				current = current.prev;
				steps ++;
			}
		}
	}

	long remove() {
		Item prev = current.prev;
		Item next = current.next;
		long value = current.value;

		prev.next = next;
		next.prev = prev;
		if (first == current) {
			first = next;
		}
		current = next;


		return value;
	}

	void add(int value) {
		Item item = new Item();
		item.value = value;

		if (current == null) {
			current = item;
			first = item;
			current.next = current;
			current.prev = current;
		} else {
			Item prev = current;
			Item next = current.next;

			current = item;
			current.next = next;
			next.prev = current;
			current.prev = prev;
			prev.next = current;
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		Item i = first;
		do {
			sb.append(i.value).append(" ");
			i = i.next;
		} while (i != first);
		return sb.toString();
	}

	class Item {
		long value;
		Item next = null;
		Item prev = null;
	}
}

