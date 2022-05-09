package edu.uob;

public class STAGException extends Exception {

    public STAGException(String message) {
        super(message);
    }

    public static class InvalidCommandException extends STAGException {
        public InvalidCommandException(String message) {
            super(message);
        }
    }

}
