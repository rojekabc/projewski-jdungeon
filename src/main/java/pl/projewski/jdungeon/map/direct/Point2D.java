package pl.projewski.jdungeon.map.direct;

import lombok.AllArgsConstructor;
import lombok.ToString;

/**
 * Direct access to 2D point. Implements {@link Cloneable} and {@link Moveable}
 * interfaces.
 * 
 * @author Piotr Rojewski <rojek_abc@o2.pl>
 *
 */
@AllArgsConstructor
@ToString
public class Point2D implements Cloneable, Moveable<Point2D> {
	/** The x coordinate. */
	public int x;
	/** The y coordinate. */
	public int y;

	/**
	 * Calculate a distance from this point to point from argument.
	 * 
	 * @param point
	 * @return
	 */
	@Override
	public Vector2D vectorTo(final Point2D point) {
		return new Vector2D(point.x - this.x, point.y - this.y);
	}

	/**
	 * Check that point is inside area.
	 * 
	 * @param area
	 * @return
	 */
	public boolean isInsideArea(final Area2D area) {
		return Point2D.isInsideArea(this.x, this.y, area.x, area.y, area.width, area.height);
	}

	/**
	 * Check that point is inside area.
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @return
	 */
	public boolean isInsideArea(final int x, final int y, final int width, final int height) {
		return Point2D.isInsideArea(this.x, this.y, x, y, width, height);
	}

	/**
	 * Check that point is inside area.
	 * 
	 * @param px
	 * @param py
	 * @param ax
	 * @param ay
	 * @param awidth
	 * @param aheight
	 * @return
	 */
	private static boolean isInsideArea(final int px, final int py, final int ax, final int ay, final int awidth,
	        final int aheight) {
		return px >= ax && px < ax + awidth && py >= ay && py < ay + aheight;
	}

	/**
	 * Check that point after move by vector is inside area.
	 * 
	 * @param area
	 * @param vector
	 * @return
	 */
	public boolean isInsideAreaMoveVector(final Area2D area, final Vector2D vector) {
		return Point2D.isInsideArea(this.x + vector.dx, this.y + vector.dy, area.x, area.y, area.width, area.height);
	}

	@Override
	public void move(final Vector2D vector) {
		this.x += vector.dx;
		this.y += vector.dy;
	}

	/**
	 * Move back by vector. It move a point with negative vector.
	 * 
	 * @param vector
	 */
	public void moveBack(final Vector2D vector) {
		this.x -= vector.dx;
		this.y -= vector.dy;
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj == null) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		if (obj instanceof Point2D) {
			final Point2D p = (Point2D) obj;
			return p.x == x && p.y == y;
		}
		return false;
	}

	@Override
	public Point2D clone() {
		return new Point2D(x, y);
	}

}
