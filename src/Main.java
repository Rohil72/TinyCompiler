public class Main {
    public static void main(String[] args) {

        Lexer lexer = new Lexer(" LET this Run123");

        while(lexer.peek() != '\0'){
            System.out.println(lexer.currChar);
            lexer.nextChar();
        }


    }

    // test function


}
