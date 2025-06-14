public class Lexer {
    String source;
    int index = -1;
    char currChar;

    public Lexer(String source){
        this.source = source + '\n';
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


    public void skipWhiteSpace(){
        while(currChar == ' ' || currChar == '\t' || currChar == '\r'){
            nextChar();

            // This function does not use any formal parameters due to not being static but that is unacceptable in production.
            // Not due to non-static but it can possibly compromise mutability somehow.
        }

    }

    public void skipComment(){
        if(currChar == '#'){
            while(currChar != '\n'){
                nextChar();
            }

        }
    }

    public static TokenType checkIfKeyword(String tokenText) {

        for (TokenType kind : TokenType.values()) {


            if (kind.name().equals(tokenText.toUpperCase()) &&
                    kind.getValue() >= 100 && kind.getValue() < 200) {

                return kind;

            }
        }
        return null;
    }


    public Token getToken() {
        skipWhiteSpace();
        skipComment();

        Token token;

        if (currChar == '+') {
            token = new Token(String.valueOf(currChar), TokenType.PLUS);
        } else if (currChar == '-') {
            token = new Token(String.valueOf(currChar), TokenType.MINUS);
        } else if (currChar == '*') {
            token = new Token(String.valueOf(currChar), TokenType.ASTERISK);
        } else if (currChar == '/') {
            token = new Token(String.valueOf(currChar), TokenType.SLASH);
        }
        // This is the start of two character Operators
        else if (currChar == '=') {
            if (peek() == '=') {
                token = new Token("==", TokenType.EQEQ);
                nextChar(); // consume second '='
            } else {
                token = new Token(String.valueOf(currChar), TokenType.EQ);
            }
        }
        else if (currChar == '>') {
            if (peek() == '=') {
                token = new Token(">=", TokenType.GTEQ);
                nextChar();
            } else {
                token = new Token(String.valueOf(currChar), TokenType.GT);
            }
        }
        else if (currChar == '<') {
            if (peek() == '=') {
                token = new Token("<=", TokenType.LTEQ);
                nextChar();
            } else {
                token = new Token(String.valueOf(currChar), TokenType.LT);
            }
        }
        else if (currChar == '!') {
            if (peek() == '=') {
                token = new Token("!=", TokenType.NOTEQ);
                nextChar();
            } else {
                System.out.println("Expected != but got !");
                token = new Token(" ", TokenType.EOF);
            }
        }
        // This exists for passing string for Printing
        else if(currChar == '\"') {

            nextChar();
            int start = index;
            while(currChar != '\"'){
                if(currChar == '\r' || currChar == '\t' || currChar == '\n' || currChar == '\\' || currChar == '%'){
                    System.out.println("The character " + currChar + " is an illegal Character");

                }
                nextChar();
            }

            token = new Token(source.substring(start, index), TokenType.STRING); // Rather than create a variable and inserting we can directly do it, python doesn't ?


        }

        // Checking if the Character is a Digit
        else if(Character.isDigit(currChar)){
            int start  = index;

            while (Character.isDigit(peek())){
                nextChar();
            }

            if( peek() == '.'){
                nextChar();

                if(!Character.isDigit(peek())){
                    System.out.println("Error, An character in Number");
                }
                while(Character.isDigit(peek())){
                    nextChar();
                }
            }

            token = new Token(source.substring(start,index +1 ), TokenType.NUMBER);
        }

        // Checking if the input is an Identifier or Keyword

        else if(Character.isAlphabetic(currChar)){
            int start = index;

            while(Character.isLetterOrDigit(peek())){
                nextChar();
            }
            String tokText = source.substring(start,index+1);

            TokenType keyword = checkIfKeyword(tokText);

            if (keyword == null) token = new Token(tokText, TokenType.IDENT);
            else token = new Token(tokText, keyword);
        }

        else if (currChar == '\n') {
            token = new Token("\\n", TokenType.NEWLINE);
        }
        else if (currChar == '\0') {
            token = new Token(" ", TokenType.EOF);
        }
        else {
            System.out.println("Invalid Token: " + currChar);
            token = new Token(" ", TokenType.EOF);
        }

        nextChar();
        return token;
    }





}
class Token {
    String text;
    TokenType kind;

    Token(String text, TokenType kind){
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

