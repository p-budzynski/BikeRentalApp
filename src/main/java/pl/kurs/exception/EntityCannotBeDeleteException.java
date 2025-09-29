package pl.kurs.exception;

public class EntityCannotBeDeleteException extends RuntimeException {
    public EntityCannotBeDeleteException(String message) {
        super(message);
    }
}
