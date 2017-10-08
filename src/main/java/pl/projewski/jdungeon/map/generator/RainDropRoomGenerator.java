package pl.projewski.jdungeon.map.generator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import lombok.Getter;
import pl.projewski.jdungeon.map.AbstractMapGenerator;
import pl.projewski.jdungeon.map.GeneratedMap;
import pl.projewski.jdungeon.map.GeneratorProperty;
import pl.projewski.jdungeon.map.MapElement;
import pl.projewski.jdungeon.map.direct.Area2D;
import pl.projewski.jdungeon.map.direct.Point2D;
import pl.projewski.jdungeon.map.direct.Vector2D;
import pl.projewski.jdungeon.path.ShortestLinePathFinder;

public class RainDropRoomGenerator extends AbstractMapGenerator {

	public RainDropRoomGenerator() {
		setProperty(RainDropRoomGeneratorProperty.RAIN_DROP_AMMOUNT, "100");
		setProperty(RainDropRoomGeneratorProperty.MAXIMUM_ROOM_AMMOUNT, "5");
	}

	@Override
	public GeneratedMap generate(final int width, final int height, final long randomSeed) {
		final GeneratedMap map = new GeneratedMap(width, height);
		final Random random = new Random(randomSeed);
		final Area2D[] rooms = new Area2D[getAsInt(RainDropRoomGeneratorProperty.MAXIMUM_ROOM_AMMOUNT)];
		int cnt = getAsInt(RainDropRoomGeneratorProperty.RAIN_DROP_AMMOUNT);
		while (cnt-- >= 0) {
			// select, to which room rain would be dropped
			final int selector = random.nextInt(rooms.length);
			Area2D room = rooms[selector];
			if (room == null) {
				// Room not created at such position. Put rain drop at random
				// point.
				final Point2D rain = map.randomPoint(random);
				if (map.getValueByPosition(rain.x, rain.y) != MapElement.NOT_GENERATED.getMapValue()) {
					// rain drop land somewhere. Check on which room.
					for (final Area2D findRoom : rooms) {
						if (findRoom == null) {
							continue;
						}
						if (rain.isInsideArea(findRoom)) {
							room = findRoom;
							break;
						}
					}
				} else {
					// create new room
					room = new Area2D(rain.x - 1, rain.y - 1, 3, 3);
					// check room may be put
					if (!room.isInside(map)) {
						continue;
					}
					if (map.isRectangleWith(room, MapElement.ROOM.getMapValue())) {
						// should never happend - remove this after all. FIXME
						throw new IllegalStateException();
					}
					rooms[selector] = room;
					map.drawRectangle(room, MapElement.WALL, MapElement.ROOM);
					continue;
				}
			}
			// try put a rain into room
			// remove current room draw
			map.drawRectangle(room, MapElement.NOT_GENERATED, MapElement.NOT_GENERATED);
			// try to resize a room
			final List<Vector2D> vectors = new ArrayList<>(Arrays.asList(Vector2D.baseDirections));
			while (!vectors.isEmpty()) {
				final Vector2D vector = vectors.get(random.nextInt(vectors.size()));
				room.resize(vector);
				if (room.isInside(map) && !map.isRectangleWith(room, MapElement.ROOM.getMapValue())) {
					// fraw new sized room and finish
					map.drawRectangle(room, MapElement.WALL, MapElement.ROOM);
					break;
				} else {
					room.resizeBack(vector);
					vectors.remove(vector);
					// draw back
					map.drawRectangle(room, MapElement.WALL, MapElement.ROOM);
				}
			}
		}

		// find a paths between rooms
		final ShortestLinePathFinder pathFinder = new ShortestLinePathFinder();
		final GeneratedMap paths = pathFinder.findPaths(map, Arrays.asList(rooms), MapElement.ROOM.getMapValue(),
		        MapElement.WALL.getMapValue());
		map.drillPath(paths);

		return map;
	}

	public enum RainDropRoomGeneratorProperty implements GeneratorProperty {
		RAIN_DROP_AMMOUNT("rain.ammount"), MAXIMUM_ROOM_AMMOUNT("room.ammount");

		@Getter
		private final String propertyName;

		/** Create instance with property name. */
		RainDropRoomGeneratorProperty(final String name) {
			this.propertyName = name;
		}

	}

}
