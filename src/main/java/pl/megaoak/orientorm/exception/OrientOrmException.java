package pl.megaoak.orientorm.exception;

public class OrientOrmException extends RuntimeException {
	public OrientOrmException() {
	}

	public OrientOrmException(String message) {
		super(message);
	}

	public OrientOrmException(String message, Throwable cause) {
		super(message, cause);
	}

	public OrientOrmException(Throwable cause) {
		super(cause);
	}
}
