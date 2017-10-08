package pl.projewski.jdungeon.map.direct;

import java.util.Random;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import pl.projewski.jdungeon.map.AbstractMapGenerator;

/**
 * Direct access class for rectangle. It implements {@link Cloneable} and
 * {@link Moveable} interfaces.
 * 
 * @author Piotr Rojewski <rojek_abc@o2.pl>
 *
 */
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Area2D implements Cloneable, Moveable<Area2D> {
	/** The x coordinate. */
	public int x;
	/** The y coordinate. */
	public int y;
	/** The width. */
	public int width;
	/** The height. */
	public int height;

	@Override
	public void move(final Vector2D v) {
		x += v.dx;
		y += v.dy;
	}

	/**
	 * Get common rectangle area.
	 * 
	 * @param area
	 *            the area
	 * @return the common area or null, if not found
	 */
	public Area2D getCommonArea(final Area2D area) {
		final int xLeft = Math.max(this.x, area.x);
		final int xRight = Math.min(this.x + this.width, area.x + area.width);
		if (xRight <= xLeft) {
			return null;
		}
		final int yTop = Math.max(this.y, area.y);
		final int yBottom = Math.min(this.y + this.height, area.y + area.height);
		if (yBottom <= yTop) {
			return null;
		}
		return new Area2D(xLeft, yTop, xRight - xLeft, yBottom - yTop);
	}

	/**
	 * Check, that this area is inside another rectangle area.
	 * 
	 * @param x
	 *            the rectangle area's x coordinate
	 * @param y
	 *            the rectangle area's y coordinate
	 * @param wdth
	 *            the rectangle area's width
	 * @param height
	 *            the rectangle area's height
	 * @return true, if the this whole area is inside of area from parameters
	 */
	public boolean isInside(final int x, final int y, final int width, final int height) {
		return (this.x >= x) && (this.y >= y) && (this.x + this.width <= width) && (this.y + this.height <= height);
	}

	/**
	 * Check, that this area is inside another rectangle area.
	 * 
	 * @param area
	 *            the rectangle area
	 * @return true, if the this whole area is inside of area from parameter
	 */
	public boolean isInside(final Area2D area) {
		return isInside(area.x, area.y, area.width, area.height);
	}

	/**
	 * Get a center point. It's calculated in integer values !
	 * 
	 * @return the center point of this area
	 */
	public Point2D center() {
		return new Point2D(this.x + this.width / 2, this.y + this.height / 2);
	}

	/**
	 * Get random point inside area.
	 * 
	 * @param random
	 *            random engine
	 * @return random point
	 */
	public Point2D randomPoint(final Random random) {
		return new Point2D( //
		        AbstractMapGenerator.nextInt(random, this.x, this.x + this.width - 1),
		        AbstractMapGenerator.nextInt(random, this.y, this.y + this.height - 1));
	}

	@Override
	public Vector2D vectorTo(final Area2D area) {
		return center().vectorTo(area.center());
	}

	@Override
	public Area2D clone() {
		return new Area2D(x, y, width, height);
	}

	/**
	 * Growing resize rectangle in directions specified by a vector.
	 * 
	 * @param v
	 *            the resize direction
	 */
	public void resize(final Vector2D v) {
		if (v.dx < 0) {
			this.x += v.dx;
		} else {
			this.width += v.dx;
		}

		if (v.dy < 0) {
			this.y += v.dy;
		} else {
			this.height += v.dy;
		}
	}

	/**
	 * Resize back rectangle from direction specified by a vector.
	 * 
	 * @param v
	 *            the resize direction
	 */
	public void resizeBack(final Vector2D v) {
		if (v.dx < 0) {
			this.x -= v.dx;
		} else {
			this.width -= v.dx;
		}

		if (v.dy < 0) {
			this.y -= v.dy;
		} else {
			this.height -= v.dy;
		}
	}
}
