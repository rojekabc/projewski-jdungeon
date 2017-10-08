package pl.projewski.jdungeon.path;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.NoArgsConstructor;
import pl.projewski.jdungeon.map.GeneratedMap;
import pl.projewski.jdungeon.map.MapElement;
import pl.projewski.jdungeon.map.direct.Area2D;
import pl.projewski.jdungeon.map.direct.Point2D;
import pl.projewski.jdungeon.map.direct.Vector2D;

/**
 * This algorithm works as a flood from start point, which search end point.
 * When it's reached flood is going back to start point with algorithm called
 * one-direction-wage. It's designed by me.
 * 
 * Size of a path is limit by the maximum short value - 1.
 * 
 * @author Piotr Rojewski <rojek_abc@o2.pl>
 *
 */
@NoArgsConstructor
public class ShortestLinePathFinder implements IPathFinder {

	/**
	 * Find paths connection between the elements of rectangle's list.
	 * 
	 * @param map
	 *            map
	 * @param areaList
	 *            list of rectangles (rooms)
	 * @param excludeElements
	 *            map elements, where path cannot be drilled
	 * @return path network
	 */
	public GeneratedMap findPaths(final GeneratedMap map, final List<Area2D> areaList, final byte... excludeElements) {
		if (areaList.size() < 2) {
			return null;
		}

		final GeneratedMap result = new GeneratedMap(map.width, map.height);

		final Area2D startArea = areaList.get(0);
		final List<Area2D> startAreaList = new ArrayList<>();
		startAreaList.add(startArea);
		final List<Area2D> targetAreaList = new ArrayList<>(areaList);
		targetAreaList.remove(startArea);

		while (!targetAreaList.isEmpty()) {
			final GeneratedMap path = findPath(map, startAreaList, targetAreaList, excludeElements);
			if (path == null) {
				break;
			}
			result.drillPath(path);
		}

		return result;
	}

	// find first possible path between start areas and target areas
	// if path is found resturn this path and move area, to which a path was
	// found from target list to start list
	private GeneratedMap findPath(final GeneratedMap map, final List<Area2D> startAreaList,
	        final List<Area2D> targetAreaList, final byte... excludeElements) {
		if (targetAreaList.isEmpty()) {
			return null;
		}
		final GeneratedMap result = new GeneratedMap(map.width, map.height);
		final int width = map.width;
		final int height = map.height;
		final int size = width * height;
		final short[] find = new short[size];
		// not-set value
		Arrays.fill(find, Short.MAX_VALUE);

		// set all target areas with target element value
		for (final Area2D targetArea : targetAreaList) {
			drawAreaBoundsWithoutCorners(find, targetArea, width, Short.MIN_VALUE, false);
		}
		// set all start area with start set value and check, that a way was
		// found
		Point2D conPoint;
		for (final Area2D startArea : startAreaList) {
			conPoint = drawAreaBoundsWithoutCorners(find, startArea, width, (short) 0, true);
			if (conPoint != null) {
				result.setValue(conPoint, MapElement.PATH.getMapValue());
				moveFromTargetToStart(conPoint, startAreaList, targetAreaList);
				return result;
			}
		}
		// find a path
		conPoint = startFloodAlgorithm(map, find, width, height, excludeElements);
		if (conPoint == null) {
			return null;
		}
		moveFromTargetToStart(conPoint, startAreaList, targetAreaList);
		return startBackAlgorithm(result, find, width, height, conPoint);

		// return result;
	}

	private void moveFromTargetToStart(final Point2D point, final List<Area2D> startAreaList,
	        final List<Area2D> targetAreaList) {
		for (final Area2D target : targetAreaList) {
			if (point.isInsideArea(target)) {
				startAreaList.add(target);
				targetAreaList.remove(target);
				return;
			}
		}
	}

