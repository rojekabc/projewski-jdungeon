package pl.projewski.jdungeon.map.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import pl.projewski.jdungeon.map.AbstractMapGenerator;
import pl.projewski.jdungeon.map.GeneratedMap;
import pl.projewski.jdungeon.map.GeneratorProperty;
import pl.projewski.jdungeon.map.MapElement;
import pl.projewski.jdungeon.map.direct.Area2D;
import pl.projewski.jdungeon.map.direct.Vector2D;
import pl.projewski.jdungeon.path.ShortestLinePathFinder;

/**
 * This is created by me simple room generator.
 * 
 * @author Piotr Rojewski <rojek_abc@o2.pl>
 *
 */
@Slf4j
public class SimpleRoomGenerator extends AbstractMapGenerator {

	/**
	 * Constructor. Sets default properties of generator.
	 */
	public SimpleRoomGenerator() {
		setProperty(SimpleRoomGeneratorProperty.MAP_MINIMUM_WIDTH, "5");
		setProperty(SimpleRoomGeneratorProperty.MAP_MINIMUM_HEIGHT, "5");
		setProperty(SimpleRoomGeneratorProperty.MAP_MAXIMUM_WIDTH, "8");
		setProperty(SimpleRoomGeneratorProperty.MAP_MAXIMUM_HEIGHT, "8");
		setProperty(SimpleRoomGeneratorProperty.MAP_ROOM_AMOUNT, "5");
	}

	@Override
	public GeneratedMap generate(final int width, final int height, final long randomSeed) {
		final GeneratedMap map = new GeneratedMap(width, height);
		final Random random = new Random(randomSeed);
		int nTries = 5;

		// generate rooms
		final int roomsCnt = getAsInt(SimpleRoomGeneratorProperty.MAP_ROOM_AMOUNT, 5);
		final List<Area2D> roomList = new ArrayList<Area2D>();
		for (int i = 0; i < roomsCnt; i++) {
			nTries = 5;
			while (nTries > 0) {
				final Area2D room = generateRoom(map, random);
				if (room != null) {
					roomList.add(room);
					break;
				}
				nTries--;
			}
		}

		// check there's only one room or no one.
		if (roomList.size() < 2) {
			return map;
		}

		final ShortestLinePathFinder pathFinder = new ShortestLinePathFinder();
		final GeneratedMap path = pathFinder.findPaths(map, roomList, MapElement.ROOM.getMapValue(),
		        MapElement.WALL.getMapValue());
		map.drillPath(path);

		return map;
	}

	/**
	 * Generate a room on the map. Use random number generator from parameters.
	 * First it'll try to find a point on the map, which is not generated. From
	 * this point algorithm tries to place a room. If it's not possible it'll
	 * check move generated room to all directions to check, that there's any
	 * place for such room. If found more than one place, algorithm would random
	 * one of move direction and place there the generated room.
	 * 
	 * @param map
	 *            the map, where room will be generated
	 * @param random
	 *            random number generator
	 * @return generated room
	 */
	private Area2D generateRoom(final GeneratedMap map, final Random random) {
		int nTries = 5;
		// random initialPoint
		int x = 0;
		int y = 0;
		while (nTries > 0) {
			x = random.nextInt(map.width);
			y = random.nextInt(map.height);
			if (map.getValueByPosition(x, y) == MapElement.NOT_GENERATED.getMapValue()) {
				log.info("Find a point " + x + ", " + y);
				break;
			} else {
				log.info("Next try of point");
			}
			nTries--;
		}
		if (nTries == 0) {
			log.info("Point not found");
			return null;
		}
		final int w = nextInt(random, getAsInt(SimpleRoomGeneratorProperty.MAP_MINIMUM_WIDTH),
		        getAsInt(SimpleRoomGeneratorProperty.MAP_MAXIMUM_WIDTH));
		final int h = nextInt(random, getAsInt(SimpleRoomGeneratorProperty.MAP_MINIMUM_HEIGHT),
		        getAsInt(SimpleRoomGeneratorProperty.MAP_MAXIMUM_HEIGHT));
		Area2D area = new Area2D(x, y, w, h);
		log.info("Size: " + w + ", " + h);
		if (x + w > map.width || y + h > map.height || map.isRectangleWith(area, MapElement.ROOM.getMapValue())) {
			// don't fit
			log.info("Not fit");
			// check possibilities
			final List<Vector2D> possibleVectors = new ArrayList<Vector2D>();
			for (final Vector2D moveVector : Vector2D.allDirections) {
				final Vector2D calcVector = checkMove(map, area, moveVector);
				if (calcVector != null) {
					possibleVectors.add(calcVector);
				}
			}
			if (possibleVectors.isEmpty()) {
				log.info("Cannot fit");
				return null;
			}
			final Vector2D vector2d = possibleVectors.get(random.nextInt(possibleVectors.size()));
			log.info("Fit with vector " + vector2d.dx + ", " + vector2d.dy);
			area = new Area2D(x + vector2d.dx, y + vector2d.dy, w, h);
		}
		map.drawRectangle(area, MapElement.WALL, MapElement.ROOM);
		return area;
	}

	/**
	 * Checking, that move in selected direction by vector is possible and there
	 * is a place for a room.
	 * 
	 * @param map
	 *            map, where place for a room is search
	 * @param area
	 *            room to place
	 * @param vector
	 *            direction in which room may be moved
	 * @return vector of move for room to place on not used place on the map
	 */
	private Vector2D checkMove(final GeneratedMap map, final Area2D area, final Vector2D vector) {
		if (area.x + area.width > map.width && vector.dx >= 0) {
			return null;
		}
		if (area.y + area.height > map.height && vector.dy >= 0) {
			return null;
		}
		final Area2D clone = area.clone();
		final Vector2D result = new Vector2D(0, 0);
		while (clone.x >= 0 && clone.y >= 0 && clone.x < map.width && clone.y < map.height) {
			clone.move(vector);
			result.add(vector);
			if (clone.isInside(0, 0, map.width, map.height)
			        && !map.isRectangleWith(clone, MapElement.ROOM.getMapValue())) {
				return result;
			}
		}
		return null;
	}

	public enum SimpleRoomGeneratorProperty implements GeneratorProperty {

		/** The minimum width of generated room. */
		MAP_MINIMUM_WIDTH("map.width.min"),
		/** The minimum height of generated room. */
		MAP_MINIMUM_HEIGHT("map.height.min"),
		/** The maximum width of generated room. */
		MAP_MAXIMUM_WIDTH("map.width.max"),
		/** The maximum height of generated room. */
		MAP_MAXIMUM_HEIGHT("map.height.max"),
		/** Target of room ammount to be genrated (if it's possible). */
		MAP_ROOM_AMOUNT("map.room.amount");

		@Getter
		private final String propertyName;

		/** Create instance with property name. */
		SimpleRoomGeneratorProperty(final String name) {
			this.propertyName = name;
		}

	}
}
