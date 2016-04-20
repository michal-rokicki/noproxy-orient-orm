package pl.megaoak.orientorm;

public interface TransactionCallback<T> {
    T call() throws Exception;
}
