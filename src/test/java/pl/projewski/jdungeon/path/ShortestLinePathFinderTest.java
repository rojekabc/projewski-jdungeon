package pl.projewski.jdungeon.path;

import org.junit.Assert;
import org.junit.Test;

import pl.projewski.jdungeon.map.GeneratedMap;
import pl.projewski.jdungeon.map.MapElement;
import pl.projewski.jdungeon.map.direct.Area2D;
import pl.projewski.jdungeon.map.direct.Point2D;
import pl.projewski.jdungeon.map.direct.Vector2D;

public class ShortestLinePathFinderTest {

	@Test
	public void testPathFindOpenPoints() {
		final GeneratedMap map = new GeneratedMap(18, 12);
		map.setValue(1, 1, MapElement.PATH.getMapValue());
		map.setValue(10, 10, MapElement.PATH.getMapValue());
		final ShortestLinePathFinder path = new ShortestLinePathFinder();
		final Point2D start = new Point2D(1, 1);
		final Point2D end = new Point2D(10, 10);
		final GeneratedMap findPath = path.findPath(map, start, end, MapElement.ROOM.getMapValue(),
		        MapElement.WALL.getMapValue());
		Assert.assertNotNull(findPath);
		checkCorrectDrill(map, findPath, MapElement.ROOM.getMapValue(), MapElement.WALL.getMapValue());
		map.drillPath(findPath);
		checkPath(map, start, end);
	}

	@Test
	public void testPathFindWithWalls() {
		final GeneratedMap map = new GeneratedMap(18, 12);
		map.drawRectangle(0, 1, 3, 2, MapElement.WALL, MapElement.ROOM);
		map.setValue(1, 1, MapElement.PATH.getMapValue());

		map.drawRectangle(9, 9, 3, 2, MapElement.WALL, MapElement.ROOM);
		map.setValue(10, 10, MapElement.PATH.getMapValue());

		final ShortestLinePathFinder path = new ShortestLinePathFinder();
		final Point2D start = new Point2D(1, 1);
		final Point2D end = new Point2D(10, 10);
		final GeneratedMap findPath = path.findPath(map, start, end, MapElement.ROOM.getMapValue(),
		        MapElement.WALL.getMapValue());
		Assert.assertNotNull(findPath);
		checkCorrectDrill(map, findPath, MapElement.ROOM.getMapValue(), MapElement.WALL.getMapValue());
		map.drillPath(findPath);
		checkPath(map, start, end);
	}

	@Test
	public void testPathFindWithWallsBlocker() {
		final GeneratedMap map = new GeneratedMap(18, 12);
		map.drawRectangle(0, 1, 3, 2, MapElement.WALL, MapElement.ROOM);
		map.setValue(1, 1, MapElement.PATH.getMapValue());

		map.drawRectangle(9, 9, 3, 2, MapElement.WALL, MapElement.ROOM);
		map.setValue(10, 10, MapElement.PATH.getMapValue());

		map.drawRectangle(8, 9, 1, 3, MapElement.WALL, MapElement.ROOM);

		final ShortestLinePathFinder path = new ShortestLinePathFinder();
		final Point2D start = new Point2D(1, 1);
		final Point2D end = new Point2D(10, 10);
		final GeneratedMap findPath = path.findPath(map, start, end, MapElement.ROOM.getMapValue(),
		        MapElement.WALL.getMapValue());
		Assert.assertNotNull(findPath);
		checkCorrectDrill(map, findPath, MapElement.ROOM.getMapValue(), MapElement.WALL.getMapValue());
		map.drillPath(findPath);
		checkPath(map, start, end);
	}

