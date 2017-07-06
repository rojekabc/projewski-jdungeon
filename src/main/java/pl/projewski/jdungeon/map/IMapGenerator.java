package pl.projewski.jdungeon.map;

import java.util.Properties;

/**
 * The map generator interface.
 * 
 * @author Piotr Rojewski <rojek_abc@o2.pl>
 *
 */
public interface IMapGenerator {
	/**
	 * List properties of a map, which will be used to generate it.
	 * 
	 * @return set of properties with value
	 */
	Properties getMapProperties();

	/**
	 * Generate a map using specified randomSeed. Properties, which are set for
	 * generator will be used.
	 * 
	 * @param width
	 *            the width of generated map
	 * @param height
	 *            the height of generated map
	 * @param randomSeed
	 *            the random seed, used to generate map.
	 * @return the generated map object
	 */
	GeneratedMap generate(int width, int height, long randomSeed);

	/**
	 * Generate a map using specified randomSeed. Properties, which are set for
	 * generator will be used. Random seed used for generation will come from
	 * current system time milliseconds.
	 * 
	 * @param width
	 *            the width of generated map
	 * @param height
	 *            the height of generated map
	 * @return the generated map object
	 */
	GeneratedMap generate(int width, int height);

}
