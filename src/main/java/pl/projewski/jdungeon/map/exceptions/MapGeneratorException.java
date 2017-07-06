package pl.projewski.jdungeon.map.exceptions;

/**
 * Root of map generator exceptions.
 * 
 * @author Piotr Rojewski <rojek_abc@o2.pl>
 */
public class MapGeneratorException extends RuntimeException {

	private static final long serialVersionUID = 6447237145480357783L;

	public MapGeneratorException() {
	}

	public MapGeneratorException(String message) {
		super(message);
	}

	public MapGeneratorException(Throwable cause) {
		super(cause);
	}

	public MapGeneratorException(String message, Throwable cause) {
		super(message, cause);
	}

	public MapGeneratorException(String message, Throwable cause, boolean enableSuppression,
	        boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
