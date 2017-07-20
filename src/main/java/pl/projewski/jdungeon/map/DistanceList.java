package pl.projewski.jdungeon.map;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import pl.projewski.jdungeon.map.direct.Moveable;

/**
 * The list of distance objects.
 * 
 * @author Piotr Rojewski <rojek_abc@o2.pl>
 *
 * @param <T>
 *            template object, which implements a {link {@link Moveable}
 *            interface and is used to calculate {@link Distance} object.
 */
public class DistanceList<T extends Moveable<T>> {
	private final List<Distance<T>> distanceList = new ArrayList<>();

	/**
	 * Append to the list a {@link Distance} object.
	 * 
	 * @param source
	 *            the source object
	 * @param target
	 *            the target object
	 */
	public void appendDistance(final T source, final T target) {
		final Distance<T> distance = new Distance<T>(source, target);
		if (!distanceList.contains(distance)) {
			distanceList.add(distance);
		}
	}

	/**
	 * Remove {@link Distance} object from the list.
	 * 
	 * @param distance
	 *            a distance object
	 */
	public void removeDistance(final Distance<T> distance) {
		distanceList.remove(distance);
	}

	/**
	 * Get a list of a {@link Distance} objects, which has a source or target
	 * object equals to {@link Moveable} object from parameter. The list will be
	 * sorted by calculated distance.
	 * 
	 * @param moveable
	 *            the {@link Moveable} object, which will be a source or a
	 *            target of the {@link Distance} object
	 * @return the list of sorted {@link Distance} objects
	 */
	public List<Distance<T>> getDistanceList(final T moveable) {
		return distanceList.stream() //
		        .filter((final Distance<T> distance) -> distance.getSource() == moveable
		                || distance.getTarget() == moveable) //
		        .sorted(Distance::compareByDistance) //
		        .collect(Collectors.toList());
	}

}
