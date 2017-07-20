package pl.projewski.jdungeon.map;

import lombok.Getter;

public enum SimpleRoomGeneratorProperty implements GeneratorProperty {

	/** The minimum width of generated room. */
	MAP_MINIMUM_WIDTH("map.width.min"),
	/** The minimum height of generated room. */
	MAP_MINIMUM_HEIGHT("map.height.min"),
	/** The maximum width of generated room. */
	MAP_MAXIMUM_WIDTH("map.width.max"),
	/** The maximum height of generated room. */
	MAP_MAXIMUM_HEIGHT("map.height.max"),
	/** Target of room ammount to be genrated (if it's possible). */
	MAP_ROOM_AMOUNT("map.room.amount");

	@Getter
	private final String propertyName;

	/** Create instance with property name. */
	SimpleRoomGeneratorProperty(final String name) {
		this.propertyName = name;
	}

}
