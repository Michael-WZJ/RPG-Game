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

    public static class AmbiguityException extends STAGException {
        public AmbiguityException(String trigger) {
            super("More than one thing can be done by '" + trigger +
                    "' ?\nWhich one do you want ?");
        }
    }

    public static class NoSubjectException extends STAGException {
        public NoSubjectException(String trigger) {
            super("Not have enough entity to perform '" + trigger + "' ?");
        }
    }

    public static class ConflictException extends STAGException {
        public ConflictException(String type, String entities) {
            super("Some of the entity: " + entities + " can't be '"+ type +
                    "' ?\nMay be taken by other players ?");
        }

    }
}
