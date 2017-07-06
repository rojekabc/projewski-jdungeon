package pl.projewski.jdungeon.map;

import org.junit.Assert;
import org.junit.Test;

public class GeneratedMapTest {

	@Test
	public void testInit() {
		GeneratedMap map = new GeneratedMap(10, 10);
		for (int j = 0; j < map.getHeight(); j++) {
			for (int i = 0; i < map.getWidth(); i++) {
				Assert.assertEquals(MapElement.NOT_GENERATED.getMapValue(), map.getValueByPosition(i, j));
			}
		}
	}

	@Test
	public void testFill() {
		GeneratedMap map = new GeneratedMap(10, 10);
		map.fill(MapElement.ROOM);
		for (int j = 0; j < map.getHeight(); j++) {
			for (int i = 0; i < map.getWidth(); i++) {
				Assert.assertEquals(MapElement.ROOM.getMapValue(), map.getValueByPosition(i, j));
			}
		}
	}

	@Test
	public void testRectangleBounded_1x1() {
		GeneratedMap map = new GeneratedMap(10, 10);
		map.drawRectangle(1, 1, 1, 1, MapElement.WALL, MapElement.ROOM);
		for (int j = 0; j < map.getHeight(); j++) {
			for (int i = 0; i < map.getWidth(); i++) {
				if (i == 1 && j == 1) {
					Assert.assertEquals(MapElement.WALL.getMapValue(), map.getValueByPosition(i, j));
				} else {
					Assert.assertEquals(MapElement.NOT_GENERATED.getMapValue(), map.getValueByPosition(i, j));
				}
			}
		}
	}

	@Test
	public void testRectangleBounded_2x2() {
		GeneratedMap map = new GeneratedMap(10, 10);
		map.drawRectangle(1, 1, 2, 2, MapElement.WALL, MapElement.ROOM);
		for (int j = 0; j < map.getHeight(); j++) {
			for (int i = 0; i < map.getWidth(); i++) {
				if (i >= 1 && i <= 2 && j >= 1 && j <= 2) {
					Assert.assertEquals(MapElement.WALL.getMapValue(), map.getValueByPosition(i, j));
				} else {
					Assert.assertEquals(MapElement.NOT_GENERATED.getMapValue(), map.getValueByPosition(i, j));
				}
			}
		}
	}

	@Test
	public void testRectangleBounded_3x3() {
		GeneratedMap map = new GeneratedMap(10, 10);
		map.drawRectangle(1, 1, 3, 3, MapElement.WALL, MapElement.ROOM);
		for (int j = 0; j < map.getHeight(); j++) {
			for (int i = 0; i < map.getWidth(); i++) {
				if (i == 2 && j == 2) {
					Assert.assertEquals(MapElement.ROOM.getMapValue(), map.getValueByPosition(i, j));
				} else if (i >= 1 && i <= 3 && j >= 1 && j <= 3) {
					Assert.assertEquals(MapElement.WALL.getMapValue(), map.getValueByPosition(i, j));
				} else {
					Assert.assertEquals(MapElement.NOT_GENERATED.getMapValue(), map.getValueByPosition(i, j));
				}
			}
		}
	}

	@Test
	public void testRectangleBounded_2x3() {
		GeneratedMap map = new GeneratedMap(10, 10);
		map.drawRectangle(1, 1, 2, 3, MapElement.WALL, MapElement.ROOM);
		for (int j = 0; j < map.getHeight(); j++) {
			for (int i = 0; i < map.getWidth(); i++) {
				if (i >= 1 && i <= 2 && j >= 1 && j <= 3) {
					Assert.assertEquals(MapElement.WALL.getMapValue(), map.getValueByPosition(i, j));
				} else {
					Assert.assertEquals(MapElement.NOT_GENERATED.getMapValue(), map.getValueByPosition(i, j));
				}
			}
		}
	}

