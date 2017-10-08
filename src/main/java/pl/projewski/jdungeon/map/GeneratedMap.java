package pl.projewski.jdungeon.map;

import java.util.Arrays;

import lombok.extern.slf4j.Slf4j;
import pl.projewski.jdungeon.map.direct.Area2D;
import pl.projewski.jdungeon.map.direct.Point2D;

/**
 * The instance of generated map.
 * 
 * @author Piotr Rojewski <rojek_abc@o2.pl>
 *
 */
@Slf4j
public class GeneratedMap extends Area2D implements Cloneable {
	/**
	 * Container of map data.
	 */
	byte[] map;

	/**
	 * Create map data for specified size and fill it by
	 * {@link MapElement}.NOT_GENERATED value.
	 * 
	 * @param width
	 *            the width of map
	 * @param height
	 *            the height of map
	 * @exception IllegalArgumentException
	 *                if width or height are less or euals zero
	 */
	public GeneratedMap(final int width, final int height) {
		super(0, 0, width, height);
		if (width <= 0) {
			throw new IllegalArgumentException("width");
		}
		if (height <= 0) {
			throw new IllegalArgumentException("height");
		}
		this.width = width;
		this.height = height;
		map = new byte[width * height];
		fill(MapElement.NOT_GENERATED);
	}

	/**
	 * Fill the whole map by an element.
	 * 
	 * @param element
	 *            the element to fill the map
	 */
	void fill(final MapElement element) {
		Arrays.fill(map, element.getMapValue());
	}

	/**
	 * Draw bounded rectangle with fill on map. If coordinates or size of
	 * element will be out of map - it'll be cut off. It is drawing method with
	 * checking arguments.
	 * 
	 * @param x
	 *            the x coordinate of the start point
	 * @param y
	 *            the y coordinate of the start point
	 * @param width
	 *            the width of rectangle
	 * @param height
	 *            the height of rectangle
	 * @param bound
	 *            the element to be as bounds
	 * @param the
	 *            element to fill inside the rectangle
	 */
	public void drawRectangle(int x, int y, int width, int height, final MapElement bound, final MapElement fill) {
		log.info("Draw " + x + ", " + y + ", " + width + ", " + height);
		// check input arguments and fit them to map area
		if (x < 0) {
			width += x;
			x = 0;
		}
		if (y < 0) {
			height += y;
			y = 0;
		}
		if (width < 0 || height < 0 || x >= this.width || y >= this.height) {
			return;
		}
		if (x + width > this.width) {
			width = this.width - x;
		}
		if (y + height > this.height) {
			height = this.height - y;
		}
		// drawing
		drawRectangleChecked(x, y, width, height, bound.getMapValue(), fill.getMapValue());
	}

	/**
	 * Draw bounded rectangle with fill on map. If coordinates or size of
	 * element will be out of map - it'll be cut off. It is drawing method with
	 * checking arguments.
	 * 
	 * @param area
	 *            area to draw
	 * @param bound
	 *            bound element
	 * @param fill
	 *            fill element
	 */
	public void drawRectangle(final Area2D area, final MapElement bound, final MapElement fill) {
		drawRectangle(area.x, area.y, area.width, area.height, bound, fill);
	}

	/**
	 * Draw the rectangle on the map.If coordinates or size of element will be
	 * out of map - it'll be cut off. It is drawing method with checking
	 * arguments.
	 * 
	 * @param x
	 *            the rectangle's x
	 * @param y
	 *            the rectangle's y
	 * @param width
	 *            the rectangle's width
	 * @param height
	 *            the rectangle's height
	 * @param fill
	 *            the rectangle's filling element
	 */
	void drawRectangle(int x, int y, int width, int height, final MapElement fill) {
		// check input arguments and fit them to map area
		if (x < 0) {
			width += x;
			x = 0;
		}
		if (y < 0) {
			height += y;
			y = 0;
		}
		if (width < 0 || height < 0 || x >= this.width || y >= this.height) {
			return;
		}
		if (x + width > this.width) {
			width = this.width - x;
		}
		if (y + height > this.height) {
			height = this.height - y;
		}
		// drawing
		drawRectangleChecked(x, y, width, height, fill.getMapValue());
	}

