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
            System.out.println("Expected " + type + "got " + currtoken.kind);
            System.exit(1);
        }
        nextToken();

    }

    public void nextToken(){

        currtoken = nextoken;
        nextoken = lexer.getToken();
    }
    // This it for comparative Operators
    public void Comparison(){
        //This is to be built
        System.out.println("Comparison");

        expression();

        if(isComparisonOperator()){
            nextToken();
            expression();

        }
        else{
            System.out.println("Expected comparision Operator at " + currtoken.text);
        }

        while(isComparisonOperator()){
            nextToken();
            expression();
        }


    }

    public boolean isComparisonOperator(){
        return checkToken(TokenType.GT)  || checkToken(TokenType.GTEQ)
                || checkToken(TokenType.LT) || checkToken(TokenType.LTEQ)
                || checkToken(TokenType.EQEQ) || checkToken(TokenType.NOTEQ);

    }

    public void expression(){
        // Here we use the linearity of If and else statement to define for us
        // the precedence logic we are using

        System.out.println("EXPRESSION");

        term();

        while (checkToken(TokenType.PLUS) || checkToken(TokenType.MINUS)){
            nextToken();
            term();


        }


    }

    public void term(){
        System.out.println("TERM");

        unary();

        while(checkToken(TokenType.ASTERISK) || checkToken(TokenType.SLASH)){
            nextToken();
            unary();
        }


    }

    public void unary(){
        System.out.println("UNARY");

        if(checkToken(TokenType.PLUS) || checkToken(TokenType.MINUS)){
            nextToken();
        }
        primary();



    }

    public void primary(){
        System.out.println("PRIMARY "  + "(" + currtoken.text + ")");


        if(checkToken(TokenType.NUMBER)){
            nextToken();

        }

        else if(checkToken(TokenType.IDENT)){
            nextToken();
        }

        else{
            System.out.println("Error in parsing, Unexpected Token at" + currtoken.text);
            System.exit(1);
        }

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

            match(TokenType.ENDIF);



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

            expression();


        }

        else{
            System.out.println("Invalid statement at " + currtoken.text + " with the type being " + currtoken.kind );
            System.exit(1); // This is my first time using this statement, ISTG I have to use java for other than DSA



        }

        nl();

    }

    public void program(){
        System.out.println("Program");


        // Checking for NEWLINE before the input stream starts
        while(checkToken(TokenType.NEWLINE)){
            nextToken();
        }

        while(!checkToken(TokenType.EOF)){
            statement();
        }

    }
}
