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
            System.out.println("Bad Match for Token");
        }
        nextToken();

    }

    public void nextToken(){

        currtoken = nextoken;
        nextoken = lexer.getToken();
    }

    public void Comparison(){
        //This is to be built
    }

    public void expression(){
        // This is to be built
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
            System.out.println("STATEMENT PRINT");
            nextToken();

            if(checkToken(TokenType.STRING)){
                // Simple string

                nextToken();

            }
            else{
                // Expect an Expression statement
            }


        }
        // "IF" comparison "THEN" {statement} "ENDIF"
        else if(checkToken(TokenType.IF)){
            System.out.println("STATEMENT - IF");
            nextToken();
            Comparison();

            match(TokenType.THEN);
            nl();

            while(!checkToken(TokenType.ENDIF)){
                statement(); // It was all just Recursion ? Answer : Always has been

            }



        }

        // "WHILE" comparison "REPEAT" {statement} "ENDWHILE"


        else if(checkToken(TokenType.WHILE)){
            System.out.println("STATEMENT WHILE");
            nextToken();
            Comparison();

            match(TokenType.REPEAT);
            nl();

            while(!checkToken(TokenType.ENDWHILE)){
                statement();
            }
        }

        // "LABEL"  ident
        else if(checkToken(TokenType.LABEL)){
            System.out.println("STATEMENT - LABEL");

            nextToken();

            match(TokenType.IDENT);
            expression();

        }

        // "INPUT" ident

        else if(checkToken(TokenType.INPUT)){
            System.out.println("STATEMENT INPUT");
            nextToken();

            match(TokenType.IDENT);


        }

        //GOTO ident
        else if(checkToken(TokenType.GOTO)){
            System.out.println("STATEMENT GOTO");

            nextToken();
            match(TokenType.IDENT);
        }

        // "LET" ident  "=" expression
        else if(checkToken(TokenType.LET)){
            System.out.println("STATEMENT LET");

            nextToken();

            match(TokenType.IDENT);

            match(TokenType.EQ);


        }

        else{
            System.out.println("Invalid statement");
        }

        nl();

    }

    public void program(){
        System.out.println("Program");

        while(!checkToken(TokenType.EOF)){
            statement();
        }
    }
}
