package pl.projewski.jdungeon.map;

import lombok.Getter;
import pl.projewski.jdungeon.map.direct.Moveable;

/**
 * Generic distance counter to calculate generic distance between two objects,
 * which implements {@link Moveable} interface.
 * 
 * @author Piotr Rojewski <rojek_abc@o2.pl>
 *
 * @param <T>
 *            class implementing {@link Moveable} interface
 */
@Getter
public class Distance<T extends Moveable<T>> {
	private final T source;
	private final T target;
	private final int distance;

	/**
	 * Create object keeping both objects as source and target and calculated
	 * value of a distance between them.
	 * 
	 * @param source
	 *            source object
	 * @param target
	 *            target object
	 */
	public Distance(final T source, final T target) {
		this.source = source;
		this.target = target;
		this.distance = source.vectorTo(target).length();
	}

	/**
	 * Check distance objects are equals. Only if source and target objects (as
	 * a reference) are same on any side of a distance.
	 * 
	 * @param distance
	 *            distance object to check is it equal
	 * @return true, if they are equals
	 */
	public boolean equals(final Distance<T> distance) {
		// comparison on base of object references
		return (distance.source == source && distance.target == target)
		        || (distance.source == target && distance.target == source);
	}

	/**
	 * Static method to compare two distance objects by distance. That with
	 * lower distance will be first.
	 * 
	 * @param a
	 *            fisrst distance object
	 * @param b
	 *            second distance object
	 * @return result in meaning of {@link Comparable} interface
	 */
	public static <Y extends Moveable<Y>> int compareByDistance(final Distance<Y> a, final Distance<Y> b) {
		return Integer.compare(a.distance, b.distance);
	}

}
