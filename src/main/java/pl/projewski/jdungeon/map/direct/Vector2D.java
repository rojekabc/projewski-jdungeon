package pl.projewski.jdungeon.map.direct;

import lombok.AllArgsConstructor;
import lombok.ToString;

/**
 * Offset vector values in 2D.
 * 
 * @author Piotr Rojewski <rojek_abc@o2.pl>
 *
 */
@AllArgsConstructor
@ToString
public class Vector2D {
	public int dx;
	public int dy;

	public void add(Vector2D v) {
		dx += v.dx;
		dy += v.dy;
	}
}