	@Override
	public GeneratedMap findPath(final GeneratedMap map, final Area2D start, final Area2D end,
	        final byte... excludeElements) {
		final GeneratedMap result = new GeneratedMap(map.width, map.height);
		final int width = map.width;
		final int height = map.height;
		final int size = width * height;

		final short[] find = new short[size];
		// not-set value
		Arrays.fill(find, Short.MAX_VALUE);
		// put a MIN_VALUE which is a symbol of end on area border without
		// narrows of the area.
		drawAreaBoundsWithoutCorners(find, end, width, Short.MIN_VALUE, false);
		Point2D conPoint = drawAreaBoundsWithoutCorners(find, start, width, (short) 0, true);
		if (conPoint != null) {
			result.setValue(conPoint, MapElement.PATH.getMapValue());
			return result;
		}

		conPoint = startFloodAlgorithm(map, find, width, height, excludeElements);
		if (conPoint == null) {
			return null;
		}
		return startBackAlgorithm(result, find, width, height, conPoint);
		// return startAlgorithm(result, map, find, width, height,
		// excludeElements);
	}

	/**
	 * Put values on bounds of rectangle but without corners. Useful for
	 * calculate paths that will be between walls - not corners of a romm.
	 * 
	 * @param findMap
	 * @param area
	 * @param mapWidth
	 * @param value
	 * @param checkEnd
	 * @return
	 */
	private Point2D drawAreaBoundsWithoutCorners(final short[] findMap, final Area2D area, final int mapWidth,
	        final short value, final boolean checkEnd) {
		int endpos = area.x + 1 + area.y * mapWidth + area.width - 2;
		for (int pos = area.x + 1 + area.y * mapWidth; pos < endpos; pos++) {
			if (checkEnd && findMap[pos] == Short.MIN_VALUE) {
				return new Point2D(pos % mapWidth, pos / mapWidth);
			}
			findMap[pos] = value;
		}
		endpos = area.x + 1 + (area.y + area.height - 1) * mapWidth + area.width - 2;
		for (int pos = area.x + 1 + (area.y + area.height - 1) * mapWidth; pos < endpos; pos++) {
			if (checkEnd && findMap[pos] == Short.MIN_VALUE) {
				return new Point2D(pos % mapWidth, pos / mapWidth);
			}
			findMap[pos] = value;
		}
		endpos = (area.y + area.height - 1) * mapWidth;
		for (int pos = area.x + (area.y + 1) * mapWidth; pos < endpos; pos += mapWidth) {
			if (checkEnd && findMap[pos] == Short.MIN_VALUE) {
				return new Point2D(pos % mapWidth, pos / mapWidth);
			}
			findMap[pos] = value;
		}
		endpos = (area.y + area.height - 1) * mapWidth + area.x + area.width - 1;
		for (int pos = area.x + area.width - 1 + (area.y + 1) * mapWidth; pos < endpos; pos += mapWidth) {
			if (checkEnd && findMap[pos] == Short.MIN_VALUE) {
				return new Point2D(pos % mapWidth, pos / mapWidth);
			}
			findMap[pos] = value;
		}
		return null;
	}

	@Override
	public GeneratedMap findPath(final GeneratedMap map, final Point2D start, final Point2D end,
	        final byte... excludeElements) {
		final GeneratedMap result = new GeneratedMap(map.width, map.height);
		if (start.equals(end)) {
			result.setValue(start, MapElement.PATH.getMapValue());
			return result;
		}
		final int width = map.width;
		final int height = map.height;
		final int size = width * height;
		final int endpos = end.x + end.y * width;
		final short[] find = new short[size];
		// not-set value
		Arrays.fill(find, Short.MAX_VALUE);
		// put a MIN_VALUE which is a symbol of end
		find[endpos] = Short.MIN_VALUE;
		// start point
		find[start.x + start.y * width] = (short) 0;
		final Point2D conPoint = startFloodAlgorithm(map, find, width, height, excludeElements);
		if (conPoint == null) {
			return null;
		}
		return startBackAlgorithm(result, find, width, height, conPoint);
		// return startAlgorithm(result, map, find, width, height,
		// excludeElements);
	}

