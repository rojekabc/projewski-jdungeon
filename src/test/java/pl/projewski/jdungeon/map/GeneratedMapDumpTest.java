package pl.projewski.jdungeon.map;

import org.junit.Test;

import pl.projewski.jdungeon.DumpHelper;

public class GeneratedMapDumpTest {

	@Test
	public void testRectangleBounded_full() {
		GeneratedMap map = new GeneratedMap(10, 10);
		map.drawRectangle(0, 0, 10, 10, MapElement.WALL, MapElement.ROOM);
		DumpHelper.dumpMap(map);
	}

	@Test
	public void testRectangleBounded_inside() {
		GeneratedMap map = new GeneratedMap(10, 10);
		map.drawRectangle(1, 1, 8, 8, MapElement.WALL, MapElement.ROOM);
		DumpHelper.dumpMap(map);
	}

	@Test
	public void testSimpleRoomGenerator() {
		SimpleRoomGenerator generator = new SimpleRoomGenerator();
		GeneratedMap map = generator.generate(18, 12);
		DumpHelper.dumpMap(map);
	}

}
