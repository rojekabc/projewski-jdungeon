package pl.projewski.jdungeon.map;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lombok.extern.slf4j.Slf4j;
import pl.projewski.jdungeon.map.direct.Area2D;
import pl.projewski.jdungeon.map.direct.Vector2D;

/**
 * This is created by me simple room generator.
 * 
 * @author Piotr Rojewski <rojek_abc@o2.pl>
 *
 */
@Slf4j
public class SimpleRoomGenerator extends AbstractMapGenerator {

	// Set of possible move directions to find a place for generated room, which
	// is in conflict with another room
	private final static Vector2D[] moveDirections = new Vector2D[] { //
	        new Vector2D(-1, -1), new Vector2D(-1, 0), new Vector2D(-1, 1), //
	        new Vector2D(0, -1), new Vector2D(0, 1), //
	        new Vector2D(1, -1), new Vector2D(1, 0), new Vector2D(1, 1), //
	};

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

		// calculate connections network between rooms
		final DistanceList<Area2D> distanceList = new DistanceList<>();
		for (int i = 0; i < roomList.size(); i++) {
			for (int j = i + 1; j < roomList.size(); j++) {
				distanceList.appendDistance(roomList.get(i), roomList.get(j));
			}
		}

		// check shortest connections between rooms and try build a path between
		// them
		for (final Area2D room : roomList) {
			final List<Distance<Area2D>> roomDistanceList = distanceList.getDistanceList(room);
			if (roomDistanceList.isEmpty()) {
				continue;
			}
			for (final Distance<Area2D> roomDistance : roomDistanceList) {

			}
		}

		// generate paths between rooms

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
			x = random.nextInt(map.getWidth());
			y = random.nextInt(map.getHeight());
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
		if (x + w > map.getWidth() || y + h > map.getHeight()
		        || map.isRectangleWith(area, MapElement.ROOM.getMapValue())) {
			// don't fit
			log.info("Not fit");
			// check possibilities
			final List<Vector2D> possibleVectors = new ArrayList<Vector2D>();
			for (final Vector2D moveVector : moveDirections) {
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
		if (area.x + area.width > map.getWidth() && vector.dx >= 0) {
			return null;
		}
		if (area.y + area.height > map.getHeight() && vector.dy >= 0) {
			return null;
		}
		final Area2D clone = area.clone();
		final Vector2D result = new Vector2D(0, 0);
		while (clone.x >= 0 && clone.y >= 0 && clone.x < map.getWidth() && clone.y < map.getHeight()) {
			clone.move(vector);
			result.add(vector);
			if (clone.isInside(0, 0, map.getWidth(), map.getHeight())
			        && !map.isRectangleWith(clone, MapElement.ROOM.getMapValue())) {
				return result;
			}
		}
		return null;
	}

}
