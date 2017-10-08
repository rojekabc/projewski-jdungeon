package pl.projewski.jdungeon.map.direct;

import lombok.AllArgsConstructor;
import lombok.ToString;

/**
 * The direct access vector values in 2D. Allows to move object.
 * 
 * @author Piotr Rojewski <rojek_abc@o2.pl>
 *
 */
@AllArgsConstructor
@ToString
public class Vector2D {
	public final static Vector2D oneRight = new Vector2D(1, 0);
	public final static Vector2D oneLeft = new Vector2D(-1, 0);
	public final static Vector2D oneUp = new Vector2D(0, -1);
	public final static Vector2D oneDown = new Vector2D(0, 1);
	public final static Vector2D oneUpRight = new Vector2D(oneUp, oneRight);
	public final static Vector2D oneUpLeft = new Vector2D(oneUp, oneLeft);
	public final static Vector2D oneDownRight = new Vector2D(oneDown, oneRight);
	public final static Vector2D oneDownLeft = new Vector2D(oneDown, oneLeft);
	public final static Vector2D[] baseDirections = { oneRight, oneLeft, oneUp, oneDown };
	public final static Vector2D[] allDirections = { oneRight, oneLeft, oneUp, oneDown, oneUpLeft, oneUpRight,
	        oneDownLeft, oneDownRight };

	/** The vector offset in x direction. */
	public int dx = 0;
	/** The vector offset in y direction. */
	public int dy = 0;

	/**
	 * Create a vector, which will be a sum of a list of vectors.
	 * 
	 * @param vectors
	 *            the vector list
	 */
	public Vector2D(final Vector2D... vectors) {
		for (final Vector2D vector : vectors) {
			add(vector);
		}
	}

	/**
	 * Add the vector.
	 * 
	 * @param v
	 *            the vector to add
	 */
	public void add(final Vector2D v) {
		dx += v.dx;
		dy += v.dy;
	}

	/**
	 * Calculate the length of vector as an integer value.
	 * 
	 * @return length of vector
	 */
	public int length() {
		return Double.valueOf(Math.sqrt(dx * dx + dy * dy)).intValue();
	}
}
