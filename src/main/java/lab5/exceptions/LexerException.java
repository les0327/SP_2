package lab5.exceptions;


public final class LexerException extends RuntimeException {

    public LexerException(String message) {
        super(message);
    }

    public LexerException(int row, int col, String message) {
        super("LexerException at [row="+row+", col="+col+"]: " + message + ";");
    }
}