	/**
	 * Draw the rectangle on the map.If coordinates or size of element will be
	 * out of map - it'll be cut off. It is drawing method with checking
	 * arguments.
	 * 
	 * @param area
	 *            the rectangle area
	 * @param fill
	 *            the filling element
	 */
	void drawRectangle(final Area2D area, final MapElement fill) {
		drawRectangle(area.x, area.y, area.width, area.height, fill);
	}

	/**
	 * Draw the rectangle on the map. Arguments should be checked before a call
	 * that are valid.
	 * 
	 * @param x
	 *            the rectangle's x
	 * @param y
	 *            the rectangle's y
	 * @param width
	 *            the rectangle's width
	 * @param height
	 *            the rectangle's height
	 * @param bound
	 *            the rectangle's bounding element
	 * @param fill
	 *            the rectangle's filling element
	 */
	void drawRectangleChecked(final int x, final int y, final int width, final int height, final byte bound,
	        final byte fill) {
		int pos = calculatePosition(x, y);
		final int last = pos + (height - 2) * this.width + width;
		Arrays.fill(map, pos, pos + width, bound);
		for (pos += this.width; pos < last; pos += this.width) {
			map[pos] = bound;
			if (width > 2) {
				Arrays.fill(map, pos + 1, pos + width - 1, fill);
			}
			map[pos + width - 1] = bound;
		}
		if (height > 1) {
			Arrays.fill(map, pos, pos + width, bound);
		}
	}

	/**
	 * Draw the rectangle on the map. Arguments should be checked before a call
	 * that are valid.
	 * 
	 * @param x
	 *            the rectangle's x
	 * @param y
	 *            the rectangle's y
	 * @param width
	 *            the rectangle's width
	 * @param height
	 *            the rectangle's height
	 * @param fill
	 *            the rectangle's filling element
	 */
	void drawRectangleChecked(final int x, final int y, final int width, final int height, final byte fill) {
		int pos = calculatePosition(x, y);
		for (int i = 0; i < height; i++) {
			Arrays.fill(map, pos, pos + width, fill);
			pos += this.width;
		}
	}

	/**
	 * Check, that insight rectangle there is an selected element from a list.
	 * Allows to find element on area or check, that area is in collision with
	 * something else. Prefer usage of this method with elements as byte array.
	 * 
	 * @param x
	 *            the rectangle's x
	 * @param y
	 *            the rectangle's y
	 * @param width
	 *            the rectangle's width
	 * @param height
	 *            the rectangle's height
	 * @param elements
	 *            the list of elements to check
	 * @return true, if found any of elements from the list
	 */
	boolean isRectangleWith(final int x, final int y, final int width, final int height, final MapElement... elements) {
		final byte[] checkArray = new byte[elements.length];
		for (int i = 0; i < elements.length; i++) {
			checkArray[i] = elements[i].getMapValue();
		}
		return isRectangleWith(x, y, width, height, checkArray);
	}

	/**
	 * Check, that insight rectangle there is an selected element from a list.
	 * Allows to find element on area or check, that area is in collision with
	 * something else. Prefer usage of this method with elements as byte array.
	 * 
	 * @param area
	 *            the rectangle's area
	 * @param mapValues
	 *            the list of element values to check
	 * @return true, if found any of element value from the list
	 */
	public boolean isRectangleWith(final Area2D area, final byte... mapValues) {
		return isRectangleWith(area.x, area.y, area.width, area.height, mapValues);
	}

