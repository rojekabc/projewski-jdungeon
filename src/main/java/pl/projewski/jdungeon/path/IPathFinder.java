package pl.projewski.jdungeon.path;

import pl.projewski.jdungeon.map.GeneratedMap;
import pl.projewski.jdungeon.map.direct.Point2D;

public interface IPathFinder {
	/**
	 * Find a path on map from start point to end poining. Excluded elements on
	 * a map are not passable for created path.
	 * 
	 * @param map
	 *            the map to search a path
	 * @param start
	 *            the start point
	 * @param end
	 *            the end point
	 * @param excludeElements
	 *            excluded elements, which are threat as a wall
	 * @return
	 */
	GeneratedMap findPath(GeneratedMap map, Point2D start, Point2D end, byte... excludeElements);
}
