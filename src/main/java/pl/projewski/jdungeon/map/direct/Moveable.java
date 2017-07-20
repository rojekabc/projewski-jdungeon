package pl.projewski.jdungeon.map.direct;

/**
 * The moveable interface, which allow to move an object in 2D area by a vector.
 * 
 * @author Piotr Rojewski <rojek_abc@o2.pl>
 *
 * @param <T>
 */
public interface Moveable<T> {
	/**
	 * Move by the vector.
	 * 
	 * @param v
	 *            the 2D vector
	 */
	void move(Vector2D v);

	/**
	 * Calculate vector between two objects.
	 * 
	 * @param target
	 *            the target object
	 * @return calculated move vector.
	 */
	Vector2D vectorTo(T target);
}
