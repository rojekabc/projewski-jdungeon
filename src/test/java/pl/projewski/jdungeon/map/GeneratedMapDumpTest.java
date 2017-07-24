package pl.projewski.jdungeon.map;

import org.junit.Test;

import pl.projewski.jdungeon.DumpHelper;
import pl.projewski.jdungeon.map.generator.SimpleRoomGenerator;

public class GeneratedMapDumpTest {

	@Test
	public void testRectangleBounded_full() {
		final GeneratedMap map = new GeneratedMap(10, 10);
		map.drawRectangle(0, 0, 10, 10, MapElement.WALL, MapElement.ROOM);
		DumpHelper.dumpMap(map);
	}

	@Test
	public void testRectangleBounded_inside() {
		final GeneratedMap map = new GeneratedMap(10, 10);
		map.drawRectangle(1, 1, 8, 8, MapElement.WALL, MapElement.ROOM);
		DumpHelper.dumpMap(map);
	}

	@Test
	public void testSimpleRoomGenerator() {
		final SimpleRoomGenerator generator = new SimpleRoomGenerator();
		final GeneratedMap map = generator.generate(18, 12);
		DumpHelper.dumpMap(map);
	}

}