	@Test
	public void testRectangleBounded_3x2() {
		GeneratedMap map = new GeneratedMap(10, 10);
		map.drawRectangle(1, 1, 3, 2, MapElement.WALL, MapElement.ROOM);
		for (int j = 0; j < map.getHeight(); j++) {
			for (int i = 0; i < map.getWidth(); i++) {
				if (i >= 1 && i <= 3 && j >= 1 && j <= 2) {
					Assert.assertEquals(MapElement.WALL.getMapValue(), map.getValueByPosition(i, j));
				} else {
					Assert.assertEquals(MapElement.NOT_GENERATED.getMapValue(), map.getValueByPosition(i, j));
				}
			}
		}
	}

	@Test
	public void testRectangleBounded_10x10() {
		GeneratedMap map = new GeneratedMap(10, 10);
		map.drawRectangle(0, 0, 10, 10, MapElement.WALL, MapElement.ROOM);
		for (int j = 0; j < map.getHeight(); j++) {
			for (int i = 0; i < map.getWidth(); i++) {
				if (i >= 1 && i <= 8 && j >= 1 && j <= 8) {
					Assert.assertEquals(MapElement.ROOM.getMapValue(), map.getValueByPosition(i, j));
				} else if (i >= 0 && i <= 9 && j >= 0 && j <= 9) {
					Assert.assertEquals(MapElement.WALL.getMapValue(), map.getValueByPosition(i, j));
				}
			}
		}
	}

	@Test
	public void testRectangleBounded_8x8() {
		GeneratedMap map = new GeneratedMap(10, 10);
		map.drawRectangle(1, 1, 8, 8, MapElement.WALL, MapElement.ROOM);
		for (int j = 0; j < map.getHeight(); j++) {
			for (int i = 0; i < map.getWidth(); i++) {
				if (i >= 2 && i <= 7 && j >= 2 && j <= 7) {
					Assert.assertEquals(MapElement.ROOM.getMapValue(), map.getValueByPosition(i, j));
				} else if (i >= 1 && i <= 8 && j >= 1 && j <= 8) {
					Assert.assertEquals(MapElement.WALL.getMapValue(), map.getValueByPosition(i, j));
				} else {
					Assert.assertEquals(MapElement.NOT_GENERATED.getMapValue(), map.getValueByPosition(i, j));
				}
			}
		}
	}

	@Test
	public void testRectangle_1x1() {
		GeneratedMap map = new GeneratedMap(10, 10);
		map.drawRectangle(1, 1, 1, 1, MapElement.ROOM);
		for (int j = 0; j < map.getHeight(); j++) {
			for (int i = 0; i < map.getWidth(); i++) {
				if (i == 1 && j == 1) {
					Assert.assertEquals(MapElement.ROOM.getMapValue(), map.getValueByPosition(i, j));
				} else {
					Assert.assertEquals(MapElement.NOT_GENERATED.getMapValue(), map.getValueByPosition(i, j));
				}
			}
		}
	}

	@Test
	public void testRectangle_2x2() {
		GeneratedMap map = new GeneratedMap(10, 10);
		map.drawRectangle(1, 1, 2, 2, MapElement.ROOM);
		for (int j = 0; j < map.getHeight(); j++) {
			for (int i = 0; i < map.getWidth(); i++) {
				if (i >= 1 && i <= 2 && j >= 1 && j <= 2) {
					Assert.assertEquals(MapElement.ROOM.getMapValue(), map.getValueByPosition(i, j));
				} else {
					Assert.assertEquals(MapElement.NOT_GENERATED.getMapValue(), map.getValueByPosition(i, j));
				}
			}
		}
	}

	@Test
	public void testRectangle_3x3() {
		GeneratedMap map = new GeneratedMap(10, 10);
		map.drawRectangle(1, 1, 3, 3, MapElement.ROOM);
		for (int j = 0; j < map.getHeight(); j++) {
			for (int i = 0; i < map.getWidth(); i++) {
				if (i >= 1 && i <= 3 && j >= 1 && j <= 3) {
					Assert.assertEquals(MapElement.ROOM.getMapValue(), map.getValueByPosition(i, j));
				} else {
					Assert.assertEquals(MapElement.NOT_GENERATED.getMapValue(), map.getValueByPosition(i, j));
				}
			}
		}
	}