	// start flood algorithm. It do a 'flood' from start points (value 0) end do
	// algorithm until find target value.
	// In result is a target point, which was found. If null - no target point
	// found by flood. This point is usefull to get information to which room
	// path was found.
	private Point2D startFloodAlgorithm(final GeneratedMap map, final short[] find, final int width, final int height,
	        final byte... excludeElements) {
		final int size = width * height;
		// do flood step until find end point
		short set = 0;
		boolean foundend = false;
		final Point2D pathPoint = new Point2D(0, 0);
		while (!foundend) {
			boolean foundset = false;
			int i = 0;
			for (pathPoint.y = 0; pathPoint.y < height; pathPoint.y++) {
				for (pathPoint.x = 0; pathPoint.x < width; pathPoint.x++, i++) {
					if (find[i] == set) {
						foundset = true;
						for (final Vector2D vector : Vector2D.baseDirections) {
							pathPoint.move(vector);
							if (pathPoint.isInsideArea(0, 0, width, height)) {
								if (flood(find, size, set, pathPoint, map, excludeElements)) {
									foundend = true;
									set++;
									return pathPoint;
									// break keepfind;
								}
							}
							pathPoint.moveBack(vector);
						}
					}
				}
			}
			set++;
			if (!foundset) {
				// Cannot find a way
				return null;
			}
		}
		throw new IllegalStateException("It shouldn't be here");
	}

	// this is back algorithm to calculate path from pathPoint, which was found
	// by flood algorithm to source point of flood.
	private GeneratedMap startBackAlgorithm(final GeneratedMap result, final short[] find, final int width,
	        final int height, final Point2D pathPoint) {
		short set = find[pathPoint.x + pathPoint.y * width];
		// going back and mark a path on a map
		final Area2D mapArea = new Area2D(0, 0, width, height);
		Vector2D lastCheck = null;
		result.setValue(pathPoint.x, pathPoint.y, MapElement.PATH.getMapValue());
		while (set > 0) {
			set--;
			// search for possible moves
			final int position = pathPoint.x + pathPoint.y * width;
			// first check with last vector
			if (lastCheck != null) {
				if (pathPoint.isInsideAreaMoveVector(mapArea, lastCheck)) {
					if (find[position + lastCheck.dx + width * lastCheck.dy] == set) {
						// next point has been found
						pathPoint.move(lastCheck);
						result.setValue(pathPoint.x, pathPoint.y, MapElement.PATH.getMapValue());
						continue;
					}
				}
			}
			// check with vectors
			for (final Vector2D check : Vector2D.baseDirections) {
				if (check == lastCheck) {
					// it was verified as a direction-wage
					continue;
				}
				if (pathPoint.isInsideAreaMoveVector(mapArea, check)) {
					if (find[position + check.dx + width * check.dy] == set) {
						// next point has been found
						pathPoint.move(check);
						result.setValue(pathPoint.x, pathPoint.y, MapElement.PATH.getMapValue());
						lastCheck = check;
						break;
					}
				}

			}
		}
		return result;
	}

	void dumpTemp(final String desc, final short[] find, final int width, final int height) {
		System.out.println(desc);
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				System.out.print(find[x + y * width] + " ");
			}
			System.out.println();
		}
	}

	/**
	 * 
	 * @param find
	 * @param size
	 * @param set
	 * @param p
	 * @param map
	 * @param excludeElements
	 * @return true if meets end-point
	 */
	private boolean flood(final short[] find, final int size, final short set, final Point2D p, final GeneratedMap map,
	        final byte... excludeElements) {
		final int pos = p.x + p.y * map.width;
		// is it end
		if (find[pos] == Short.MIN_VALUE) {
			find[pos] = (short) (set + 1);
			return true;
		}
		// is it flooded
		if (find[pos] != Short.MAX_VALUE) {
			return false;
		}
		// can be flooded
		final byte mapValue = map.getValue(pos);
		for (final byte ex : excludeElements) {
			if (mapValue == ex) {
				return false;
			}
		}
		// flood
		find[pos] = (short) (set + 1);
		return false;
	}

}