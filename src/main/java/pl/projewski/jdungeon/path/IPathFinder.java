package pl.projewski.jdungeon.path;

import pl.projewski.jdungeon.map.GeneratedMap;
import pl.projewski.jdungeon.map.direct.Area2D;
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
	 * @return map with created path
	 */
	GeneratedMap findPath(GeneratedMap map, Point2D start, Point2D end, byte... excludeElements);

	/**
	 * Find a path on map from the start area to the end area, begin at any
	 * point and reach any point of the end area. Excluded elements on a map are
	 * blocking points for created path.
	 * 
	 * @param map
	 *            the map to search a path
	 * @param start
	 *            the start area
	 * @param end
	 *            the end area
	 * @return map with created path
	 */
	GeneratedMap findPath(GeneratedMap map, Area2D start, Area2D end, byte... excludeElements);
}
