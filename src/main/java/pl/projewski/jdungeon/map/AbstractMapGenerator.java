package pl.projewski.jdungeon.map;

import java.util.Properties;
import java.util.Random;

abstract class AbstractMapGenerator implements IMapGenerator {

	Properties props = new Properties();

	public Properties getMapProperties() {
		return props;
	}

	public GeneratedMap generate(int width, int height) {
		return generate(width, height, System.currentTimeMillis());
	}

	public int getAsInt(String key) {
		String value = props.getProperty(key);
		if (value == null) {
			throw new IllegalArgumentException("key");
		}
		return Integer.valueOf(value);
	}

	public int getAsInt(String key, int defaultValue) {
		String value = props.getProperty(key);
		if (value == null) {
			return defaultValue;
		}
		return Integer.valueOf(value);
	}

	public int nextInt(Random random, int min, int max) {
		if (max < min) {
			throw new IllegalArgumentException("max");
		}
		if (max == min) {
			return min;
		}
		return min + random.nextInt(max - min + 1);
	}
}