	@Test
	public void testRectangle_10x10() {
		GeneratedMap map = new GeneratedMap(10, 10);
		map.drawRectangle(0, 0, 10, 10, MapElement.ROOM);
		for (int j = 0; j < map.getHeight(); j++) {
			for (int i = 0; i < map.getWidth(); i++) {
				if (i >= 0 && i <= 9 && j >= 0 && j <= 9) {
					Assert.assertEquals(MapElement.ROOM.getMapValue(), map.getValueByPosition(i, j));
				}
			}
		}
	}

	@Test
	public void testIsGeneratedWith_emptyMap() {
		GeneratedMap map = new GeneratedMap(10, 10);
		Assert.assertFalse(map.isRectangleWith(0, 0, 10, 10, MapElement.PATH, MapElement.ROOM, MapElement.WALL));
		Assert.assertTrue(map.isRectangleWith(0, 0, 10, 10, MapElement.NOT_GENERATED));
		Assert.assertFalse(map.isRectangleWith(0, 0, 10, 10, MapElement.PATH.getMapValue(),
		        MapElement.ROOM.getMapValue(), MapElement.WALL.getMapValue()));
		Assert.assertTrue(map.isRectangleWith(0, 0, 10, 10, MapElement.NOT_GENERATED.getMapValue()));
	}

	@Test
	public void testIsGeneratedWith_room() {
		GeneratedMap map = new GeneratedMap(10, 10);
		map.drawRectangle(1, 1, 1, 1, MapElement.ROOM);
		Assert.assertTrue(map.isRectangleWith(0, 0, 10, 10, MapElement.PATH, MapElement.ROOM, MapElement.WALL));
		Assert.assertFalse(map.isRectangleWith(0, 0, 10, 10, MapElement.PATH, MapElement.WALL));
		Assert.assertTrue(map.isRectangleWith(0, 0, 10, 10, MapElement.NOT_GENERATED));
		Assert.assertTrue(map.isRectangleWith(0, 0, 10, 10, MapElement.PATH.getMapValue(),
		        MapElement.ROOM.getMapValue(), MapElement.WALL.getMapValue()));
		Assert.assertFalse(
		        map.isRectangleWith(0, 0, 10, 10, MapElement.PATH.getMapValue(), MapElement.WALL.getMapValue()));
		Assert.assertTrue(map.isRectangleWith(0, 0, 10, 10, MapElement.NOT_GENERATED.getMapValue()));
		Assert.assertFalse(map.isRectangleWith(0, 2, 10, 8, MapElement.PATH, MapElement.ROOM, MapElement.WALL));
		Assert.assertFalse(map.isRectangleWith(2, 0, 8, 10, MapElement.PATH, MapElement.ROOM, MapElement.WALL));
		Assert.assertTrue(map.isRectangleWith(1, 1, 1, 1, MapElement.ROOM));
		Assert.assertFalse(map.isRectangleWith(0, 0, 10, 1, MapElement.PATH, MapElement.ROOM, MapElement.WALL));
		Assert.assertFalse(map.isRectangleWith(0, 0, 1, 10, MapElement.PATH, MapElement.ROOM, MapElement.WALL));
	}

	@Test
	public void testIsGeneratedWith_typicalCheck() {
		GeneratedMap map = new GeneratedMap(10, 10);
		map.drawRectangle(1, 1, 5, 7, MapElement.WALL, MapElement.ROOM);
		Assert.assertTrue(map.isRectangleWith(0, 0, 2, 2, MapElement.PATH, MapElement.ROOM, MapElement.WALL));
		Assert.assertTrue(map.isRectangleWith(3, 4, 4, 5, MapElement.PATH, MapElement.ROOM, MapElement.WALL));
		Assert.assertFalse(map.isRectangleWith(6, 2, 4, 5, MapElement.PATH, MapElement.ROOM, MapElement.WALL));
		Assert.assertFalse(map.isRectangleWith(0, 0, 1, 10, MapElement.PATH, MapElement.ROOM, MapElement.WALL));
	}
}
