public class EventException extends RuntimeException { // extends RuntimeException as it is unchecked
    public EventException(String message) {
        super(message); // calls parent constructor with the given msg
    }
}
