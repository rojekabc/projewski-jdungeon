package pl.projewski.jdungeon.path;

import java.util.Random;

import org.junit.Test;

import pl.projewski.jdungeon.DumpHelper;
import pl.projewski.jdungeon.map.GeneratedMap;
import pl.projewski.jdungeon.map.MapElement;
import pl.projewski.jdungeon.map.direct.Point2D;

public class ShortestLinePathFinderDumpTest {

	@Test
	public void testPathFindOpenPoints() {
		// SimpleRoomGenerator generator = new SimpleRoomGenerator();
		// GeneratedMap map = generator.generate(18, 12);
		GeneratedMap map = new GeneratedMap(18, 12);
		Random random = new Random();
		map.setValue(1, 1, MapElement.PATH.getMapValue());
		map.setValue(10, 10, MapElement.PATH.getMapValue());
		ShortestLinePathFinder path = new ShortestLinePathFinder();
		GeneratedMap findPath = path.findPath(map, new Point2D(1, 1), new Point2D(10, 10),
		        MapElement.ROOM.getMapValue(), MapElement.WALL.getMapValue());
		map.drillMap(findPath);
		DumpHelper.dumpMap(findPath, "Path");
		DumpHelper.dumpMap(map, "Map");
	}

	@Test
	public void testPathFindWithWalls() {
		// SimpleRoomGenerator generator = new SimpleRoomGenerator();
		// GeneratedMap map = generator.generate(18, 12);
		GeneratedMap map = new GeneratedMap(18, 12);
		Random random = new Random();
		map.drawRectangle(0, 1, 3, 2, MapElement.WALL, MapElement.ROOM);
		map.setValue(1, 1, MapElement.PATH.getMapValue());

		map.drawRectangle(9, 9, 3, 2, MapElement.WALL, MapElement.ROOM);
		map.setValue(10, 10, MapElement.PATH.getMapValue());

		ShortestLinePathFinder path = new ShortestLinePathFinder();
		GeneratedMap findPath = path.findPath(map, new Point2D(1, 1), new Point2D(10, 10),
		        MapElement.ROOM.getMapValue(), MapElement.WALL.getMapValue());
		map.drillMap(findPath);
		DumpHelper.dumpMap(findPath, "Path");
		DumpHelper.dumpMap(map, "Map");
	}

	@Test
	public void testPathFindWithWallsBlocker() {
		// SimpleRoomGenerator generator = new SimpleRoomGenerator();
		// GeneratedMap map = generator.generate(18, 12);
		GeneratedMap map = new GeneratedMap(18, 12);
		Random random = new Random();
		map.drawRectangle(0, 1, 3, 2, MapElement.WALL, MapElement.ROOM);
		map.setValue(1, 1, MapElement.PATH.getMapValue());

		map.drawRectangle(9, 9, 3, 2, MapElement.WALL, MapElement.ROOM);
		map.setValue(10, 10, MapElement.PATH.getMapValue());

		map.drawRectangle(8, 9, 1, 3, MapElement.WALL, MapElement.ROOM);

		ShortestLinePathFinder path = new ShortestLinePathFinder();
		GeneratedMap findPath = path.findPath(map, new Point2D(1, 1), new Point2D(10, 10),
		        MapElement.ROOM.getMapValue(), MapElement.WALL.getMapValue());
		map.drillMap(findPath);
		DumpHelper.dumpMap(findPath, "Path");
		DumpHelper.dumpMap(map, "Map");
	}

	@Test
	public void testPathFindWithWallsTwoBlockers() {
		// SimpleRoomGenerator generator = new SimpleRoomGenerator();
		// GeneratedMap map = generator.generate(18, 12);
		GeneratedMap map = new GeneratedMap(18, 12);
		Random random = new Random();
		map.drawRectangle(0, 1, 3, 2, MapElement.WALL, MapElement.ROOM);
		map.setValue(1, 1, MapElement.PATH.getMapValue());

		map.drawRectangle(9, 9, 3, 2, MapElement.WALL, MapElement.ROOM);
		map.setValue(10, 10, MapElement.PATH.getMapValue());

		map.drawRectangle(8, 9, 1, 3, MapElement.WALL, MapElement.ROOM);
		map.drawRectangle(6, 5, 7, 2, MapElement.WALL, MapElement.ROOM);

		ShortestLinePathFinder path = new ShortestLinePathFinder();
		GeneratedMap findPath = path.findPath(map, new Point2D(1, 1), new Point2D(10, 10),
		        MapElement.ROOM.getMapValue(), MapElement.WALL.getMapValue());
		map.drillMap(findPath);
		DumpHelper.dumpMap(findPath, "Path");
		DumpHelper.dumpMap(map, "Map");
	}
}
