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
	/** The vector offset in x direction. */
	public int dx;
	/** The vector offset in y direction. */
	public int dy;

	public void add(final Vector2D v) {
		dx += v.dx;
		dy += v.dy;
	}

	public int length() {
		return Double.valueOf(Math.sqrt(dx * dx + dy * dy)).intValue();
	}
}
