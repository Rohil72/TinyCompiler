public class Lexer {
    String source;
    int index;
    char currChar;

    public Lexer(String source){
        this.source = source;
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

    }

    public void skipComment(){

    }

    public String getToken(){

        return "";

    }




}
