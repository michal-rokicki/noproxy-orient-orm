package pl.megaoak.orientorm.exception;

public class ObjectConfigurationException extends OrientOrmException {
    public ObjectConfigurationException(String message) {
        super(message);
    }

    public ObjectConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
