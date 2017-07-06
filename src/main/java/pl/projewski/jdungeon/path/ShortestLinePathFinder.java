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

	public GeneratedMap findPath(GeneratedMap map, Point2D start, Point2D end, byte... excludeElements) {
		GeneratedMap result = new GeneratedMap(map.getWidth(), map.getHeight());
		if (start.equals(end)) {
			result.setValue(start.x, start.y, MapElement.PATH.getMapValue());
			return result;
		}
		int width = map.getWidth();
		int height = map.getHeight();
		int size = width * height;
		int endpos = end.x + end.y * width;
		short[] find = new short[size];
		// not-crossable value
		Arrays.fill(find, Short.MAX_VALUE);
		// start point
		short set = 0;
		find[start.x + start.y * width] = set;
		// do flood step until find end point
		boolean foundend = false;
		keepfind: while (!foundend) {
			boolean foundset = false;
			int i = 0;
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++, i++) {
					if (find[i] == set) {
						foundset = true;
						foundend =
						        // flood right
						        ((x + 1 < width) && (flood(find, size, set, i + 1, map, excludeElements) == endpos))
						                // flood left
						                || ((x - 1 >= 0)
						                        && (flood(find, size, set, i - 1, map, excludeElements) == endpos))
										// flood up
						                || ((y + 1 < height)
						                        && (flood(find, size, set, i + width, map, excludeElements) == endpos))
										// flood down
						                || ((y - 1 >= 0)
						                        && (flood(find, size, set, i - width, map, excludeElements) == endpos));
						if (foundend) {
							set++;
							break keepfind;
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
		Area2D mapArea = new Area2D(0, 0, width, height);
		Point2D pathPoint = end.clone();
		List<Vector2D> checkList = Arrays.asList(new Vector2D(1, 0), new Vector2D(-1, 0), new Vector2D(0, 1),
		        new Vector2D(0, -1));
		Vector2D lastCheck = null;
		while (set > 1) {
			set--;
			// search for possible moves
			int position = pathPoint.x + pathPoint.y * width;
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
			for (Vector2D check : checkList) {
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

	private void dumpTemp(String desc, short[] find, int width, int height) {
		System.out.println(desc);
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				System.out.print(find[x + y * width] + " ");
			}
			System.out.println();
		}
	}

	private int flood(short[] find, int size, short set, int p, GeneratedMap map, byte... excludeElements) {
		// if (p < 0 || p > size) {
		// return -1;
		// }
		if (find[p] != Short.MAX_VALUE) {
			return -1;
		}
		byte mapValue = map.getValue(p);
		for (byte ex : excludeElements) {
			if (mapValue == ex) {
				return -1;
			}
		}
		find[p] = (short) (set + 1);
		return p;
	}
}