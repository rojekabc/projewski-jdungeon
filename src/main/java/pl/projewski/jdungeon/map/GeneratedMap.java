package pl.projewski.jdungeon.map;

import java.util.Arrays;

import lombok.Getter;
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
public class GeneratedMap {
	/**
	 * Container of map data.
	 */
	byte[] map;
	@Getter
	private int width;
	@Getter
	private int height;

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
	public GeneratedMap(int width, int height) {
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
	void fill(MapElement element) {
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
	public void drawRectangle(int x, int y, int width, int height, MapElement bound, MapElement fill) {
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

	void drawRectangle(Area2D area, MapElement bound, MapElement fill) {
		drawRectangle(area.x, area.y, area.width, area.height, bound, fill);
	}

	void drawRectangle(int x, int y, int width, int height, MapElement fill) {
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

	void drawRectangle(Area2D area, MapElement fill) {
		drawRectangle(area.x, area.y, area.width, area.height, fill);
	}

	void drawRectangleChecked(int x, int y, int width, int height, byte bound, byte fill) {
		int pos = calculatePosition(x, y);
		int last = pos + (height - 2) * this.width + width;
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

	void drawRectangleChecked(int x, int y, int width, int height, byte fill) {
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
	 * @param y
	 * @param width
	 * @param height
	 * @param element
	 * @return
	 */
	boolean isRectangleWith(int x, int y, int width, int height, MapElement... elements) {
		byte[] checkArray = new byte[elements.length];
		for (int i = 0; i < elements.length; i++) {
			checkArray[i] = elements[i].getMapValue();
		}
		return isRectangleWith(x, y, width, height, checkArray);
	}

	boolean isRectangleWith(Area2D area, byte... mapValues) {
		return isRectangleWith(area.x, area.y, area.width, area.height, mapValues);
	}

	boolean isRectangleWith(int x, int y, int width, int height, byte... mapValues) {
		int pos = calculatePosition(x, y);
		for (int j = 0; j < height; j++) {
			for (int i = 0; i < width; i++, pos++) {
				byte valueFromMap = map[pos];
				for (byte mapValue : mapValues) {
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

	private int calculatePosition(int x, int y) {
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
	public byte getValueByPosition(int x, int y) {
		return map[calculatePosition(x, y)];
	}

	/**
	 * Get value directly from map data.
	 * 
	 * @param i
	 *            position of value
	 * @return value at selected position
	 */
	public byte getValue(int i) {
		return map[i];
	}

	public void setValue(int pos, byte v) {
		map[pos] = v;
	}

	public void setValue(int x, int y, byte v) {
		setValue(calculatePosition(x, y), v);
	}

	public void setValue(Point2D p, byte v) {
		setValue(calculatePosition(p.x, p.y), v);
	}

	public char[][] convertToCharMap(char[] valueMapping) {
		char[][] result = new char[width][height];
		for (int i = 0; i < map.length; i++) {
			result[i % width][i / width] = valueMapping[map[i]];
		}
		return result;
	}

	/**
	 * Append elements from drilling map to current map. Elements are move only
	 * to not generated elements of current map. Size of map has to be same.
	 * 
	 * @param drillMap
	 *            drilling map
	 */
	public void drillMap(GeneratedMap drillMap) {
		if (drillMap == null) {
			return;
		}
		if (drillMap.width != width || drillMap.height != height) {
			throw new IllegalArgumentException("Wrong size of map");
		}

		int size = width * height;
		for (int i = 0; i < size; i++) {
			if (map[i] == MapElement.NOT_GENERATED.getMapValue()
			        && drillMap.map[i] != MapElement.NOT_GENERATED.getMapValue()) {
				map[i] = drillMap.map[i];
			}

		}
	}

}
