package pl.megaoak.orientorm.exception;

public class ObjectMappingException extends OrientOrmException {
    public ObjectMappingException(String message) {
        super(message);
    }

    public ObjectMappingException(String message, Throwable cause) {
        super(message, cause);
    }
}
