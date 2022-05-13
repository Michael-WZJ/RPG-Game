package edu.uob;

import java.io.Serial;

public class STAGException extends Exception {

    @Serial
    private static final long serialVersionUID = 7761734534936697859L;

    public STAGException(String message) {
        super(message);
    }

    public static class InvalidCommandException extends STAGException {
        @Serial
        private static final long serialVersionUID = 980069672548821880L;

        public InvalidCommandException(String message) {
            super("[ERROR] " + message);
        }
    }

    public static class AmbiguityException extends STAGException {
        @Serial
        private static final long serialVersionUID = -909922183553550544L;

        public AmbiguityException(String trigger) {
            super("[ERROR] More than one thing can be done by '" + trigger +
                    "' ?\nWhich one do you want ?");
        }
    }

    public static class NoSubjectException extends STAGException {
        @Serial
        private static final long serialVersionUID = 9081219753469908208L;

        public NoSubjectException(String trigger) {
            super("[ERROR] Not have enough entity to perform '" + trigger + "' ?");
        }
    }

    public static class ConflictException extends STAGException {
        @Serial
        private static final long serialVersionUID = 1525266928792542261L;

        public ConflictException(String type, String entities) {
            super("[ERROR] Some of the entity: " + entities + " can't be '"+ type +
                    "' ?\nMay be taken by other players ?");
        }
    }
}
