public class Main {
    public static void main(String[] args) {
        String source = "+- */";
        Lexer lexer = new Lexer(source);

        // Test 1: doesn't output the first character, not sure what the issue is but can be resolved by a do while loop but Let's hold that thought for a bit
//        while(lexer.peek() != '\0'){
//            System.out.println(lexer.currChar);
//            lexer.nextChar();
//        }

        //Test 2
        Token token = lexer.getToken();

        while(token.kind != TokenType.EOF){
            System.out.println(token.kind);
            token = lexer.getToken();
        }


    }

    // test function


}
