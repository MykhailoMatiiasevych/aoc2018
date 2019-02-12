package day15;

import utils.Reader;

import java.io.BufferedReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

interface CHARS {
	char EMPTY = '.';
	char ELF = 'E';
	char GOBLIN = 'G';
	char WALL = '#';
}

class Point implements Comparable<Point> {
	int x;
	int y;

	Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public int compareTo(Point o) {
		return y == o.y ? x - o.x : y - o.y;
	}

	public boolean inRange(Point o) {
		return (x == o.x && Math.abs(y - o.y) == 1)
				|| (y == o.y && Math.abs(x - o.x) == 1);
	}

	Point ifEmpty(int x, int y, char[][] map) {
		return y >= 0 && y < map.length
				&& x >= 0 && x < map[y].length
				&& map[y][x] == CHARS.EMPTY ? new Point(x, y) : null;

	}

	Point top(char[][] map) {
		return ifEmpty(x, y-1, map);
	}

	Point bottom(char[][] map) {
		return ifEmpty(x, y+1, map);
	}

	Point left(char[][] map) {
		return ifEmpty(x - 1, y, map);
	}

	Point right(char[][] map) {
		return ifEmpty(x + 1, y, map);
	}

	List<Point> getInRange(char[][] map) {
		return Stream.of(this.top(map), this.left(map), this.right(map), this.bottom(map)).filter(Objects::nonNull).collect(Collectors.toList());
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (!(obj instanceof Point)) return false;
		Point o = (Point) obj;
		return o.x == x && o.y == y;
	}

	@Override
	public String toString() {
		return x + "_" + y;
	}
}

class Path implements Comparable<Path>{
	Point current;
	Point root;
	int steps = 0;

	Path(Point curent, Point root) {
		this.current = curent;
		this.root = root;
	}

	Path(Point point) {
		this(point, point);
	}

	@Override
	public int compareTo(Path o) {
		return steps == o.steps ? root.compareTo(o.root) : steps - o.steps;
	}
}

abstract class Creature implements Comparable<Creature> {
	int power = 3;
	int health = 200;
	Point point;
	char mapChar;

	Creature(int x, int y) {
		point = new Point(x, y);
	}

	boolean isAlive() {
		return health > 0;
	}

	boolean isEnemy(Creature other) {
		return !this.getClass().equals(other.getClass());
	}

	void move(Point to, char[][] map) {
		map[point.y][point.x] = CHARS.EMPTY;
		point = to;
		map[point.y][point.x] = mapChar;
	}

	@Override
	public int compareTo(Creature o) {
		return point.compareTo(o.point);
	}
}

class Elf extends Creature {
	Elf(int x, int y) {
		super(x, y);
		mapChar = CHARS.ELF;
	}
}

class Goblin extends Creature {
	Goblin(int x, int y) {
		super(x, y);
		mapChar = CHARS.ELF;
	}
}

public class Day15 {
	Creature create(char c, int x, int y) {
		if (c == CHARS.ELF){
			return new Elf(x, y);
		} else if (c == CHARS.GOBLIN) {
			return new Goblin(x, y);
		}
		return null;
	}

	// Battle is over if only one kind of creatures remains
	boolean isBattleOver(Collection<Creature> creatures) {
		Creature first = creatures.iterator().next();
		return creatures.stream().noneMatch(first::isEnemy);
	}

	Stream<Creature> enemies(Creature one, Collection<Creature> creatures) {
		return creatures.stream().filter(one::isEnemy);
	}

	// Return enemy creature that current creature in range with, sorted
	Creature enemyInRange(Creature one, Collection<Creature> creatures) {
		return enemies(one, creatures).filter(enemy -> one.point.inRange(enemy.point)).sorted().findFirst().orElse(null);
	}

	// Return all points that in range with enemies
	Set<Point> targets(Creature one, Collection<Creature> creatures, char[][] map) {
		return enemies(one, creatures).flatMap(enemy -> enemy.point.getInRange(map).stream()).collect(Collectors.toSet());
	}

	void run() {
		try (BufferedReader reader = Reader.getReader(getClass().getResourceAsStream("../input.txt"))) {
			char[][] map = reader.lines().map(String::toCharArray).toArray(char[][]::new);
			Map<Point, Creature> creatures = new TreeMap<>();

			for (int y = 0; y < map.length; y++) {
				for (int x = 0; x < map[y].length; x++) {
					Optional.ofNullable(create(map[y][x], x, y)).ifPresent(one -> {
						creatures.put(one.point, one);
					});
				}
			}

			int round = 0;
			boolean battleOver = isBattleOver(creatures.values());
			while (!battleOver) {

				int i = 0;
				while (i < creatures.values().size() && !battleOver) {
					Creature one = creatures.values().toArray(new Creature[0])[i];

					Creature another = enemyInRange(one, creatures.values());
					if (another == null) {
						// try move
						List<Path> wave = one.point.getInRange(map).stream().map(Path::new).collect(Collectors.toList());
						Set<Point> visited = wave.stream().map(path -> path.current).collect(Collectors.toSet());
						visited.add(one.point);
						List<Path> matched = new ArrayList<>();
						Set<Point> targets = targets(one, creatures.values(), map);

						while (matched.isEmpty() && !wave.isEmpty()) {
							List<Path> nextWave = new ArrayList<>();
							for (Path path : wave) {
								path.current.getInRange(map).forEach(point -> {
									if (!visited.contains(point)) {

										if (targets.contains(point)) {

										}
									}
								});

							}

						}

						if (!matched.isEmpty()) {
							matched.sort(Comparator.naturalOrder());
							one.move(matched.get(0).root, map);
							// Find new enemy in range
							another = enemyInRange(one, creatures.values());
						}
					}

					if (another != null) {
						// try attack
						another.health -= one.power;
						if (!another.isAlive()) {
							map[another.point.y][another.point.x] = '.';
							creatures.remove(another.point);

							// Kill previous creature
							if (creatures.values().toArray(new Creature[0])[i] != one) {
								i--;
							}
						}

					}

					battleOver = isBattleOver(creatures.values());
					i++;
				}


				if (!battleOver) {
					// Sort creatures by inserting in the tree map
					Map<Point, Creature> sorted = new TreeMap<>();
					for (Creature one : creatures.values()) {
						sorted.put(one.point, one);
					}
					creatures.clear();
					creatures.putAll(sorted);

					round++;
				}
			}

			int totalHealth = creatures.values().stream().mapToInt(one -> one.health).sum();
			System.out.println(totalHealth * round);
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
	}

	public static void main(String[] args) {
		Day15 day = new Day15();
		day.run();
	}
}
