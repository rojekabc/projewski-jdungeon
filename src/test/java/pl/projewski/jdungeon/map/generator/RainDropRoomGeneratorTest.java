package pl.projewski.jdungeon.map.generator;

import org.junit.Test;

import pl.projewski.jdungeon.DumpHelper;
import pl.projewski.jdungeon.map.GeneratedMap;

public class RainDropRoomGeneratorTest {

	@Test
	public void test() {
		final RainDropRoomGenerator generator = new RainDropRoomGenerator();
		final GeneratedMap map = generator.generate(18, 12);
		DumpHelper.dumpMap(map);
	}
}
