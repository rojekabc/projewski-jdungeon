package pl.projewski.jdungeon.path;

import org.junit.Assert;
import org.junit.Test;

import pl.projewski.jdungeon.DumpHelper;
import pl.projewski.jdungeon.map.GeneratedMap;
import pl.projewski.jdungeon.map.MapElement;
import pl.projewski.jdungeon.map.direct.Area2D;
import pl.projewski.jdungeon.map.direct.Point2D;

public class ShortestLinePathFinderDumpTest {

	@Test
	public void testPathFindOpenPoints() {
		// SimpleRoomGenerator generator = new SimpleRoomGenerator();
		// GeneratedMap map = generator.generate(18, 12);
		final GeneratedMap map = new GeneratedMap(18, 12);
		map.setValue(1, 1, MapElement.PATH.getMapValue());
		map.setValue(10, 10, MapElement.PATH.getMapValue());
		final ShortestLinePathFinder path = new ShortestLinePathFinder();
		final GeneratedMap findPath = path.findPath(map, new Point2D(1, 1), new Point2D(10, 10),
		        MapElement.ROOM.getMapValue(), MapElement.WALL.getMapValue());
		Assert.assertNotNull(findPath);
		map.drillPath(findPath);
		DumpHelper.dumpMap(findPath, "Path");
		DumpHelper.dumpMap(map, "Map");
	}

	@Test
	public void testPathFindWithWalls() {
		// SimpleRoomGenerator generator = new SimpleRoomGenerator();
		// GeneratedMap map = generator.generate(18, 12);
		final GeneratedMap map = new GeneratedMap(18, 12);
		map.drawRectangle(0, 1, 3, 2, MapElement.WALL, MapElement.ROOM);
		map.setValue(1, 1, MapElement.PATH.getMapValue());

		map.drawRectangle(9, 9, 3, 2, MapElement.WALL, MapElement.ROOM);
		map.setValue(10, 10, MapElement.PATH.getMapValue());

		final ShortestLinePathFinder path = new ShortestLinePathFinder();
		final GeneratedMap findPath = path.findPath(map, new Point2D(1, 1), new Point2D(10, 10),
		        MapElement.ROOM.getMapValue(), MapElement.WALL.getMapValue());
		Assert.assertNotNull(findPath);
		map.drillPath(findPath);
		DumpHelper.dumpMap(findPath, "Path");
		DumpHelper.dumpMap(map, "Map");
	}

	@Test
	public void testPathFindWithWallsBlocker() {
		// SimpleRoomGenerator generator = new SimpleRoomGenerator();
		// GeneratedMap map = generator.generate(18, 12);
		final GeneratedMap map = new GeneratedMap(18, 12);
		map.drawRectangle(0, 1, 3, 2, MapElement.WALL, MapElement.ROOM);
		map.setValue(1, 1, MapElement.PATH.getMapValue());

		map.drawRectangle(9, 9, 3, 2, MapElement.WALL, MapElement.ROOM);
		map.setValue(10, 10, MapElement.PATH.getMapValue());

		map.drawRectangle(8, 9, 1, 3, MapElement.WALL, MapElement.ROOM);

		final ShortestLinePathFinder path = new ShortestLinePathFinder();
		final GeneratedMap findPath = path.findPath(map, new Point2D(1, 1), new Point2D(10, 10),
		        MapElement.ROOM.getMapValue(), MapElement.WALL.getMapValue());
		Assert.assertNotNull(findPath);
		map.drillPath(findPath);
		DumpHelper.dumpMap(findPath, "Path");
		DumpHelper.dumpMap(map, "Map");
	}

	@Test
	public void testPathFindWithWallsTwoBlockers() {
		// SimpleRoomGenerator generator = new SimpleRoomGenerator();
		// GeneratedMap map = generator.generate(18, 12);
		final GeneratedMap map = new GeneratedMap(18, 12);
		map.drawRectangle(0, 1, 3, 2, MapElement.WALL, MapElement.ROOM);
		map.setValue(1, 1, MapElement.PATH.getMapValue());

		map.drawRectangle(9, 9, 3, 2, MapElement.WALL, MapElement.ROOM);
		map.setValue(10, 10, MapElement.PATH.getMapValue());

		map.drawRectangle(8, 9, 1, 3, MapElement.WALL, MapElement.ROOM);
		map.drawRectangle(6, 5, 7, 2, MapElement.WALL, MapElement.ROOM);

		final ShortestLinePathFinder path = new ShortestLinePathFinder();
		final GeneratedMap findPath = path.findPath(map, new Point2D(1, 1), new Point2D(10, 10),
		        MapElement.ROOM.getMapValue(), MapElement.WALL.getMapValue());
		Assert.assertNotNull(findPath);
		map.drillPath(findPath);
		DumpHelper.dumpMap(findPath, "Path");
		DumpHelper.dumpMap(map, "Map");
	}

	@Test
	public void testPathFindArea() {
		final GeneratedMap map = new GeneratedMap(18, 12);
		final Area2D one = new Area2D(1, 1, 3, 3);
		final Area2D two = new Area2D(8, 8, 3, 3);
		map.drawRectangle(one, MapElement.WALL, MapElement.ROOM);
		map.drawRectangle(two, MapElement.WALL, MapElement.ROOM);
		final ShortestLinePathFinder path = new ShortestLinePathFinder();
		final GeneratedMap findPath = path.findPath(map, one, two, MapElement.WALL.getMapValue(),
		        MapElement.ROOM.getMapValue());
		Assert.assertNotNull(findPath);
		map.drillPath(findPath);
		DumpHelper.dumpMap(findPath, "Path");
		DumpHelper.dumpMap(map, "Map");
	}
}
