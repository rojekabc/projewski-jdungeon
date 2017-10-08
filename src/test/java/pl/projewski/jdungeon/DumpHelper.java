package pl.projewski.jdungeon;

import pl.projewski.jdungeon.map.GeneratedMap;
import pl.projewski.jdungeon.map.MapElement;

public class DumpHelper {

	public static void dumpMap(final GeneratedMap map, final String description) {
		if (description != null) {
			System.out.println(description);
		}
		final char[] valueMapping = new char[MapElement.values().length];
		valueMapping[MapElement.NOT_GENERATED.getMapValue()] = '#';
		valueMapping[MapElement.PATH.getMapValue()] = '.';
		valueMapping[MapElement.ROOM.getMapValue()] = '.';
		valueMapping[MapElement.WALL.getMapValue()] = '#';
		valueMapping[MapElement.DOOR.getMapValue()] = '+';

		final char[][] charMap = map.convertToCharMap(valueMapping);
		final int height = map.height;
		final int width = map.width;
		for (int j = 0; j < height; j++) {
			for (int i = 0; i < width; i++) {
				System.out.print(charMap[i][j]);
			}
			System.out.println();
		}

	}

	public static void dumpMap(final GeneratedMap map) {
		dumpMap(map, null);
	}

	public static void dumpMapValues(final GeneratedMap map) {
		final int width = map.width;
		for (int j = 0; j < map.height; j++) {
			for (int i = 0; i < width; i++) {
				final byte b = map.getValue(j * width + i);
				if (b < 0x10) {
					System.out.print('0');
					System.out.print(Integer.toHexString(b & 0xFF));
				} else {
					System.out.print(Integer.toHexString(b & 0xFF));
				}
				System.out.print(' ');
			}
			System.out.println();
		}
	}

}
