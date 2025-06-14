import java.util.HashSet;
import java.util.Set;
public class Parser {

    Lexer lexer;
    Token currtoken;
    Token nextoken;
    Emitter emitter;

    Set<String> labelsGOTOed = new HashSet<>();
    Set<String> labelsDecleared = new HashSet<>();
    Set<String> symbols = new HashSet<>();


    public Parser(Lexer lexer, Emitter emitter){
        this.lexer = lexer;
        this.emitter = emitter;
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



        expression();

        if(isComparisonOperator()){
            emitter.emit(currtoken.text);
            nextToken();
            expression();

        }
        else{
            System.out.println("Expected comparision Operator at " + currtoken.text);
        }

        while(isComparisonOperator()){
            emitter.emit(currtoken.text);
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



        term();

        while (checkToken(TokenType.PLUS) || checkToken(TokenType.MINUS)){
            emitter.emit(currtoken.text);
            nextToken();
            term();


        }


    }

    public void term(){


        unary();

        while(checkToken(TokenType.ASTERISK) || checkToken(TokenType.SLASH)){
            emitter.emit(currtoken.text);

            nextToken();
            unary();
        }


    }

    public void unary(){


        if(checkToken(TokenType.PLUS) || checkToken(TokenType.MINUS)){
            emitter.emit(currtoken.text);
            nextToken();
        }
        primary();



    }

    public void primary(){



        if(checkToken(TokenType.NUMBER)){
            emitter.emit(currtoken.text);
            nextToken();

        }

        else if(checkToken(TokenType.IDENT)){
            if(!symbols.contains(currtoken.text)){
                System.out.println("Referencing a Variable before assigning it " + currtoken.text);
                System.exit(1);
            }

            emitter.emit(currtoken.text);
            nextToken();
        }



        else{
            System.out.println("Error in parsing, Unexpected Token at" + currtoken.text);
            System.exit(1);
        }

    }


    // New line accommodation Function

    public void nl(){


        match(TokenType.NEWLINE);
        // If multiple Newline are alongside each other
        while(checkToken(TokenType.NEWLINE)){
            nextToken();
        }

    }

    public void statement(){

        if(checkToken(TokenType.PRINT)){

            nextToken();

            if(checkToken(TokenType.STRING)){
                // Simple string
                emitter.emitLine("printf(\"" + currtoken.text + "\\n\");");

                nextToken();

            }
            else{
                emitter.emit("printf(\"%" + ".2f\\n\", (float)(");
                expression();
                emitter.emitLine("));");


            }


        }
        // "IF" comparison "THEN" {statement} "ENDIF"
        else if(checkToken(TokenType.IF)){

            nextToken();
            emitter.emit("if(");
            Comparison();

            match(TokenType.THEN);
            nl();
            emitter.emitLine("){");
            while(!checkToken(TokenType.ENDIF)){
                statement(); // It was all just Recursion ? Answer : Always has been

            }

            match(TokenType.ENDIF);
            emitter.emitLine("}");


        }

        // "WHILE" comparison "REPEAT" {statement} "ENDWHILE"


        else if(checkToken(TokenType.WHILE)){

            nextToken();
            emitter.emit("while(");
            Comparison();

            match(TokenType.REPEAT);
            nl();
            emitter.emitLine("){");
            while(!checkToken(TokenType.ENDWHILE)){
                statement();
            }

            match(TokenType.ENDWHILE);
            emitter.emitLine("}");
        }

        // "LABEL"  ident
        else if(checkToken(TokenType.LABEL)){


            nextToken();

            if(labelsDecleared.contains(currtoken.text)){
                System.out.println("Already Contains the Label" + currtoken.text);
                System.exit(1);
            }
            labelsDecleared.add(currtoken.text);

            emitter.emitLine(currtoken.text + ":");


            match(TokenType.IDENT);


        }

        // "INPUT" ident

        else if(checkToken(TokenType.INPUT)){

            nextToken();
            if(!symbols.contains(currtoken.text)){
                emitter.headerLine("float " + currtoken.text + ";");

            }
            symbols.add(currtoken.text);
            emitter.emitLine("if(0 == scanf(\"%" + "f\", &" + currtoken.text + ")) { ");
            emitter.emitLine(currtoken.text + " = 0;");
            emitter.emit("scanf(\"% ");
            emitter.emitLine("*s\");");
            emitter.emitLine("}");

            match(TokenType.IDENT);


        }

        //GOTO ident
        else if(checkToken(TokenType.GOTO)){


            labelsGOTOed.add(currtoken.text);
            emitter.emitLine("goto " + currtoken.text + ";");
            nextToken();
            match(TokenType.IDENT);
        }

        // "LET" ident  "=" expression
        else if(checkToken(TokenType.LET)){


            nextToken();

            if(!symbols.contains(currtoken.text)){
                emitter.headerLine("float "+ currtoken.text + ";");
            }
            symbols.add(currtoken.text);

            emitter.emit(currtoken.text + " = ");
            match(TokenType.IDENT);
            match(TokenType.EQ);

            expression();
            emitter.emitLine(";");

        }

        else{
            System.out.println("Invalid statement at " + currtoken.text + " with the type being " + currtoken.kind );
            System.exit(1); // This is my first time using this statement, ISTG I have to use java for other than DSA



        }

        nl();

    }

    public void program(){
        emitter.headerLine("#include <stdio.h>");
        emitter.headerLine("int main(void){");



        // Checking for NEWLINE before the input stream starts
        while(checkToken(TokenType.NEWLINE)){
            nextToken();
        }

        while(!checkToken(TokenType.EOF)){
            statement();
        }

        emitter.emitLine("return 0;");
        emitter.emitLine("}");


        // Check that each label referenced in a GOTO is declared.
        if(!labelsDecleared.containsAll(labelsGOTOed)){
            System.out.println("Attempting to GOTO an undeclared variable");
            System.exit(1);
        }


    }
}