	@Test
	public void testPathFindWithWallsTwoBlockers() {
		final GeneratedMap map = new GeneratedMap(18, 12);
		map.drawRectangle(0, 1, 3, 2, MapElement.WALL, MapElement.ROOM);
		map.setValue(1, 1, MapElement.PATH.getMapValue());

		map.drawRectangle(9, 9, 3, 2, MapElement.WALL, MapElement.ROOM);
		map.setValue(10, 10, MapElement.PATH.getMapValue());

		map.drawRectangle(8, 9, 1, 3, MapElement.WALL, MapElement.ROOM);
		map.drawRectangle(6, 5, 7, 2, MapElement.WALL, MapElement.ROOM);

		final ShortestLinePathFinder path = new ShortestLinePathFinder();
		final Point2D start = new Point2D(1, 1);
		final Point2D end = new Point2D(10, 10);
		final GeneratedMap findPath = path.findPath(map, start, end, MapElement.ROOM.getMapValue(),
		        MapElement.WALL.getMapValue());
		Assert.assertNotNull(findPath);
		checkCorrectDrill(map, findPath, MapElement.ROOM.getMapValue(), MapElement.WALL.getMapValue());
		map.drillPath(findPath);
		checkPath(map, start, end);
	}

	@Test
	public void testPathFind_noPassToEndPoint() {
		final GeneratedMap map = new GeneratedMap(18, 12);
		map.setValue(1, 1, MapElement.PATH.getMapValue());
		map.drawRectangle(9, 9, 3, 3, MapElement.WALL, MapElement.PATH);

		final ShortestLinePathFinder path = new ShortestLinePathFinder();
		final Point2D start = new Point2D(1, 1);
		final Point2D end = new Point2D(10, 10);
		final GeneratedMap findPath = path.findPath(map, start, end, MapElement.ROOM.getMapValue(),
		        MapElement.WALL.getMapValue());
		Assert.assertNull(findPath);
	}

	@Test
	public void testPathFind_noPassFromStartPoint() {
		final GeneratedMap map = new GeneratedMap(18, 12);
		map.drawRectangle(0, 0, 3, 3, MapElement.WALL, MapElement.PATH);
		map.setValue(10, 10, MapElement.PATH.getMapValue());

		final ShortestLinePathFinder path = new ShortestLinePathFinder();
		final Point2D start = new Point2D(1, 1);
		final Point2D end = new Point2D(10, 10);
		final GeneratedMap findPath = path.findPath(map, start, end, MapElement.ROOM.getMapValue(),
		        MapElement.WALL.getMapValue());
		Assert.assertNull(findPath);
	}

	// check, that path don't go through not allowed elements
	private void checkCorrectDrill(final GeneratedMap map, final GeneratedMap path, final byte... excludes) {
		final int width = map.getWidth();
		final int height = map.getHeight();
		Assert.assertEquals(width, path.getWidth());
		Assert.assertEquals(height, path.getHeight());
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				final byte pathValue = path.getValueByPosition(x, y);
				if (pathValue == MapElement.PATH.getMapValue()) {
					final byte mapValue = map.getValueByPosition(x, y);
					for (final byte exclude : excludes) {
						Assert.assertNotEquals("Path is in unexpected position on the map", exclude, mapValue);
					}
				}
			}
		}
	}

	// validate, that can walk from start to end one independent path
	private void checkPath(final GeneratedMap map, final Point2D start, final Point2D end) {
		final GeneratedMap clone = map.clone();
		final Point2D pos = start.clone();
		final Vector2D[] vectors = new Vector2D[] { new Vector2D(-1, 0), new Vector2D(1, 0), new Vector2D(0, -1),
		        new Vector2D(0, 1) };
		final Area2D mapArea = new Area2D(0, 0, map.getWidth(), map.getHeight());

		// check it is a path
		Assert.assertEquals(MapElement.PATH.getMapValue(), clone.getValueByPosition(pos.x, pos.y));
		while (!pos.equals(end)) {
			// remove point with path
			clone.setValue(pos, MapElement.NOT_GENERATED.getMapValue());
			// find next point of path
			Vector2D moveVector = null;
			for (final Vector2D vector : vectors) {
				pos.move(vector);
				if (pos.isInsideArea(mapArea)
				        && clone.getValueByPosition(pos.x, pos.y) == MapElement.PATH.getMapValue()) {
					Assert.assertNull("More than one possiblitity to move on path", moveVector);
					moveVector = vector;
				}
				pos.moveBack(vector);
			}
			Assert.assertNotNull("Cannot find path to the end", moveVector);
			// move to next path element
			pos.move(moveVector);
		}
	}
}
