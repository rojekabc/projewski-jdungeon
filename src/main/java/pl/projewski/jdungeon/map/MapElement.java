package pl.projewski.jdungeon.map;

/**
 * The enumeration with possible values on map. Allow to keep max 256 element,
 * and I think it should be enough set for basic map generation.
 * 
 * @author Piotr Rojewski <rojek_abc@o2.pl>
 *
 */
public enum MapElement {
	/** Nothing was generated here. */
	NOT_GENERATED,
	/** Room area. */
	ROOM,
	/** Path area. */
	PATH,
	/** Wall around room or path. */
	WALL,
	/** Door element. */
	DOOR;

	/**
	 * Get byte value assigned to map element.
	 * 
	 * @return byte value
	 */
	public byte getMapValue() {
		return (byte) ordinal();
	}

	/**
	 * Get instance of map element on base of byte value.
	 * 
	 * @param mapValue
	 *            byte value
	 * @return map element
	 */
	public MapElement getFromMapValue(final byte mapValue) {
		return values()[mapValue & 0xFF];
	}
}
