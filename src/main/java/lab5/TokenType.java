package lab5;


public enum TokenType {

    NUMBER,
    VAR,

    // keyword
    IF,
    THEN,
    ELSE,
    FOR,
    TO,
    DO,
    BEGIN,
    END,

    SEMI_COLON, // ;
    
    PLUS, // +
    MINUS, // -

    ASSIGN, // :=
    EQUALS, // =
    NOT_EQUALS, // <>
    LESS_EQUALS, // <=
    LESS, // <
    MORE, // >
    MORE_EQUALS, // >=


    LEFT_ROUND_BRACKET, // (
    RIGHT_ROUND_BRACKET, // )
    EOF
}