	/**
	 * Check, that insight rectangle there is an selected element from a list.
	 * Allows to find element on area or check, that area is in collision with
	 * something else. Prefer usage of this method with elements as byte array.
	 * 
	 * @param x
	 *            the rectangle's x
	 * @param y
	 *            the rectangle's y
	 * @param width
	 *            the rectangle's width
	 * @param height
	 *            the rectangle's height
	 * @param mapValues
	 *            the list of element values to check
	 * @return true, if found any of element value from the list
	 */
	boolean isRectangleWith(final int x, final int y, final int width, final int height, final byte... mapValues) {
		int pos = calculatePosition(x, y);
		for (int j = 0; j < height; j++) {
			for (int i = 0; i < width; i++, pos++) {
				final byte valueFromMap = map[pos];
				for (final byte mapValue : mapValues) {
					if (valueFromMap == mapValue) {
						return true;
					}
				}
			}
			pos -= width;
			pos += this.width;
		}
		return false;
	}

	/**
	 * Calculate position on the map array element based on coordinates of the
	 * point.
	 * 
	 * @param x
	 *            the point's x
	 * @param y
	 *            the point's y
	 * @return position on the map array
	 */
	private int calculatePosition(final int x, final int y) {
		return y * this.width + x;
	}

	/**
	 * Get value by coordinates x and y.
	 * 
	 * @param x
	 *            the x coordinate
	 * @param y
	 *            the y coordinate
	 * @return value at selected coordinates
	 */
	public byte getValueByPosition(final int x, final int y) {
		return map[calculatePosition(x, y)];
	}

	/**
	 * Get value directly from map data.
	 * 
	 * @param i
	 *            position of value
	 * @return value at selected position
	 */
	public byte getValue(final int i) {
		return map[i];
	}

	/**
	 * Set the value on selected position in the map array.
	 * 
	 * @param pos
	 *            the position
	 * @param v
	 *            the value
	 */
	public void setValue(final int pos, final byte v) {
		map[pos] = v;
	}

	/**
	 * Set the value on the point selected by coordinates.
	 * 
	 * @param x
	 *            the point's x
	 * @param y
	 *            the point's y
	 * @param v
	 *            the value
	 */
	public void setValue(final int x, final int y, final byte v) {
		setValue(calculatePosition(x, y), v);
	}

	/**
	 * Set the value on the selected point .
	 * 
	 * @param p
	 *            the point
	 * @param v
	 *            the value
	 */
	public void setValue(final Point2D p, final byte v) {
		setValue(calculatePosition(p.x, p.y), v);
	}

	/**
	 * Convert current map to char map. Map value are converted to characters on
	 * base of valueMapping parameter. Position in valueMapping array is a value
	 * of converted element.
	 * 
	 * @param valueMapping
	 *            conversion map
	 * @return converted character map
	 */
	public char[][] convertToCharMap(final char[] valueMapping) {
		final char[][] result = new char[width][height];
		for (int i = 0; i < map.length; i++) {
			result[i % width][i / width] = valueMapping[map[i]];
		}
		return result;
	}

	/**
	 * Drilling a path on current path. It means, that all elements, which
	 * contains a path are moved to the map, but only if it's not generated
	 * element on the map. If on map is a wall it'll be changed to a door
	 * element.
	 * 
	 * @param drillPathMap
	 *            drilling map of a path
	 */
	public void drillPath(final GeneratedMap drillPathMap) {
		if (drillPathMap == null) {
			return;
		}
		if (drillPathMap.width != width || drillPathMap.height != height) {
			throw new IllegalArgumentException("Wrong size of map");
		}

		final int size = width * height;
		for (int i = 0; i < size; i++) {
			if (drillPathMap.map[i] == MapElement.PATH.getMapValue()) {
				final byte mapElement = map[i];
				if (mapElement == MapElement.NOT_GENERATED.getMapValue()) {
					map[i] = MapElement.PATH.getMapValue();
				} else if (mapElement == MapElement.WALL.getMapValue()) {
					map[i] = MapElement.DOOR.getMapValue();
				}
			}
		}
	}

	@Override
	public GeneratedMap clone() {
		final GeneratedMap result = new GeneratedMap(this.width, this.height);
		System.arraycopy(this.map, 0, result.map, 0, this.width * this.height);
		return result;
	}
}
