package day13;

import utils.Reader;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

enum Direction {
	UP(0, -1),
	DOWN(0, 1),
	LEFT(-1, 0),
	RIGHT(1, 0);


	int dx;
	int dy;
	Direction left;
	Direction right;

	Direction(int dx, int dy) {
		this.dx = dx;
		this.dy = dy;
	}

	Direction turn(Turn turn) {
		if (turn == Turn.LEFT) {
			return this.left;
		} else if (turn == Turn.RIGHT) {
			return this.right;
		}
		return this;
	}
}

enum Turn {
	LEFT,
	STRAIGHT,
	RIGHT;

	Turn next() {
		switch (this) {
			case LEFT:
				return STRAIGHT;
			case STRAIGHT:
				return RIGHT;
			case RIGHT:
				return LEFT;
		}
		return this;
	}

}

class Cart {
	int x;
	int y;

	Direction direction;

	Turn nextTurn = Turn.LEFT;

	Cart(int x, int y, char c) {
		this.x = x;
		this.y = y;
		switch (c) {
			case '<':
				direction = Direction.LEFT;
				break;
			case '>':
				direction = Direction.RIGHT;
				break;
			case 'v':
				direction = Direction.DOWN;
				break;
			case '^':
				direction = Direction.UP;
				break;
		}
	}

	void move(char[][] map) {
		x += direction.dx;
		y += direction.dy;

		char pos = map[y][x];

		switch (pos) {
			case '+':
				direction = direction.turn(nextTurn);
				nextTurn = nextTurn.next();
				break;
			case '/':
				if (direction == Direction.UP || direction == Direction.DOWN) direction = direction.turn(Turn.RIGHT);
				else if (direction == Direction.LEFT || direction == Direction.RIGHT)
					direction = direction.turn(Turn.LEFT);
				break;
			case '\\':
				if (direction == Direction.UP || direction == Direction.DOWN) direction = direction.turn(Turn.LEFT);
				else if (direction == Direction.LEFT || direction == Direction.RIGHT)
					direction = direction.turn(Turn.RIGHT);
				break;
		}
	}

	Cart isCollide(List<Cart> carts) {
		return carts.stream().filter(cart -> {
			if (cart == this) {
				return false;
			}
			return cart.x == this.x && cart.y == this.y;
		}).findFirst().orElse(null);
	}

	@Override
	public String toString() {
		return x + "," + y;
	}
}

public class Day13 {

	Set<Character> chars = Set.of('<', '>', 'v', '^');

	void configDirection(Direction main, Direction left, Direction right) {
		main.left = left;
		main.right = right;
	}

	void star1(char[][] map, List<Cart> carts) {
		int index = 0;
		while (true) {
			Cart cart = carts.get(index);
			cart.move(map);

			if (cart.isCollide(carts) != null) {
				System.out.println(cart);
				break;
			}

			index++;
			if (index == carts.size()) {
				index = 0;
			}
		}
	}

	void star2(char[][] map, List<Cart> carts) {
		int index = 0;
		carts.sort((a,b) -> a.y == b.y ? a.x - b.x : a.y - b.y);
		while (true) {
			Cart cart = carts.get(index);
			cart.move(map);

			Cart collided = cart.isCollide(carts);
			if (collided != null) {
				int collidedIndex = carts.indexOf(collided);
				if (index < collidedIndex) {
					index -= 1;
				} else {
					index -= 2;
				}

				carts.remove(cart);
				carts.remove(collided);
			}

			index++;
			if (index >= carts.size()) {
				index = 0;
				if (carts.size() == 1) {
					System.out.println(carts.get(0));
					break;
				} else {
					carts.sort((a,b) -> a.y == b.y ? a.x - b.x : a.y - b.y);
				}
			}
		}
	}


	void run() {
		configDirection(Direction.UP, Direction.LEFT, Direction.RIGHT);
		configDirection(Direction.LEFT, Direction.DOWN, Direction.UP);
		configDirection(Direction.DOWN, Direction.RIGHT, Direction.LEFT);
		configDirection(Direction.RIGHT, Direction.UP, Direction.DOWN);

		try (BufferedReader reader = Reader.getReader(getClass().getResourceAsStream("input.txt"))) {
			char[][] map = reader.lines().map(String::toCharArray).toArray(char[][]::new);
			List<Cart> carts = new ArrayList<>();

			for (int y = 0; y < map.length; y++) {
				for (int x = 0; x < map[y].length; x++) {
					if (chars.contains(map[y][x])) {
						carts.add(new Cart(x, y, map[y][x]));
					}
				}
			}

			//star1(map, carts);
			star2(map, carts);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public static void main(String[] args) {
		Day13 day = new Day13();
		day.run();
	}
}
