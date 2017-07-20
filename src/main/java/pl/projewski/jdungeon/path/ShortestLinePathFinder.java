package pl.projewski.jdungeon.path;

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

	@Override
	public GeneratedMap findPath(final GeneratedMap map, final Area2D start, final Area2D end,
	        final byte... excludeElements) {
		final GeneratedMap result = new GeneratedMap(map.getWidth(), map.getHeight());
		final int width = map.getWidth();
		final int height = map.getHeight();
		final int size = width * height;

		final short[] find = new short[size];
		// not-set value
		Arrays.fill(find, Short.MAX_VALUE);
		// put a MIN_VALUE which is a symbol of end on area border without
		// narrows of the area.
		drawAreaBoundsWithoutNarrows(find, end, width, Short.MIN_VALUE);
		drawAreaBoundsWithoutNarrows(find, start, width, (short) 0);

		return startAlgorithm(result, map, find, width, height, excludeElements);
	}

	private void drawAreaBoundsWithoutNarrows(final short[] findMap, final Area2D area, final int mapWidth,
	        final short value) {
		int endpos = area.x + 1 + area.y * mapWidth + area.width - 2;
		for (int pos = area.x + 1 + area.y * mapWidth; pos < endpos; pos++) {
			findMap[pos] = value;
		}
		endpos = area.x + 1 + (area.y + area.height - 1) * mapWidth + area.width - 2;
		for (int pos = area.x + 1 + (area.y + area.height - 1) * mapWidth; pos < endpos; pos++) {
			findMap[pos] = value;
		}
		endpos = (area.y + area.height - 1) * mapWidth;
		for (int pos = area.x + (area.y + 1) * mapWidth; pos < endpos; pos += mapWidth) {
			findMap[pos] = value;
		}
		endpos = (area.y + area.height - 1) * mapWidth + area.x + area.width - 1;
		for (int pos = area.x + area.width - 1 + (area.y + 1) * mapWidth; pos < endpos; pos += mapWidth) {
			findMap[pos] = value;
		}
	}

	@Override
	public GeneratedMap findPath(final GeneratedMap map, final Point2D start, final Point2D end,
	        final byte... excludeElements) {
		final GeneratedMap result = new GeneratedMap(map.getWidth(), map.getHeight());
		if (start.equals(end)) {
			result.setValue(start, MapElement.PATH.getMapValue());
			return result;
		}
		final int width = map.getWidth();
		final int height = map.getHeight();
		final int size = width * height;
		final int endpos = end.x + end.y * width;
		final short[] find = new short[size];
		// not-set value
		Arrays.fill(find, Short.MAX_VALUE);
		// put a MIN_VALUE which is a symbol of end
		find[endpos] = Short.MIN_VALUE;
		// start point
		find[start.x + start.y * width] = (short) 0;
		return startAlgorithm(result, map, find, width, height, excludeElements);
	}

	private GeneratedMap startAlgorithm(final GeneratedMap result, final GeneratedMap map, final short[] find,
	        final int width, final int height, final byte... excludeElements) {
		final int size = width * height;
		// do flood step until find end point
		short set = 0;
		boolean foundend = false;
		final Point2D pathPoint = new Point2D(0, 0);
		keepfind: while (!foundend) {
			boolean foundset = false;
			int i = 0;
			for (pathPoint.y = 0; pathPoint.y < height; pathPoint.y++) {
				for (pathPoint.x = 0; pathPoint.x < width; pathPoint.x++, i++) {
					if (find[i] == set) {
						foundset = true;
						final List<Vector2D> checkList = Arrays.asList(new Vector2D(1, 0), new Vector2D(-1, 0),
						        new Vector2D(0, 1), new Vector2D(0, -1));
						for (final Vector2D vector : checkList) {
							pathPoint.move(vector);
							if (pathPoint.isInsideArea(0, 0, width, height)) {
								if (flood(find, size, set, pathPoint, map, excludeElements)) {
									foundend = true;
									set++;
									break keepfind;
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
		// going back and mark a path on a map
		final Area2D mapArea = new Area2D(0, 0, width, height);
		final List<Vector2D> checkList = Arrays.asList(new Vector2D(1, 0), new Vector2D(-1, 0), new Vector2D(0, 1),
		        new Vector2D(0, -1));
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
			for (final Vector2D check : checkList) {
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
		final int pos = p.x + p.y * map.getWidth();
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