package pl.projewski.jdungeon.map;

import java.util.Properties;
import java.util.Random;

/**
 * Abstract class to generate a map. It'll keep and manage properties.
 * 
 * @author Piotr Rojewski <rojek_abc@o2.pl>
 *
 */
public abstract class AbstractMapGenerator implements IMapGenerator {

	/** Properties of a map generator. */
	private final Properties props = new Properties();

	@Override
	public Properties getMapProperties() {
		return props;
	}

	@Override
	public GeneratedMap generate(final int width, final int height) {
		return generate(width, height, System.currentTimeMillis());
	}

	/**
	 * Set a value of the property.
	 * 
	 * @param property
	 *            the generator property
	 * @param value
	 *            the value
	 */
	public void setProperty(final GeneratorProperty property, final String value) {
		props.setProperty(property.getPropertyName(), value);
	}

	/**
	 * Get a value of property as int.
	 * 
	 * @param key
	 *            the generator's property
	 * @return value as int type
	 * @throws {@link
	 *             IllegalArgumentException} there's no property with such key
	 */
	public int getAsInt(final GeneratorProperty key) {
		if (!props.containsKey(key.getPropertyName())) {
			throw new IllegalArgumentException("key");
		}
		final String value = props.getProperty(key.getPropertyName());
		return Integer.valueOf(value);
	}

	/**
	 * Get a value of property as int type. If it's not on a list or it's a null
	 * value the defaultValue will be returned.
	 * 
	 * @param key
	 *            the generator's property
	 * @param defaultValue
	 *            default value
	 * @return the value of a property
	 */
	public int getAsInt(final GeneratorProperty key, final int defaultValue) {
		final String value = props.getProperty(key.getPropertyName());
		if (value == null) {
			return defaultValue;
		}
		return Integer.valueOf(value);
	}

	/**
	 * Get next integer from random number generator. Number will be from area
	 * <min, max>. (min and max value are included).
	 * 
	 * @param random
	 *            random number generator
	 * @param min
	 *            minimum value
	 * @param max
	 *            maximum value
	 * @return result
	 * @throws {@link
	 *             IllegalArgumentException} if value of maximum is less than
	 *             value of minimum
	 */
	public static int nextInt(final Random random, final int min, final int max) {
		if (max < min) {
			throw new IllegalArgumentException("max");
		}
		if (max == min) {
			return min;
		}
		return min + random.nextInt(max - min + 1);
	}
}
