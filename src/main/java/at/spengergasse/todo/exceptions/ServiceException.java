package at.spengergasse.todo.exceptions;

public class ServiceException extends RuntimeException
{
    public ServiceException(String message) {
        super(message);
    }

    public static ServiceException ofNotFound(Long id) {
        return new ServiceException("Entity with id " + id + " not found.");
    }

    // TODO: Add more factory methods as needed
}
