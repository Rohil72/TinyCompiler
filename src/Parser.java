public class Parser {

    Lexer lexer;
    Token currtoken;
    Token nextoken;
    public Parser(Lexer lexer){
        this.lexer = lexer;
        nextToken();
        nextToken();
    }

    public boolean checkToken(TokenType type){
        return type == currtoken.kind;
    }

    public boolean checkPeek(TokenType type){
       return type == nextoken.kind;
    }

    public void match(TokenType type){
        if(!checkToken(type)){
            System.out.println("This error message is work in progress");
        }
        nextToken();

    }

    public void nextToken(){

        currtoken = nextoken;
        nextoken = lexer.getToken();
    }


    // New line accommodation Function

    public void nl(){
        System.out.println("NEWLINE");

        match(TokenType.NEWLINE);
        // If multiple Newline are alongside each other
        while(checkToken(TokenType.NEWLINE)){
            nextToken();
        }

    }

    public void statement(){

        if(checkToken(TokenType.PRINT)){
            System.out.println("Print Statement");
            nextToken();

            if(checkToken(TokenType.STRING)){
                // Simple string

                nextToken();

            }
            else{
                // Expect an Expression statement
            }

            nl();
        }

    // Production Rules

        // Big function incoming








    }
    public void program(){
        System.out.println("Program");

        while(!checkToken(TokenType.EOF)){
            statement();
        }
    }
}
