package pl.projewski.jdungeon.map;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lombok.extern.slf4j.Slf4j;
import pl.projewski.jdungeon.map.direct.Area2D;
import pl.projewski.jdungeon.map.direct.Vector2D;

@Slf4j
public class SimpleRoomGenerator extends AbstractMapGenerator {

	public final static String MAP_MINIMUM_WIDTH = "map.width.min";
	public final static String MAP_MINIMUM_HEIGHT = "map.height.min";
	public final static String MAP_MAXIMUM_WIDTH = "map.width.max";
	public final static String MAP_MAXIMUM_HEIGHT = "map.height.max";
	public final static String MAP_ROOM_AMOUNT = "map.room.amount";

	private final static Vector2D[] moveDirections = new Vector2D[] { //
	        new Vector2D(-1, -1), new Vector2D(-1, 0), new Vector2D(-1, 1), //
	        new Vector2D(0, -1), new Vector2D(0, 1), //
	        new Vector2D(1, -1), new Vector2D(1, 0), new Vector2D(1, 1), //
	};

	public SimpleRoomGenerator() {
		props.setProperty(MAP_MINIMUM_WIDTH, "5");
		props.setProperty(MAP_MINIMUM_HEIGHT, "5");
		props.setProperty(MAP_MAXIMUM_WIDTH, "8");
		props.setProperty(MAP_MAXIMUM_HEIGHT, "8");
		props.setProperty(MAP_ROOM_AMOUNT, "5");
	}

	public GeneratedMap generate(int width, int height, long randomSeed) {
		GeneratedMap map = new GeneratedMap(width, height);
		Random random = new Random(randomSeed);
		int nTries = 5;
		int roomsCnt = getAsInt(MAP_ROOM_AMOUNT, 5);
		for (int i = 0; i < roomsCnt; i++) {
			nTries = 5;
			while (nTries > 0) {
				final Area2D room = generateRoom(map, random);
				if (room != null) {
					break;
				}
				nTries--;
			}
		}
		return map;
	}

	private Area2D generateRoom(GeneratedMap map, Random random) {
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
		int w = nextInt(random, getAsInt(MAP_MINIMUM_WIDTH), getAsInt(MAP_MAXIMUM_WIDTH));
		int h = nextInt(random, getAsInt(MAP_MINIMUM_HEIGHT), getAsInt(MAP_MAXIMUM_HEIGHT));
		Area2D area = new Area2D(x, y, w, h);
		log.info("Size: " + w + ", " + h);
		if (x + w > map.getWidth() || y + h > map.getHeight()
		        || map.isRectangleWith(area, MapElement.ROOM.getMapValue())) {
			// don't fit
			log.info("Not fit");
			// check possibilities
			final List<Vector2D> possibleVectors = new ArrayList<Vector2D>();
			for (Vector2D moveVector : moveDirections) {
				Vector2D calcVector = checkMove(map, area, moveVector);
				if (calcVector != null) {
					possibleVectors.add(calcVector);
				}
			}
			if (possibleVectors.isEmpty()) {
				log.info("Cannot fit");
				return null;
			}
			Vector2D vector2d = possibleVectors.get(random.nextInt(possibleVectors.size()));
			log.info("Fit with vector " + vector2d.dx + ", " + vector2d.dy);
			area = new Area2D(x + vector2d.dx, y + vector2d.dy, w, h);
		}
		map.drawRectangle(area, MapElement.WALL, MapElement.ROOM);
		return area;
	}

	private Vector2D checkMove(GeneratedMap map, Area2D area, Vector2D vector) {
		if (area.x + area.width > map.getWidth() && vector.dx >= 0) {
			return null;
		}
		if (area.y + area.height > map.getHeight() && vector.dy >= 0) {
			return null;
		}
		Area2D clone = area.clone();
		Vector2D result = new Vector2D(0, 0);
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
