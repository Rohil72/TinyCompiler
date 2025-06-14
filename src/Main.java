import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        // Lexer Tests
//        String source = "IF+-123 foo*THEN/";
//        Lexer lexer = new Lexer(source);

        // Test 1: doesn't output the first character, not sure what the issue is but can be resolved by a do while loop but Let's hold that thought for a bit
//        while(lexer.peek() != '\0'){
//            System.out.println(lexer.currChar);
//            lexer.nextChar();
//        }

        //Test 2
//        Token token = lexer.getToken();
//
//        while(token.kind != TokenType.EOF){
//            System.out.println(token.kind);
//            token = lexer.getToken();
//        }
//

        // Parser Test 1


        String source = "";

        try {
            source = new String(Files.readAllBytes(Paths.get("src/Text3.txt")));
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            System.exit(1);
        }

        // You can now pass `source` to your Lexer or other compiler components
        Lexer lexer = new Lexer(source);
        Emitter emitter = new Emitter("src/output.txt");
        Parser parser = new Parser(lexer, emitter);


        parser.program();
        emitter.writeFile();


        System.out.println("Compiling completed");
        System.out.println("""
                Try Running your code in the Compiler of your choice(You can script it as well)\s
                 or Run it here:\s
                 https://www.onlinegdb.com/online_c_compiler""");
    }



    // test function


}
