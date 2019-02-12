package day7;

import java.util.HashSet;
import java.util.Set;

public class Item {

	public char c;
	public Set<Character> deps;

	public Item(char c) {
		this.c = c;
		deps = new HashSet<>();
	}
}
