public class Lexer {
    String source;
    int index = -1;
    char currChar;

    public Lexer(String source){
        this.source = source;
        nextChar();
    }
    // processing the next character, We check if we are at the end of our character and if we are we add a null character there
    //  else just pass the next character in the string

    public void nextChar(){
        index++;
        if(index >= source.length()){
            currChar = '\0';

        }
        else{
            currChar = source.charAt(index);
        }
    }

    public char peek(){
        if(index+1 >= source.length()){
            return '\0';
        }
        return source.charAt(index+1);
        // Fun fact if you didn't know \0 represents null in Ascii

    }

    public void abort(String message){

    }

    public void skipWhiteSpace(){
        while(currChar == ' ' || currChar == '\t' || currChar == '\r' || currChar == '\n'){
            nextChar();

            // This function does not use any formal parameters due to not being static but that is unacceptable in production.
            // Not due to non-static but it can possibly compromise mutability somehow.
        }

    }

    public void skipComment(){

    }

    public Token getToken(){
        skipWhiteSpace();

        Token token;

        if(currChar == '+'){
            token = new Token(currChar, TokenType.PLUS);
        }

        else if(currChar == '-'){
            token = new Token(currChar, TokenType.MINUS);
        }

        else if(currChar == '*'){
            token = new Token(currChar, TokenType.ASTERISK);
        }
        else if(currChar == '/'){
            token = new Token(currChar, TokenType.SLASH);
        }
        else if(currChar == '\n'){
            token = new Token(currChar, TokenType.NEWLINE);
        }
        else if(currChar == '\0'){
            token = new Token(' ', TokenType.EOF);
        }
        else {
            System.out.println("Invalid Token");
            token = new Token(' ', TokenType.EOF);
        }


        nextChar();
        return token;
        // require return statement possibly using a variable even if it seems obvious I am mentioning it cause Python is like that



    }




}
class Token {
    char text;
    TokenType kind;

    Token(char text, TokenType kind){
        this.text = text;
        this.kind = kind;
    }


}

enum TokenType {
    // Special tokens
    EOF(-1),
    NEWLINE(0),
    NUMBER(1),
    IDENT(2),
    STRING(3),

    // Keywords
    LABEL(101),
    GOTO(102),
    PRINT(103),
    INPUT(104),
    LET(105),
    IF(106),
    THEN(107),
    ENDIF(108),
    WHILE(109),
    REPEAT(110),
    ENDWHILE(111),

    // Operators
    EQ(201),
    PLUS(202),
    MINUS(203),
    ASTERISK(204),
    SLASH(205),
    EQEQ(206),
    NOTEQ(207),
    LT(208),
    LTEQ(209),
    GT(210),
    GTEQ(211);

    private final int value;

    TokenType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

