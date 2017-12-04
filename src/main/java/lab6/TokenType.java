package lab6;


public enum TokenType {

    LONGINT,
    REAL,
    BOOLEAN,
    VAR,

    TRUE,
    FALSE,

    OF,
    ARRAY,

    SEMI_COLON, // ;
    COLON, // :
    
    PLUS, // +
    MINUS, // -

    ASSIGN, // :=
    EQUALS, // =
    NOT_EQUALS, // <>
    LESS_EQUALS, // <=
    LESS, // <
    MORE, // >
    MORE_EQUALS, // >=
    POINT_POINT, // ..

    LEFT_ROUND_BRACKET, // (
    RIGHT_ROUND_BRACKET, // )
    LEFT_SQUARE_BRACKET, // [
    RIGHT_SQUARE_BRACKET, // ]
    EOF
}
